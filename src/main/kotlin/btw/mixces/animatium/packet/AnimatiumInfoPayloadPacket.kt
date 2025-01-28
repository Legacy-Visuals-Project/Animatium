package btw.mixces.animatium.packet

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import java.util.Optional

class AnimatiumInfoPayloadPacket(val version: Double, val developmentVersion: Optional<String>) : CustomPacketPayload {
    companion object {
        val CODEC = CustomPacketPayload.codec(AnimatiumInfoPayloadPacket::write, null)
        val PAYLOAD_ID = CustomPacketPayload.Type<AnimatiumInfoPayloadPacket>(ResourceLocation.parse("animatium:info"))
    }

    private fun write(buffer: FriendlyByteBuf) {
        buffer.writeDouble(version)
        buffer.writeOptional<String>(developmentVersion) { buf, value ->
            buf.writeUtf(value)
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return PAYLOAD_ID
    }
}