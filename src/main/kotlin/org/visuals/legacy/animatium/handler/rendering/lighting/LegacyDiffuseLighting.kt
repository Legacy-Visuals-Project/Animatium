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

package org.visuals.legacy.animatium.handler.rendering.lighting

import com.mojang.blaze3d.platform.Lighting
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector3fc
import org.visuals.legacy.animatium.Animatium
import org.visuals.legacy.animatium.config.AnimatiumConfig
import java.util.function.BiConsumer

object LegacyDiffuseLighting {
    private val DIFFUSE_LIGHT_0 = (Vector3f(0.2F, 1.0F, -0.7F)).normalize()
    private val DIFFUSE_LIGHT_1 = (Vector3f(-0.2F, 1.0F, 0.7F)).normalize()

    private val INVENTORY_DIFFUSE_LIGHT_0 = (Vector3f(0.2F, -1.0F, 1.0F)).normalize()
    private val INVENTORY_DIFFUSE_LIGHT_1 = (Vector3f(-0.2F, -1.0F, 0.0F)).normalize()

    @JvmStatic
    var item3dPose: Matrix4f? = null

    @JvmStatic
    var updateLightingInvoker: BiConsumer<Lighting.Entry, Lights>? = null

    @JvmStatic
    fun refresh() {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.legacyDiffuseLighting) {
            val lights = Lights(
                item3dPose!!.transformDirection(DIFFUSE_LIGHT_0, Vector3f()),
                item3dPose!!.transformDirection(DIFFUSE_LIGHT_1, Vector3f())
            )
            updateLightingInvoker!!.accept(Lighting.Entry.ENTITY_IN_UI, lights)
            updateLightingInvoker!!.accept(Lighting.Entry.PLAYER_SKIN, lights)
        } else {
            // Vanilla Values (TODO/NOTE, Possibly a nicer way to do this?)
            updateLightingInvoker!!.accept(
                Lighting.Entry.ENTITY_IN_UI,
                Lights(INVENTORY_DIFFUSE_LIGHT_0, INVENTORY_DIFFUSE_LIGHT_1)
            )

            val playerSkinPose = Matrix4f()
            updateLightingInvoker!!.accept(
                Lighting.Entry.PLAYER_SKIN,
                Lights(
                    playerSkinPose.transformDirection(INVENTORY_DIFFUSE_LIGHT_0, Vector3f()),
                    playerSkinPose.transformDirection(INVENTORY_DIFFUSE_LIGHT_1, Vector3f())
                )
            )
        }
    }

    data class Lights(
        @JvmField
        val light0: Vector3fc,

        @JvmField
        val light1: Vector3fc
    )
}