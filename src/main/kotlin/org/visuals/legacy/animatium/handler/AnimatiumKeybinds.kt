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

package org.visuals.legacy.animatium.handler

import com.mojang.blaze3d.platform.InputConstants
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import org.visuals.legacy.animatium.Animatium
import org.visuals.legacy.animatium.Animatium.location
import org.visuals.legacy.animatium.config.AnimatiumConfig
import java.util.function.Consumer

object AnimatiumKeybinds {
    private val REGISTRY = arrayListOf<Binding>()
    private val ANIMATIUM_CATEGORY = KeyMapping.Category(location("common"))

    val CONFIG_SCREEN = create(
        "Open Mod Configuration",
        InputConstants.KEY_BACKSLASH
    ) { client -> client.setScreen(AnimatiumConfig.getConfigScreen(client.screen)) }

    val RELOAD = create(
        "Reload Mod",
        InputConstants.KEY_END
    ) { client -> Animatium.reload() }

    fun bootstrap() {
        ClientTickEvents.END_CLIENT_TICK.register { tick(it) }
        for (binding in REGISTRY) {
            KeyMappingHelper.registerKeyMapping(binding.mapping)
        }
    }

    private fun tick(minecraft: Minecraft) {
        for (binding in REGISTRY) {
            if (binding.mapping.consumeClick()) {
                minecraft.schedule {
                    binding.onClick.accept(minecraft)
                }
            }
        }
    }

    private fun create(
        name: String,
        keybind: Int,
        onClick: Consumer<Minecraft>
    ): KeyMapping {
        val mapping = KeyMapping(name, keybind, ANIMATIUM_CATEGORY)
        REGISTRY.add(Binding(mapping, onClick))
        return mapping
    }

    private data class Binding(val mapping: KeyMapping, val onClick: Consumer<Minecraft>)
}