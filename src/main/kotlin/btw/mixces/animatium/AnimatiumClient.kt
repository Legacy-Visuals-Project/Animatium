package btw.mixces.animatium

import btw.mixces.animatium.command.AnimatiumCommand
import btw.mixces.animatium.config.AnimatiumConfig
import btw.mixces.animatium.packet.AnimatiumInfoPayloadPacket
import btw.mixces.animatium.packet.SetFeaturesPayloadPacket
import btw.mixces.animatium.util.Feature
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import java.io.File
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
        // TODO/NOTE: Find a better way/cleanup
        val modContainer: ModContainer =
            FabricLoader.getInstance().getModContainer("animatium").orElseThrow { RuntimeException("Mod not found") }
        val VERSION_FULL = modContainer.metadata.version.friendlyString.split("-")

        val VERSION = VERSION_FULL[0].toDouble()
        val DEVELOPMENT_VERSION: Optional<String> = Optional.ofNullable(VERSION_FULL[1])

        @JvmStatic
        fun getInfoPayload(): AnimatiumInfoPayloadPacket {
            return AnimatiumInfoPayloadPacket(VERSION, DEVELOPMENT_VERSION)
        }

        // Other
        val stateFile = File(FabricLoader.getInstance().gameDir.toFile(), "animatium_state.txt")
        fun saveEnabledState() {
            if (!stateFile.exists()) {
                stateFile.createNewFile()
            }

            stateFile.writeText(enabled.toString())
        }

        fun loadEnabledState() {
            if (stateFile.exists()) {
                enabled = stateFile.readText() == "true"
            } else {
                saveEnabledState()
            }
        }
    }

    override fun onInitializeClient() {
        AnimatiumConfig.load()
        loadEnabledState()

        // Commands
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, registryAccess ->
            AnimatiumCommand.register(dispatcher)
        }

        // Packets
        ClientLoginConnectionEvents.DISCONNECT.register { packet, client -> enabledFeatures.clear() }
        ClientConfigurationConnectionEvents.DISCONNECT.register { packet, client -> enabledFeatures.clear() }
        ClientPlayConnectionEvents.DISCONNECT.register { packet, client -> enabledFeatures.clear() }

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