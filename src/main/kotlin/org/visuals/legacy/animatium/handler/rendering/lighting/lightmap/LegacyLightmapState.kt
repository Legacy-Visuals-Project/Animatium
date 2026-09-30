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

class LegacyLightmapState {
    var needsUpdate: Boolean = false
    var skyDarken: Float = 0.0F
    var skyDarkness: Float = 0.0F
    var blockLightRed: Float = 0.0F
    var nightVisionScale: Float = 0.0F
    var gamma: Float = 0.0F
    var useBrightLightmap: Boolean = false
}