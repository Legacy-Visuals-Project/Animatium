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

package org.visuals.legacy.animatium.mixins.v1.entity.cape.movement;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.player.PlayerCapeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(ModelFeatureRenderer.class)
public abstract class MixinModelFeatureRenderer_CapeSwinging {
    @Inject(method = "renderModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;setupAnim(Ljava/lang/Object;)V", shift = At.Shift.AFTER))
    private <S> void animatium$capeSwingRotation(final SubmitNodeStorage.ModelSubmit<S> submit, final RenderType renderType, final VertexConsumer buffer, final OutlineBufferSource outlineBufferSource, final MultiBufferSource.BufferSource crumblingBufferSource, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.disableCapeSwingRotation && submit.model() instanceof PlayerCapeModel playerCapeModel) {
            playerCapeModel.body.yRot = 0;
        }
    }
}
