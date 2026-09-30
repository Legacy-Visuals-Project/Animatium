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

import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LegacyLightmapState;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LightmapStateExtension;

@Mixin(LightmapRenderState.class)
public abstract class MixinLightmapRenderState_StoreLegacy implements LightmapStateExtension {
    @Unique
    private LegacyLightmapState animatium$legacyState;

    @Override
    public LegacyLightmapState animatium$getState() {
        return this.animatium$legacyState;
    }

    @Override
    public void animatium$setState(final LegacyLightmapState state) {
        this.animatium$legacyState = state;
    }
}
