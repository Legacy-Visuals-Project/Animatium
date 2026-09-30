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

package org.visuals.legacy.animatium.handler.compatibility

enum class IrisPipeline {
    BASIC,
    TEXTURED,
    TERRAIN,
    TERRAIN_SOLID,
    TERRAIN_CUTOUT,
    TRANSLUCENT,
    SKY_BASIC,
    SKY_TEXTURED,
    ARMOR_GLINT,
    ENTITIES,
    ENTITIES_TRANSLUCENT,
    CLOUDS,
    BLOCK,
    BLOCK_TRANSLUCENT,
    HAND,
    HAND_TRANSLUCENT,
    PARTICLES,
    PARTICLES_TRANSLUCENT,
    EMISSIVE_ENTITIES,
    BEACON_BEAM,
    LINES;

    companion object {
        @JvmField
        val VALUES = entries.toTypedArray()
    }

    private var value: Enum<*>? = null

    fun <S : Enum<S>> initialize(clazz: Class<S>) {
        this.value = java.lang.Enum.valueOf(clazz, this.name)
    }

    fun internal() = this.value
}