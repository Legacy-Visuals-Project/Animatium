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

package org.visuals.legacy.animatium.handler.rendering.lighting.lightmap

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.util.profiling.Profiler
import net.minecraft.world.effect.MobEffects
import org.visuals.legacy.animatium.util.getLegacySkyDarken

class LegacyLightmapExtractor {
    private var needsUpdate: Boolean = false
    private var blockLightRed: Float = 0.0F

    fun tick(blockLightFlicker: Float) {
        this.blockLightRed += blockLightFlicker - this.blockLightRed
        this.needsUpdate = true
    }

    fun extract(minecraft: Minecraft, state: LegacyLightmapState, tickDelta: Float) {
        state.needsUpdate = this.needsUpdate
        if (this.needsUpdate) {
            val level = minecraft.level ?: return
            val player = minecraft.player ?: return

            val profiler = Profiler.get()
            profiler.push("lightmap")
            state.skyDarken = level.getLegacySkyDarken()
            state.blockLightRed = this.blockLightRed
            state.skyDarkness = minecraft.gameRenderer.getDarkenWorldAmount(tickDelta)
            if (player.hasEffect(MobEffects.NIGHT_VISION)) {
                state.nightVisionScale = GameRenderer.getNightVisionScale(player, tickDelta)
            }

            state.gamma = minecraft.options.gamma().get().toFloat()
            state.useBrightLightmap = level.dimension() == ClientLevel.END
            profiler.pop()

            this.needsUpdate = false
        }
    }
}