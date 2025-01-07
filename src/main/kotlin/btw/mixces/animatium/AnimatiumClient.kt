package btw.mixces.animatium

import btw.mixces.animatium.command.AnimatiumCommand
import btw.mixces.animatium.config.AnimatiumConfig
import btw.mixces.animatium.packet.AnimatiumInfoPayloadPacket
import btw.mixces.animatium.packet.SetFeaturesPayloadPacket
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.resources.ResourceLocation
import java.util.Optional

class AnimatiumClient : ClientModInitializer {
    companion object {
        // Settings
        @JvmStatic
        var disableSwingMissPenalty = false

        @JvmStatic
        var leftClickItemUsage = false

        @JvmStatic
        var shouldReloadOverlayTexture = false

        @JvmStatic
        fun reloadOverlayTexture() {
            shouldReloadOverlayTexture = true
        }

        // Info
        @JvmStatic
        val VERSION = 1.0
        val DEVELOPMENT_VERSION = Optional.ofNullable(0.0)

        @JvmStatic
        fun location(path: String): ResourceLocation {
            return ResourceLocation.parse("animatium:$path")
        }

        @JvmStatic
        fun getInfoPayload(): AnimatiumInfoPayloadPacket {
            return AnimatiumInfoPayloadPacket(VERSION, DEVELOPMENT_VERSION)
        }

        @JvmStatic
        var enabled = true
    }

    override fun onInitializeClient() {
        AnimatiumConfig.Companion.load()
        initializeCommands()
        initializePackets()
    }

    private fun initializeCommands() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, registryAccess ->
            AnimatiumCommand.Companion.register(dispatcher)
        }
    }

    private fun initializePackets() {
        PayloadTypeRegistry.playC2S().register(AnimatiumInfoPayloadPacket.Companion.PAYLOAD_ID, AnimatiumInfoPayloadPacket.Companion.CODEC)

        PayloadTypeRegistry.playS2C().register(SetFeaturesPayloadPacket.Companion.PAYLOAD_ID, SetFeaturesPayloadPacket.Companion.CODEC)

        ClientPlayNetworking.registerGlobalReceiver(SetFeaturesPayloadPacket.Companion.PAYLOAD_ID) { payload, context ->
            context.client().execute {
                disableSwingMissPenalty = payload.miss_penalty
                leftClickItemUsage = payload.left_click_item_usage
            }
        }
    }
}