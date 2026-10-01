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

package org.visuals.legacy.animatium.mixins.v1.rendering.sky;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.renderpearl.api.commands.RenderPass;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.minecraft.util.ARGB;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.LegacySkyRenderer;
import org.visuals.legacy.animatium.util.states.SkyUtilityState;

@Mixin(SkyRenderer.class)
public abstract class MixinSkyRenderer_SkyAdditions {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void animatium$extractHorizonHeight(final ClientLevel level, final float tickDelta, final Camera camera, final SkyRenderState state, final CallbackInfo ci) {
        ((SkyUtilityState) state).animatium$setHorizonHeight(LegacySkyRenderer.getHorizonEyeHeight(level, tickDelta));
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderSunMoonAndStars(Lcom/mojang/renderpearl/api/commands/RenderPass;Lcom/mojang/blaze3d/vertex/PoseStack;FFFLnet/minecraft/world/level/MoonPhase;FFZ)V", shift = At.Shift.AFTER))
    private void animatium$voidDiscAndBox(final SkyRenderState state, final RenderPass pass, final Vector4f fogColor, final boolean withDepthAttachment, final CallbackInfo ci) {
        final double depth = ((SkyUtilityState) state).animatium$getHorizonHeight();
        if (Animatium.isEnabled() && depth < 0.0) {
            if (AnimatiumConfig.instance().other.blueVoidSky) {
                LegacySkyRenderer.renderVoidDisc(pass, withDepthAttachment);
            }

            if (AnimatiumConfig.instance().other.playerVoidBox) {
                LegacySkyRenderer.renderVoidBox(pass, depth, withDepthAttachment);
            }
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderSkyOccluder(Lcom/mojang/renderpearl/api/commands/RenderPass;Lorg/joml/Vector4f;Z)V"))
    private void animatium$blueVoid(final SkyRenderer instance, final RenderPass pass, final Vector4f color, final boolean withDepthAttachment, final Operation<Void> original, @Local(name = "state", argsOnly = true) final SkyRenderState state) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.blueVoidSky) {
            final double depth = ((SkyUtilityState) state).animatium$getHorizonHeight();
            LegacySkyRenderer.renderBlueVoid(pass, ARGB.colorFromVector4f(color), depth, withDepthAttachment);
        } else {
            original.call(instance, pass, color, withDepthAttachment);
        }
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void animatium$closeSkyRenderUtility(final CallbackInfo ci) {
        LegacySkyRenderer.close();
    }
}
