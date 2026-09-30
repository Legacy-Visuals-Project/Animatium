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
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.renderer.CubeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.panorama.LegacyPanoramaRenderer;

@Mixin(GuiRenderer.class)
public abstract class MixinGuiRenderer_LegacyPanoramaRendering {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CubeMap;render(FF)V", ordinal = 0))
    private void animatium$panoramaRendering(final CubeMap instance, final float rotXInDegrees, final float rotYInDegrees, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.panoramaRendering) {
            LegacyPanoramaRenderer.INSTANCE.render();
        } else {
            original.call(instance, rotXInDegrees, rotYInDegrees);
        }
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void animatium$closePanorama(final CallbackInfo ci) {
        LegacyPanoramaRenderer.INSTANCE.close();
    }
}
