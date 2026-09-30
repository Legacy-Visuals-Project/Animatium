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

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.OptionsRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer_OldSkyRenderingCheck {
    @Shadow
    @Final
    private OptionsRenderState optionsRenderState;

    @WrapMethod(method = "shouldRenderSky")
    private boolean animatium$oldSkyRenderingCheck(final Operation<Boolean> original) {
        final boolean shouldRender = original.call();
        if (Animatium.isEnabled() && AnimatiumConfig.instance().fixes.oldSkyRenderingCheck) {
            return shouldRender && this.optionsRenderState.renderDistance >= 4;
        } else {
            return shouldRender;
        }
    }
}
