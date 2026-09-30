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

package org.visuals.legacy.animatium.mixins.v1.rendering.lighting;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LegacyLightmapRenderer;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LightmapStateExtension;

@Mixin(Lightmap.class)
public abstract class MixinLightmap_LegacyLightmap {
    @Shadow
    @Final
    private GpuTextureView textureView;

    @Unique
    private final LegacyLightmapRenderer animatium$renderer = new LegacyLightmapRenderer();

    @WrapMethod(method = "render")
    private void animatium$legacyLightmap(final LightmapRenderState renderState, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.legacyLightmap) {
            this.animatium$renderer.render(((LightmapStateExtension) renderState).animatium$getState(), this.textureView);
        } else {
            original.call(renderState);
        }
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void animatium$legacyLightmap$close(final CallbackInfo ci) {
        this.animatium$renderer.close();
    }
}
