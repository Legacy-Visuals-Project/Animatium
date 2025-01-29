/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2025 lowercasebtw
 * Copyright (C) 2024-2025 mixces
 * Copyright (C) 2024-2025 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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