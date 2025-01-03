package me.mixces.animatium.mixins.screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.mixins.accessor.PlayerAccessor;
import me.mixces.animatium.util.CameraVersion;
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
        if (AnimatiumConfig.instance().getRemoveSmoothSneaking()) {
            this.eyeHeightOld = eyeHeight;
            this.eyeHeight = this.animatium$getStandingEyeHeight();
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeHeight()F"))
    private float animatium$useOldEyeHeight(Entity instance, Operation<Float> original) {
        if (AnimatiumConfig.instance().getFakeOldSneakEyeHeight()) {
            return this.animatium$getStandingEyeHeight();
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/Camera;eyeHeight:F"))
    private void animatium$oldSneakAnimationInterpolation(Camera instance, float value, Operation<Void> original) {
        if (AnimatiumConfig.instance().getOldSneakAnimationInterpolation() && !AnimatiumConfig.instance().getRemoveSmoothSneaking() && this.entity.getEyeHeight() < eyeHeight) {
            this.eyeHeight = this.animatium$getStandingEyeHeight();
        } else {
            original.call(instance, value);
        }
    }

    // TODO/NOTE: Could we also just do this in TransparentBlock?
    @WrapOperation(method = "getMaxZoom", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/ClipContext$Block;VISUAL:Lnet/minecraft/world/level/ClipContext$Block;"))
    private ClipContext.Block animatium$disableCameraTransparentPassthrough(Operation<ClipContext.Block> original) {
        if (AnimatiumConfig.instance().getDisableCameraTransparentPassthrough()) {
            return ClipContext.Block.OUTLINE;
        } else {
            return original.call();
        }
    }

    @Inject(method = "setup", at = @At(value = "TAIL"))
    private void animatium$oldCameraVersion(BlockGetter area, Entity entity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (AnimatiumConfig.instance().getCameraVersion() != CameraVersion.LATEST && !thirdPerson && !(entity instanceof LivingEntity && ((LivingEntity) entity).isSleeping())) {
            // TODO: Fix bed/sleeping position
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

//    @WrapOperation(method = "getNearPlane", at = @At(value = "INVOKE", target = "Ljava/lang/Integer;intValue()I"))
//    private int animatium$removeFOVBasedProjection(Integer instance, Operation<Integer> original) {
//        if (AnimatiumConfig.getInstance().getRemoveFOVBasedProjection()) {
//            return 70;
//        } else {
//            return original.call(instance);
//        }
//    }

    @Unique
    private float animatium$getStandingEyeHeight() {
        float standingEyeHeight = this.entity.getEyeHeight();
        if (AnimatiumConfig.instance().getFakeOldSneakEyeHeight() && this.entity.isShiftKeyDown() && this.entity instanceof Player player && ((PlayerAccessor) player).canChangeIntoPose$(Pose.STANDING)) {
            return 1.54F;
        } else {
            return standingEyeHeight;
        }
    }
}
