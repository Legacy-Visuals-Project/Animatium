/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * Licensed under the PolyForm Shield License 1.0.0.
 * You may obtain a copy of the license at
 * https://polyformproject.org/licenses/shield/1.0.0
 */

package org.visuals.legacy.animatium.mixins.v1.entity.armor_hurt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.moulberry.mixinconstraints.annotations.IfModAbsent;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.object.skull.SkullModelBase;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@IfModAbsent("ichor")
@Mixin(CustomHeadLayer.class)
public abstract class MixinCustomHeadLayer_DamageTintArmor<S extends LivingEntityRenderState, M extends EntityModel<S> & HeadedModel> {
    @WrapOperation(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;submitSkull(Lnet/minecraft/core/Direction;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/model/object/skull/SkullModelBase;Lnet/minecraft/client/renderer/rendertype/RenderType;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    private void animatium$damageTintArmor$skullOverride(final Direction direction, final float rotation, final float animationValue, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final SkullModelBase model, final RenderType renderType, final int outlineColor, final ModelFeatureRenderer.CrumblingOverlay breakProgress, final Operation<Void> original, @Local(argsOnly = true, ordinal = 0) final S state) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.damageTintArmor) {
            poseStack.pushPose();
            if (direction == null) {
                poseStack.translate(0.5F, 0.0F, 0.5F);
            } else {
                poseStack.translate(0.5F - (float) direction.getStepX() * 0.25F, 0.25F, 0.5F - (float) direction.getStepZ() * 0.25F);
            }

            poseStack.scale(-1.0F, -1.0F, 1.0F);
            final SkullModelBase.State modelState = new SkullModelBase.State();
            modelState.animationPos = animationValue;
            modelState.yRot = rotation;
            submitNodeCollector.submitModel(model, modelState, poseStack, renderType, lightCoords, OverlayTexture.pack(0, OverlayTexture.v(state.hasRedOverlay)), outlineColor, breakProgress);
            poseStack.popPose();
        } else {
            original.call(direction, rotation, animationValue, poseStack, submitNodeCollector, lightCoords, model, renderType, outlineColor, breakProgress);
        }
    }

    @ModifyExpressionValue(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I", opcode = Opcodes.GETSTATIC))
    private int animatium$damageTintArmor(final int original, @Local(argsOnly = true, ordinal = 0) final S state) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.damageTintArmor) {
            return OverlayTexture.pack(0, OverlayTexture.v(state.hasRedOverlay));
        } else {
            return original;
        }
    }
}
