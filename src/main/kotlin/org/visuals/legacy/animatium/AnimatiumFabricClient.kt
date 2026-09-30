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

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import org.visuals.legacy.animatium.handler.AnimatiumKeybinds
import org.visuals.legacy.animatium.handler.command.AnimatiumCommand
import org.visuals.legacy.animatium.handler.networking.AnimatiumNetworking
import org.visuals.legacy.animatium.handler.rendering.updateOverlayTint

@Entrypoint
class AnimatiumFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        Animatium.initialize()

        val modContainer = FabricLoader.getInstance().getModContainer(AnimatiumConstants.MOD_ID)
            .orElseThrow({ RuntimeException("Mod container data could not be found for Animatium!") })
        for (pack in listOf("classic_textures", "classic_panorama", "classic_water")) {
            ResourceLoader.registerBuiltinPack(Animatium.location(pack), modContainer, PackActivationType.NORMAL)
        }

        ModelLoadingPlugin.register { context ->
            context.addModel(
                AnimatiumConstants.FAST_GRASS_MODEL_KEY,
                SimpleUnbakedExtraModel.blockStateModel(AnimatiumConstants.FAST_GRASS_MODEL_LOCATION)
            )
        }

        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ -> dispatcher.register(AnimatiumCommand.create()) }

        AnimatiumKeybinds.bootstrap()
        AnimatiumNetworking.bootstrap()

        Minecraft.getInstance().execute {
            updateOverlayTint() // Force update overlay for damageTintStyle
        }
    }
}