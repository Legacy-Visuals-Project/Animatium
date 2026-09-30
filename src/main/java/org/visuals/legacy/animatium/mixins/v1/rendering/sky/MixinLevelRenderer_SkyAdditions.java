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

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.SkyRenderState;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.LegacySkyRenderer;
import org.visuals.legacy.animatium.util.states.SkyUtilityState;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer_SkyAdditions {
    @Inject(method = "method_62215", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderDarkDisc()V", shift = At.Shift.AFTER))
    private static void animatium$voidBox(final GpuBufferSlice gpuBufferSlice, final SkyRenderState state, final SkyRenderer skyRenderer, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.playerVoidBox) {
            LegacySkyRenderer.renderVoidBox(((SkyUtilityState) state).animatium$getHorizonHeight());
        }
    }

    @Inject(method = "method_62215", at = @At("TAIL"))
    private static void animatium$blueVoid(final GpuBufferSlice skyFog, final SkyRenderState state, final SkyRenderer skyRenderer, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.blueVoidSky && state.skybox != DimensionType.Skybox.END) {
            LegacySkyRenderer.renderBlueVoid(state.skyColor, ((SkyUtilityState) state).animatium$getHorizonHeight());
        }
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void animatium$closeSkyRenderUtility(final CallbackInfo ci) {
        LegacySkyRenderer.close();
    }
}
