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

package org.visuals.legacy.animatium.handler.screen.debug

import net.minecraft.client.gui.components.debug.DebugEntryCategory
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer
import net.minecraft.client.gui.components.debug.DebugScreenEntry
import net.minecraft.network.chat.Component
import net.minecraft.world.level.Level
import net.minecraft.world.level.chunk.LevelChunk
import org.visuals.legacy.animatium.Animatium
import org.visuals.legacy.animatium.AnimatiumConstants
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures

class AnimatiumDebugEntry : DebugScreenEntry {
    companion object {
        val CATEGORY = DebugEntryCategory(Component.translatable("animatium.category.debug"), Float.MAX_VALUE)
        val GROUP = Animatium.location("debug")
    }

    override fun display(
        displayer: DebugScreenDisplayer,
        serverOrClientLevel: Level?,
        clientChunk: LevelChunk?,
        serverChunk: LevelChunk?
    ) {
        val list = arrayListOf<String>()
        list.add("Animatium " + AnimatiumConstants.VERSION + (if (AnimatiumConstants.IS_DEVELOPMENT) " - Development Version (" + AnimatiumConstants.DEVELOPMENT_VERSION + ")" else ""))
        if (ServerFeatureManager.ENABLED_SERVER_FEATURES.isNotEmpty()) {
            list.add("Enabled Server Features:")
            for (feature in ServerFeatureManager.ENABLED_SERVER_FEATURES) {
                if (feature != ServerFeatures.ALL) {
                    list.add(" - " + feature.identifier.path)
                }
            }
        }

        displayer.addToGroup(GROUP, list)
    }

    override fun isAllowed(reducedDebugInfo: Boolean) = true

    override fun category() = CATEGORY
}