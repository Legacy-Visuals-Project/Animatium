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

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(AtmosphericFogEnvironment.class)
public abstract class MixinAtmosphericFogEnvironment_OldCloudRendering {
    @Expression("(float) (? * 16)")
    @ModifyExpressionValue(method = "setupFog", at = @At("MIXINEXTRAS:EXPRESSION"))
    private float animatium$oldCloudFogDistance(final float original, @Local(argsOnly = true, name = "renderDistance") final float renderDistance) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldCloudRendering) {
            return renderDistance;
        } else {
            return original;
        }
    }
}
