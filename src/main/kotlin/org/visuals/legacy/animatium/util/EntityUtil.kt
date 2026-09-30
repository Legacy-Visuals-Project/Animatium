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

package org.visuals.legacy.animatium.util

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.state.AvatarRenderState
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import java.util.*

/**
 * Can always safely assume that if this returns true, the provided render-state is AvatarRenderState
 *
 * @return Entity id matches the client player id
 */
fun LivingEntityRenderState.isSelf(): Boolean {
    val player = Minecraft.getInstance().player
    return player != null && this is AvatarRenderState && this.id == player.id
}

fun Entity?.isSelf(): Boolean {
    val player = Minecraft.getInstance().player
    return player != null && this != null && this.id == player.id
}

fun Player.getPosWithEyeHeight(tickDelta: Float, eyeHeight: Double) =
    this.getPosition(tickDelta).add(0.0, eyeHeight, 0.0)

fun Entity.getScale() = if (this is LivingEntity) this.scale else 1.0F