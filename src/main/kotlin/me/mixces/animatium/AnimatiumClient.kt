package me.mixces.animatium

import me.mixces.animatium.command.AnimatiumCommand
import me.mixces.animatium.config.AnimatiumConfig
import me.mixces.animatium.packet.SetFeaturesPayloadPacket
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry

class AnimatiumClient : ClientModInitializer {
    companion object {
        @JvmStatic
        var disableSwingMissPenalty = false

        @JvmStatic
        var leftClickItemUsage = false
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
        PayloadTypeRegistry.playS2C().register(SetFeaturesPayloadPacket.PAYLOAD_ID, SetFeaturesPayloadPacket.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(SetFeaturesPayloadPacket.PAYLOAD_ID) { payload, context ->
            context.client().execute {
                disableSwingMissPenalty = payload.miss_penalty
                leftClickItemUsage = payload.left_click_item_usage
            }
        }
    }
}