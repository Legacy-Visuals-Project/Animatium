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

package org.visuals.legacy.animatium.util.enums

import net.minecraft.util.ARGB
import org.visuals.legacy.animatium.config.AnimatiumConfig

enum class DamageTintSetting(private val colorGetter: (brightness: Float) -> Int) {
    V1_7({ brightness -> ARGB.colorFromFloat(0.6F, brightness, 0.0F, 0.0F) }),
    V1_8_ORANGE_MARSHALL(ARGB.colorFromFloat(0.5F, 1.0F, 0.0F, 0.0F)),
    CUSTOM({ brightness ->
        val color = AnimatiumConfig.instance().other.customTintColor
        ARGB.colorFromFloat(1.0F - (color.alpha / 255.0F), color.red / 255.0F, color.green / 255.0F, color.blue / 255.0F)
    }),
    VANILLA(-1);

    constructor(color: Int) : this({ color })

    fun getColor(brightness: Float) = this.colorGetter(brightness)
}