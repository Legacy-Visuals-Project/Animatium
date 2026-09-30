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

package org.visuals.legacy.animatium.handler.rendering

import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity

object LegacyFogDarkness {
    private var prevDarkness: Float = 0.0F
    private var darkness: Float = 0.0F

    @JvmStatic
    fun tick(entity: Entity, renderDistance: Int) {
        val brightness = entity.level().getLightLevelDependentMagicValue(entity.blockPosition())
        this.prevDarkness = this.darkness
        this.darkness = Mth.lerp(0.1F, this.darkness, Mth.lerp(renderDistance / 32.0F, brightness, 1.0F))
    }

    @JvmStatic
    fun getDarkness(tickDelta: Float) = Mth.lerp(tickDelta, this.prevDarkness, this.darkness)
}