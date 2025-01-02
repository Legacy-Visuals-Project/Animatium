package me.mixces.animatium.packet

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation

class SetFeaturesPayloadPacket(val miss_penalty: Boolean, val left_click_item_usage: Boolean) : CustomPacketPayload {
    companion object {
        val CODEC = CustomPacketPayload.codec(null, SetFeaturesPayloadPacket::read)
        val PAYLOAD_ID =
            CustomPacketPayload.Type<SetFeaturesPayloadPacket>(ResourceLocation.parse("animatium:set_features"))

        private fun read(buffer: FriendlyByteBuf): SetFeaturesPayloadPacket {
            return SetFeaturesPayloadPacket(buffer.readBoolean(), buffer.readBoolean())
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return PAYLOAD_ID
    }
}