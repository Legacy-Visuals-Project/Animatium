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

package org.visuals.legacy.animatium.mixins.v1.gui.screen_tweaks.panorama;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.panorama.LegacyPanoramaRenderer;

@Mixin(PanoramaRenderer.class)
public abstract class MixinPanoramaRenderer_LegacyPanorama {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CubeMap;render(Lnet/minecraft/client/Minecraft;FF)V"))
    private void animatium$panoramaRendering(final CubeMap instance, final Minecraft minecraft, final float rotXInDegrees, final float rotYInDegrees, final Operation<Void> original, @Local(argsOnly = true, ordinal = 0) final GuiGraphics guiGraphics, @Local(argsOnly = true, ordinal = 0) final int width, @Local(argsOnly = true, ordinal = 1) final int height) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.panoramaRendering) {
            LegacyPanoramaRenderer.INSTANCE.extractRenderState(guiGraphics, width, height, Minecraft.getInstance().getDeltaTracker().getRealtimeDeltaTicks());
            LegacyPanoramaRenderer.INSTANCE.render();
        } else {
            original.call(instance, minecraft, rotXInDegrees, rotYInDegrees);
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIII)V"))
    private void animatium$legacyPanorama(final GuiGraphics instance, final RenderPipeline renderPipeline, final Identifier texture, final int x, final int y, final float u, final float v, final int width, final int height, final int srcWidth, final int srcHeight, final int textureWidth, final int textureHeight, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.panoramaRendering) {
            instance.fillGradient(0, 0, width, height, -2130706433, 16777215);
            instance.fillGradient(0, 0, width, height, 0, Integer.MIN_VALUE);
        } else {
            original.call(instance, renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight);
        }
    }
}
