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

package org.visuals.legacy.animatium.util.version

import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import org.visuals.legacy.animatium.AnimatiumConstants
import java.lang.Byte.parseByte

data class Version(val major: Byte, val minor: Byte, val patch: Byte) {
    val packedValue = (this.major.toInt() shl MAJOR) or (this.minor.toInt() shl MINOR) or this.patch.toInt()

    companion object {
        const val MAJOR = 8
        const val MINOR = 16

        val BOGUS = Version(42, 69, 67)

        val STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            Version::packedValue
        ) { unpack(it) }

        fun unpack(value: Int) = Version(
            ((value shr MAJOR) and 0xFF).toByte(),
            ((value shr MINOR) and 0xFF).toByte(),
            (value and 0xFF).toByte()
        )

        fun parse(input: String): Version? {
            if (input.isBlank()) return null

            val parts = input.split('.')
            if (parts.size > 3) return null

            try {
                val major = parts[0]
                val minor = parts[1]
                val patch = if (parts.size == 3) parts[2] else "0"
                return Version(parseByte(major), parseByte(minor), parseByte(patch))
            } catch (_: Exception) {
                return null
            }
        }
    }

    override fun toString() = if (this.patch > 0) {
        "$major.$minor.$patch"
    } else {
        "$major.$minor"
    } + (if (AnimatiumConstants.IS_DEVELOPMENT) " (${this.packedValue})" else "")
}