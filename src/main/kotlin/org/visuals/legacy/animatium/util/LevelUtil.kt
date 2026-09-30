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

package org.visuals.legacy.animatium.util

import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.util.Mth
import net.minecraft.world.level.Level
import org.visuals.legacy.animatium.mixins.accessor.ClientLevelDataAccessor
import kotlin.math.cos

fun ClientLevel.hasVoidFog(): Boolean {
    val levelDataAccessor = this.getLevelData() as ClientLevelDataAccessor
    return !levelDataAccessor.`animatium$isFlatWorld`() &&
            !this.dimensionType().hasCeiling() // "isDark" method from 1.7/1.8
}

fun ClientLevel.getLegacySkyDarken(): Float {
    var value = 1.0F - (Mth.cos((this.getTimeOfDay(1.0F) * (Math.PI * 2).toFloat()).toDouble()) * 2.0F + 0.2F)
    value = Mth.clamp(value, 0.0F, 1.0F)
    value = 1.0F - value
    value *= 1.0F - this.getRainLevel(1.0F) * 5.0F / 16.0F
    value *= 1.0F - this.getThunderLevel(1.0F) * 5.0F / 16.0F
    return value * 0.8F + 0.2F
}

fun ClientLevel.getLegacyFixedTime(): Long? {
    if (this.dimensionType().hasFixedTime()) {
        val dimension = this.dimension()
        if (dimension == Level.NETHER) {
            return 18000L
        } else if (dimension == Level.END) {
            return 6000L
        }
    }

    return null
}

fun ClientLevel.getTimeOfDay(tickDelta: Float): Float {
    var dayTime = this.getLegacyFixedTime() ?: this.overworldClockTime
    if (dayTime == 0L) {
        dayTime = 1L // 1.8 never lets the tick time be 0
    }

    val time = Math.toIntExact(dayTime % 24000L)

    var frac = (time + tickDelta) / 24000.0F - 0.25F
    if (frac < 0.0F) {
        ++frac
    }

    if (frac > 1.0F) {
        --frac
    }

    val mul = 1.0F - ((cos(frac * Math.PI) + 1.0) / 2.0).toFloat()
    frac += (mul - frac) / 3.0F
    return frac
}