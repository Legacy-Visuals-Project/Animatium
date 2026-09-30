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

package org.visuals.legacy.animatium.mixins.v1.rendering.states;

import net.minecraft.client.renderer.state.level.SkyRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.visuals.legacy.animatium.util.states.SkyUtilityState;

@Mixin(SkyRenderState.class)
public abstract class MixinSkyRenderState implements SkyUtilityState {
    @Unique
    private double animatium$height = 0.0D;

    @Override
    public double animatium$getHorizonHeight() {
        return this.animatium$height;
    }

    @Override
    public void animatium$setHorizonHeight(final double height) {
        this.animatium$height = height;
    }
}
