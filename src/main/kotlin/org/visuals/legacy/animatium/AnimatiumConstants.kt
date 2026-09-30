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

package org.visuals.legacy.animatium

import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey
import net.minecraft.client.renderer.block.model.BlockStateModel
import org.visuals.legacy.animatium.handler.networking.payloads.InfoPayload
import org.visuals.legacy.animatium.util.version.Version
import java.lang.Boolean.parseBoolean
import java.util.*

object AnimatiumConstants {
    const val MOD_ID = "@MODID@"
    const val DEVELOPMENT_VERSION = "@COMMIT@"

    @JvmField
    val VERSION = Version.parse("@VERSION@") ?: Version.BOGUS

    @JvmField
    val IS_DEVELOPMENT = parseBoolean("@DEVELOPMENT@")

    @JvmField
    val FAST_GRASS_MODEL_LOCATION = Animatium.location("block/fast_grass_block")

    @JvmField
    val FAST_GRASS_MODEL_KEY: ExtraModelKey<BlockStateModel> =
        ExtraModelKey.create(FAST_GRASS_MODEL_LOCATION::toString)

    @JvmField
    val INFO_PAYLOAD =
        InfoPayload(VERSION, if (IS_DEVELOPMENT) Optional.of(DEVELOPMENT_VERSION) else Optional.empty())
}