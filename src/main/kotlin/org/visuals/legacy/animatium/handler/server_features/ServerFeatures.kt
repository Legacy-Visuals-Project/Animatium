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

package org.visuals.legacy.animatium.handler.server_features

import net.minecraft.resources.Identifier
import org.visuals.legacy.animatium.Animatium.location

object ServerFeatures {
    private val REGISTRY = hashMapOf<Identifier, ServerFeature>()
    private var LAST_ID = 0

    @JvmField
    val ALL = register(location("all")) // Internal use, on read in packet, adds everything in the REGISTRY

    @JvmField
    val MISS_PENALTY = register(location("miss_penalty"))

    @JvmField
    val LEFT_CLICK_ITEM_USAGE = register(location("left_click_item_usage"))

    @JvmField
    val MINING_ITEM_USAGE = register(location("mining_item_usage"))

    @JvmField
    val HIDE_FIRST_PERSON_ROD_BOBBER = register(location("hide_rod_bobber"))

    @JvmField
    val PICK_INFLATION = register(location("pick_inflation"))

    @JvmField
    val OLD_SNEAK_HEIGHT = register(location("old_sneak_height"))

    @JvmField
    val CLIENTSIDE_ENTITIES = register(location("clientside_entities"))

    @JvmField
    val FIX_SPRINT_ITEM_USE = register(location("disable_sprint_item_use"))

    @JvmField
    val FIX_SPRINT_SNEAKING = register(location("disable_sprint_sneaking"))

    @JvmStatic
    fun allFeatures() = REGISTRY.values.toList()

    @JvmStatic
    fun totalFeatures() = REGISTRY.size

    @JvmStatic
    fun byRawId(raw: Int): ServerFeature? {
        for (entry in REGISTRY) {
            val feature = entry.value
            if (feature.raw == raw) {
                return feature
            }
        }

        return null
    }

    private fun register(id: Identifier): ServerFeature {
        val feature = ServerFeature(id, LAST_ID++)
        REGISTRY[id] = feature
        return feature
    }
}