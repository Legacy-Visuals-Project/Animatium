package btw.mixces.animatium.packet

import btw.mixces.animatium.AnimatiumClient
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

class SetFeaturesPayloadPacket(val missPenalty: Boolean, val leftClickItemUsage: Boolean) : CustomPacketPayload {
    companion object {
        val CODEC = CustomPacketPayload.codec(null, SetFeaturesPayloadPacket::read)
        val PAYLOAD_ID = CustomPacketPayload.Type<SetFeaturesPayloadPacket>(AnimatiumClient.location("set_features"))

        private fun read(buffer: FriendlyByteBuf): SetFeaturesPayloadPacket {
            return SetFeaturesPayloadPacket(buffer.readBoolean(), buffer.readBoolean())
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return PAYLOAD_ID
    }
}