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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.LegacyFogDarkness;

@Mixin(FogRenderer.class)
public abstract class MixinFogRenderer_Darkness {
    @WrapOperation(method = "computeFogColor", at = {
            @At(value = "INVOKE", target = "Lorg/joml/Vector3fc;x()F"),
            @At(value = "INVOKE", target = "Lorg/joml/Vector3fc;y()F"),
            @At(value = "INVOKE", target = "Lorg/joml/Vector3fc;z()F"),
    })
    private float animatium$applyFogDarkness(final Vector3fc color, final Operation<Float> original, @Local(argsOnly = true, name = "partialTicks") final float tickDelta) {
        float component = original.call(color);
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.legacyFogDarkness) {
            component *= LegacyFogDarkness.getDarkness(tickDelta);
        }

        return component;
    }
}
