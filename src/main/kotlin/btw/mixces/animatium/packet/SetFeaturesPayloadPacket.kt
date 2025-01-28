package btw.mixces.animatium.packet

import btw.mixces.animatium.util.Feature
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation

class SetFeaturesPayloadPacket(val features: List<Feature>) : CustomPacketPayload {
    companion object {
        val CODEC = CustomPacketPayload.codec(null, SetFeaturesPayloadPacket::read)
        val PAYLOAD_ID =
            CustomPacketPayload.Type<SetFeaturesPayloadPacket>(ResourceLocation.parse("animatium:set_features"))

        private fun read(buffer: FriendlyByteBuf): SetFeaturesPayloadPacket {
            val features = arrayListOf<Feature>()
            val size = buffer.readVarInt()
            for (index in 1..size) {
                features.add(Feature.byId(buffer.readUtf()) ?: continue)
            }
            return SetFeaturesPayloadPacket(features)
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return PAYLOAD_ID
    }
}