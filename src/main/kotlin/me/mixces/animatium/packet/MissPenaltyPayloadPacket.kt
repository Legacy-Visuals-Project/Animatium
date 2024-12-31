package me.mixces.animatium.packet

import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Identifier

class MissPenaltyPayloadPacket(val value: Boolean) : CustomPayload {
    companion object {
        val CODEC = CustomPayload.codecOf<PacketByteBuf, MissPenaltyPayloadPacket>(
            MissPenaltyPayloadPacket::write
        ) { buf ->
            MissPenaltyPayloadPacket(buf.readBoolean())
        }

        val PAYLOAD_ID =
            CustomPayload.Id<MissPenaltyPayloadPacket>(Identifier.of("animatium:set_swing_miss_penalty"))
    }

    private fun write(buffer: PacketByteBuf) {
    }

    override fun getId(): CustomPayload.Id<out CustomPayload?>? {
        return PAYLOAD_ID
    }
}