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

package btw.mixces.animatium.mixins.renderer.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.EntityUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer extends LivingEntityRenderer<AbstractClientPlayer, PlayerRenderState, PlayerModel> {
    public MixinPlayerRenderer(EntityRendererProvider.Context context, PlayerModel entityModel, float f) {
        super(context, entityModel, f);
    }

    @Shadow
    private static HumanoidModel.ArmPose getArmPose(AbstractClientPlayer abstractClientPlayer, HumanoidArm humanoidArm) {
        return null;
    }

    @Shadow
    @NotNull
    public abstract PlayerRenderState createRenderState();

    @WrapOperation(method = "extractCapeState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(FFF)F"))
    private static float animatium$changeLerpMethod(float delta, float start, float end, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldCapeMovement()) {
            return Mth.lerp(delta, start, end);
        } else {
            return original.call(delta, start, end);
        }
    }

    @ModifyArg(method = "extractCapeState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 1), index = 2)
    private static float animatium$uncapRotation(float original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDontClampCapeLean()) {
            return Float.MAX_VALUE;
        } else {
            return original;
        }
    }

    @WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;capeLean:F", ordinal = 1))
    private static boolean animatium$dontAssignLeanField(PlayerRenderState instance, float value) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getOldCapeMovement();
    }

    @WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;capeLean2:F", ordinal = 1))
    private static boolean animatium$dontAssignLean2Field(PlayerRenderState instance, float value) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getOldCapeMovement();
    }

    @WrapOperation(method = "getRenderOffset(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;)Lnet/minecraft/world/phys/Vec3;", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;isCrouching:Z"))
    private boolean animatium$fixSneakingFeetPosition(PlayerRenderState instance, Operation<Boolean> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixSneakingFeetPosition()) {
            return false;
        } else {
            return original.call(instance);
        }
    }

    @Inject(method = "renderHand", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/model/geom/ModelPart;visible:Z", ordinal = 2))
    private void animatium$oldHeldItemArmLogic(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ResourceLocation resourceLocation, ModelPart modelPart, boolean bl, CallbackInfo ci, @Local PlayerModel playerModel) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldHeldItemArmLogic()) {
            AbstractClientPlayer player = Minecraft.getInstance().player;
            HumanoidArm arm = modelPart == model.rightArm ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            if (player != null && getArmPose(player, arm) == HumanoidModel.ArmPose.ITEM) {
                modelPart.xRot = modelPart.xRot * 0.5F - (float) (Math.PI / 10);
                modelPart.yRot = 0.0F;
            }
        }
    }
}
