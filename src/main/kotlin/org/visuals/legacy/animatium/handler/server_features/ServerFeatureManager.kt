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

import org.visuals.legacy.animatium.handler.config.bundle.ConfigBundles
import org.visuals.legacy.animatium.util.isSingleplayer

object ServerFeatureManager {
    @JvmField
    val ENABLED_SERVER_FEATURES: HashSet<ServerFeature> = hashSetOf()

    @JvmStatic
    fun isPresent(feature: ServerFeature): Boolean {
        if (isSingleplayer()) {
            for (entry in ConfigBundles.EXTRAS.entries()) {
                if (entry.name() == feature.identifier.path) {
                    return entry.value() as Boolean
                }
            }

            return false
        } else {
            return ENABLED_SERVER_FEATURES.contains(feature)
        }
    }
}