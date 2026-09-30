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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LegacyLightmapExtractor;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LegacyLightmapRenderer;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LegacyLightmapState;

@Mixin(LightTexture.class)
public abstract class MixinLightTexture_LegacyLightmap {
    @Shadow
    @Final
    private GpuTextureView textureView;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private float blockLightRedFlicker;

    @Unique
    private final LegacyLightmapExtractor animatium$extractor = new LegacyLightmapExtractor();

    @Unique
    private final LegacyLightmapRenderer animatium$renderer = new LegacyLightmapRenderer();

    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "floatValue=0.1"))
    private float animatium$legacyLightmap$changeFlickerDifference(final float original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.legacyLightmap) {
            return 1.0F;
        } else {
            return original;
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void animatium$legacyLightmap$tick(final CallbackInfo ci) {
        this.animatium$extractor.tick(this.blockLightRedFlicker);
    }

    @WrapMethod(method = "updateLightTexture")
    private void animatium$legacyLightmap(final float tickDelta, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.legacyLightmap) {
            final LegacyLightmapState state = new LegacyLightmapState();
            this.animatium$extractor.extract(this.minecraft, state, tickDelta);
            this.animatium$renderer.render(state, this.textureView);
        } else {
            original.call(tickDelta);
        }
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void animatium$legacyLightmap$close(final CallbackInfo ci) {
        this.animatium$renderer.close();
    }
}
