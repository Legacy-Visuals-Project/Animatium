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

package org.visuals.legacy.animatium

import com.mojang.logging.LogUtils
import net.minecraft.ChatFormatting
import net.minecraft.SharedConstants
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.debug.DebugScreenEntries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import org.visuals.legacy.animatium.config.AnimatiumConfig
import org.visuals.legacy.animatium.handler.rendering.lighting.LegacyDiffuseLighting
import org.visuals.legacy.animatium.handler.screen.debug.AnimatiumDebugEntry
import org.visuals.legacy.animatium.util.ToastUtil
import org.visuals.legacy.animatium.util.config.GeneralConfigUtil
import org.visuals.legacy.animatium.util.reinitializeInventorySlots

object Animatium {
    private val LOGGER = LogUtils.getLogger()

    @JvmStatic
    var enabled = true
        set(value) {
            field = value
            GeneralConfigUtil.put(GeneralConfigUtil.ENABLED_KEY, value)
        }

    @JvmStatic
    fun isEnabled(): Boolean = enabled

    @JvmStatic
    fun reload() {
        val minecraft = Minecraft.getInstance()
        minecraft.levelRenderer.allChanged()
        LegacyDiffuseLighting.refresh()
        reinitializeInventorySlots()
        ToastUtil.send(Component.literal("Mod reloaded.").withStyle(ChatFormatting.GREEN))
    }

    @JvmStatic
    fun location(path: String) = Identifier.fromNamespaceAndPath(AnimatiumConstants.MOD_ID, path)

    @JvmStatic
    fun initialize() {
        if (AnimatiumConstants.IS_DEVELOPMENT) {
            SharedConstants.IS_RUNNING_IN_IDE = true
        }

        AnimatiumConfig.load()
        try {
            GeneralConfigUtil.load()
            LOGGER.info("Successfully loaded the animatium utility config!")
        } catch (_: Exception) {
            enabled = GeneralConfigUtil.getBoolean(GeneralConfigUtil.ENABLED_KEY)
            LOGGER.error("Failed to load animatium utility config, defaulting...")
        }

        DebugScreenEntries.register(AnimatiumDebugEntry.GROUP, AnimatiumDebugEntry())
    }
}