/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * Licensed under the PolyForm Shield License 1.0.0.
 * You may obtain a copy of the license at
 * https://polyformproject.org/licenses/shield/1.0.0
 */

package org.visuals.legacy.animatium.handler.networking.payloads

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import org.visuals.legacy.animatium.Animatium.location
import org.visuals.legacy.animatium.handler.server_features.ServerFeature
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures
import java.util.*

data class SetServerFeaturesPayload(val features: Set<ServerFeature>) : CustomPacketPayload {
    companion object {
        val TYPE = CustomPacketPayload.Type<SetServerFeaturesPayload>(location("set_server_features"))

        val FEATURE_SET_CODEC = object : StreamCodec<FriendlyByteBuf, Set<ServerFeature>> {
            override fun encode(
                buffer: FriendlyByteBuf,
                features: Set<ServerFeature>
            ) {
                val bitSet = BitSet()
                for (feature in features) {
                    bitSet.set(feature.raw)
                }

                buffer.writeBitSet(bitSet)
            }

            override fun decode(buffer: FriendlyByteBuf): Set<ServerFeature> {
                val bytes = ByteArray(buffer.readableBytes())
                buffer.readBytes(bytes)

                val bitSet = BitSet.valueOf(bytes)
                val features = hashSetOf<ServerFeature>()
                for (index in 0..<ServerFeatures.totalFeatures()) {
                    if (bitSet.get(index)) {
                        val feature = ServerFeatures.byRawId(index) ?: continue
                        if (feature == ServerFeatures.ALL) {
                            features.addAll(ServerFeatures.allFeatures())
                            break
                        }

                        features.add(feature)
                    }
                }

                return features
            }
        }

        val STREAM_CODEC = StreamCodec.composite(
            FEATURE_SET_CODEC,
            SetServerFeaturesPayload::features
        ) { features -> SetServerFeaturesPayload(features) }
    }

    override fun type() = TYPE
}