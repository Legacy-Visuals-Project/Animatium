package me.mixces.animatium

import me.mixces.animatium.command.AnimatiumCommand
import me.mixces.animatium.config.AnimatiumConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.minecraft.client.MinecraftClient

class AnimatiumClient : ClientModInitializer {
    companion object {
        fun isLegacySupportedVersion(): Boolean {
            val client = MinecraftClient.getInstance()
            val networkHandler = client.networkHandler ?: return false
            val brand = networkHandler.brand?.lowercase() ?: return false
            return if (brand.contains("1.8")) {
                true
            } else {
                val serverInfo = networkHandler.serverInfo
                serverInfo != null &&
                        (serverInfo.address.contains("loyisa") ||
                                serverInfo.address.contains("bedwarspractice") ||
                                serverInfo.address.contains("bridger.land"))
            }
        }
    }

    override fun onInitializeClient() {
        AnimatiumConfig.load()
        initializeCommands()
    }

    private fun initializeCommands() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, registryAccess ->
            AnimatiumCommand.register(dispatcher)
        }
    }
}