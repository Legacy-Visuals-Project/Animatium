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

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.dimension.DimensionType;
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

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderDarkDisc(Lcom/mojang/renderpearl/api/commands/RenderPass;)V", shift = At.Shift.AFTER))
    private void animatium$voidBox(final GpuBufferSlice skyFog, final SkyRenderState state, final CallbackInfo ci, @Local(name = "renderPass") final RenderPass pass) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.playerVoidBox) {
            LegacySkyRenderer.renderVoidBox(pass, ((SkyUtilityState) state).animatium$getHorizonHeight());
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/commands/RenderPass;close()V"))
    private static void animatium$blueVoid(final GpuBufferSlice skyFog, final SkyRenderState state, final CallbackInfo ci, @Local(name = "renderPass") final RenderPass pass) {
        // TODO/NOTE: Ignore the intellij warning for 'state.skyColor' here as it is wrong, it can be null
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.blueVoidSky && state.skybox == DimensionType.Skybox.OVERWORLD && state.skyColor != null) {
            LegacySkyRenderer.renderBlueVoid(pass, ARGB.colorFromVector3f(state.skyColor), ((SkyUtilityState) state).animatium$getHorizonHeight());
        }
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void animatium$closeSkyRenderUtility(final CallbackInfo ci) {
        LegacySkyRenderer.close();
    }
}
