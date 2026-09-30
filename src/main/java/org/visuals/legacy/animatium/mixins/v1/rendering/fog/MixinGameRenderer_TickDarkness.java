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

package org.visuals.legacy.animatium.mixins.v1.rendering.fog;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.GameRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.handler.rendering.LegacyFogDarkness;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer_TickDarkness {
    @Shadow
    @Final
    private Camera mainCamera;

    @Shadow
    @Final
    private GameRenderState gameRenderState;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;tick()V", shift = At.Shift.AFTER))
    private void animatium$tickFogDarkness(final CallbackInfo ci) {
        final Entity entity = this.mainCamera.entity();
        if (entity != null) {
            LegacyFogDarkness.tick(entity, this.gameRenderState.optionsRenderState.renderDistance);
        }
    }
}
