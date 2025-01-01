package me.mixces.animatium.packet

import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Identifier

class MissPenaltyPayloadPacket(val value: Boolean) : CustomPayload {
    companion object {
        val CODEC = CustomPayload.codecOf(null, MissPenaltyPayloadPacket::read)
        val PAYLOAD_ID =
            CustomPayload.Id<MissPenaltyPayloadPacket>(Identifier.of("animatium:disable_swing_miss_penalty"))

        private fun read(buffer: PacketByteBuf): MissPenaltyPayloadPacket {
            return MissPenaltyPayloadPacket(buffer.readBoolean())
        }
    }

    override fun getId(): CustomPayload.Id<out CustomPayload> {
        return PAYLOAD_ID
    }
}