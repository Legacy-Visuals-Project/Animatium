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

import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.handler.rendering.panorama.LegacyPanoramaRenderer;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer_CloseLegacyPanorama {
    @Inject(method = "close", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CubeMap;close()V", shift = At.Shift.AFTER))
    private void animatium$closePanorama(final CallbackInfo ci) {
        LegacyPanoramaRenderer.INSTANCE.close();
    }
}
