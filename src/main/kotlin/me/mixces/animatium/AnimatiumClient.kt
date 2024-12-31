package me.mixces.animatium

import me.mixces.animatium.command.AnimatiumCommand
import me.mixces.animatium.config.AnimatiumConfig
import me.mixces.animatium.packet.MissPenaltyPayloadPacket
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.client.MinecraftClient

class AnimatiumClient : ClientModInitializer {
    companion object {
        @JvmStatic
        var disableSwingMissPenalty = false

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
        initializePackets();
    }

    private fun initializeCommands() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, registryAccess ->
            AnimatiumCommand.register(dispatcher)
        }
    }

    private fun initializePackets() {
        PayloadTypeRegistry.playS2C().register(MissPenaltyPayloadPacket.PAYLOAD_ID, MissPenaltyPayloadPacket.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(MissPenaltyPayloadPacket.PAYLOAD_ID) { payload, context ->
            context.client().execute {
                disableSwingMissPenalty = payload.value
            }
        }
    }
}