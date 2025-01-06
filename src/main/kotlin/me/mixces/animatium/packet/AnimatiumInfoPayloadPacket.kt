package me.mixces.animatium.packet

import me.mixces.animatium.AnimatiumClient
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import java.util.Optional

class AnimatiumInfoPayloadPacket(val version: Double, val development_version: Optional<Double>) :
    CustomPacketPayload {
    companion object {
        val CODEC = CustomPacketPayload.codec(AnimatiumInfoPayloadPacket::write, null)
        val PAYLOAD_ID =
            CustomPacketPayload.Type<AnimatiumInfoPayloadPacket>(AnimatiumClient.location("info"))
    }

    private fun write(buffer: FriendlyByteBuf) {
        buffer.writeDouble(version)
        buffer.writeOptional<Double>(development_version) { buf, value ->
            buf.writeDouble(value)
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return PAYLOAD_ID
    }
}