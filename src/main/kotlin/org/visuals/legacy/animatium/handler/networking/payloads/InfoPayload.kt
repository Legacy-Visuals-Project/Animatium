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

import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import org.visuals.legacy.animatium.Animatium.location
import org.visuals.legacy.animatium.util.version.Version
import java.util.*

data class InfoPayload(val version: Version, val developmentVersion: Optional<String>) : CustomPacketPayload {
    companion object {
        val TYPE = CustomPacketPayload.Type<InfoPayload>(location("info"))

        val STREAM_CODEC = StreamCodec.composite(
            Version.STREAM_CODEC,
            InfoPayload::version,
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8),
            InfoPayload::developmentVersion
        ) { version, developmentVersion -> InfoPayload(version, developmentVersion) }
    }

    override fun type() = TYPE
}