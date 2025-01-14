package btw.mixces.animatium

import btw.mixces.animatium.command.AnimatiumCommand
import btw.mixces.animatium.config.AnimatiumConfig
import btw.mixces.animatium.packet.AnimatiumInfoPayloadPacket
import btw.mixces.animatium.packet.SetFeaturesPayloadPacket
import btw.mixces.animatium.util.Feature
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import java.util.Optional

class AnimatiumClient : ClientModInitializer {
    companion object {
        // Settings
        @JvmStatic
        var enabled = true

        @JvmStatic
        val enabledFeatures = arrayListOf<Feature>()

        @JvmStatic
        var shouldReloadOverlayTexture = false

        @JvmStatic
        fun reloadOverlayTexture() {
            shouldReloadOverlayTexture = true
        }

        // Info
        const val VERSION = 1.0
        val DEVELOPMENT_VERSION = Optional.ofNullable(34)

        @JvmStatic
        fun getInfoPayload(): AnimatiumInfoPayloadPacket {
            return AnimatiumInfoPayloadPacket(VERSION, DEVELOPMENT_VERSION)
        }
    }

    override fun onInitializeClient() {
        AnimatiumConfig.load()

        // Commands
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, registryAccess ->
            AnimatiumCommand.register(dispatcher)
        }

        // Packets
        PayloadTypeRegistry.playC2S().register(AnimatiumInfoPayloadPacket.PAYLOAD_ID, AnimatiumInfoPayloadPacket.CODEC)
        PayloadTypeRegistry.playS2C().register(SetFeaturesPayloadPacket.PAYLOAD_ID, SetFeaturesPayloadPacket.CODEC)
        ClientPlayNetworking.registerGlobalReceiver(SetFeaturesPayloadPacket.PAYLOAD_ID) { payload, context ->
            context.client().execute {
                enabledFeatures.clear()
                for (feature in payload.features) {
                    enabledFeatures.add(feature)
                }
            }
        }
    }
}