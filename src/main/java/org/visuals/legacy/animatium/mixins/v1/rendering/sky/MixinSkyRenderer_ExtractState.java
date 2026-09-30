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

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.SkyRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.handler.rendering.LegacySkyRenderer;
import org.visuals.legacy.animatium.util.states.SkyUtilityState;

@Mixin(SkyRenderer.class)
public abstract class MixinSkyRenderer_ExtractState {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void animatium$extractHorizonHeight(final ClientLevel level, final float tickDelta, final Camera camera, final SkyRenderState state, final CallbackInfo ci) {
        ((SkyUtilityState) state).animatium$setHorizonHeight(LegacySkyRenderer.getHorizonEyeHeight(level, tickDelta));
    }
}
