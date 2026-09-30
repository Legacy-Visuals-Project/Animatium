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

package org.visuals.legacy.animatium.mixins.v1.gui.screen_tweaks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(SplashRenderer.class)
public abstract class MixinSplashRenderer_LegacySplashPosition {
    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "floatValue=123.0"))
    private float animatium$legacySplashPosition(final float original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.legacySplashPosition) {
            return 90.0F;
        } else {
            return original;
        }
    }
}
