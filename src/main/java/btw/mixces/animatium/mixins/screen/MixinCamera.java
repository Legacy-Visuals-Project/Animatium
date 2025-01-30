/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2025 lowercasebtw
 * Copyright (C) 2024-2025 mixces
 * Copyright (C) 2024-2025 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package btw.mixces.animatium.mixins.screen;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.mixins.accessor.PlayerAccessor;
import btw.mixces.animatium.util.CameraVersion;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class MixinCamera {
    @Shadow
    private float eyeHeightOld;

    @Shadow
    private float eyeHeight;

    @Shadow
    private Entity entity;

    @Shadow
    protected abstract void move(float f, float g, float h);

    @Inject(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", shift = At.Shift.BEFORE))
    private void animatium$removeSmoothSneaking(CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getRemoveSmoothSneaking()) {
            this.eyeHeightOld = eyeHeight;
            this.eyeHeight = this.animatium$getStandingEyeHeight();
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeHeight()F"))
    private float animatium$useOldEyeHeight(Entity instance, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFakeOldSneakEyeHeight()) {
            return this.animatium$getStandingEyeHeight();
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/Camera;eyeHeight:F"))
    private void animatium$oldSneakAnimationInterpolation(Camera instance, float value, Operation<Void> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldSneakAnimationInterpolation() && !AnimatiumConfig.instance().getRemoveSmoothSneaking() && this.entity.getEyeHeight() < eyeHeight) {
            this.eyeHeight = this.animatium$getStandingEyeHeight();
        } else {
            original.call(instance, value);
        }
    }

    @WrapOperation(method = "getMaxZoom", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/ClipContext$Block;VISUAL:Lnet/minecraft/world/level/ClipContext$Block;"))
    private ClipContext.Block animatium$disableCameraTransparentPassthrough(Operation<ClipContext.Block> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDisableCameraTransparentPassthrough()) {
            return ClipContext.Block.OUTLINE;
        } else {
            return original.call();
        }
    }

    @Inject(method = "setup", at = @At(value = "TAIL"))
    private void animatium$oldCameraVersion(BlockGetter area, Entity entity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        // TODO: Fix bed/sleeping position
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getCameraVersion() != CameraVersion.LATEST && !thirdPerson && !(entity instanceof LivingEntity && ((LivingEntity) entity).isSleeping())) {
            final int ordinal = AnimatiumConfig.instance().getCameraVersion().ordinal();
            if (ordinal <= CameraVersion.V1_14_V1_14_3.ordinal()) {
                // <= 1.14.3
                this.move(-0.05000000074505806F, 0.0F, 0.0F);
                // <= 1.13.2
                if (ordinal <= CameraVersion.V1_9_V1_13_2.ordinal()) {
                    this.move(0.1F, 0.0F, 0.0F);
                    // <= 1.8
                    if (ordinal == CameraVersion.V1_8.ordinal()) {
                        this.move(-0.15F, 0, 0); // unfixing parallax
                    }
                }
            }
        }
    }

    @Unique
    private float animatium$getStandingEyeHeight() {
        float standingEyeHeight = this.entity.getEyeHeight();
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFakeOldSneakEyeHeight() && this.entity.hasPose(Pose.CROUCHING) && this.entity instanceof Player player && ((PlayerAccessor) player).canChangeIntoPose(Pose.STANDING)) {
            float scale = this.entity instanceof LivingEntity livingEntity ? livingEntity.getScale() : 1.0F;
            return 1.54F * scale;
        } else {
            return standingEyeHeight;
        }
    }
}
