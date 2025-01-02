package me.mixces.animatium.packet

import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Identifier

class SetFeaturesPayloadPacket(val miss_penalty: Boolean, val left_click_item_usage: Boolean) : CustomPayload {
    companion object {
        val CODEC = CustomPayload.codecOf(null, SetFeaturesPayloadPacket::read)
        val PAYLOAD_ID =
            CustomPayload.Id<SetFeaturesPayloadPacket>(Identifier.of("animatium:set_features"))

        private fun read(buffer: PacketByteBuf): SetFeaturesPayloadPacket {
            return SetFeaturesPayloadPacket(buffer.readBoolean(), buffer.readBoolean())
        }
    }

    override fun getId(): CustomPayload.Id<out CustomPayload> {
        return PAYLOAD_ID
    }
}