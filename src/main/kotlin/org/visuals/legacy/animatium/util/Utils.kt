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
import net.minecraft.util.Mth
import net.minecraft.util.profiling.Profiler
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.GameType
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import org.visuals.legacy.animatium.AnimatiumConstants
import org.visuals.legacy.animatium.mixins.accessor.PlayerAccessor

fun toRadians(angle: Float) = angle * Mth.DEG_TO_RAD

fun expandVoxelShape(shape: VoxelShape, value: Double): VoxelShape {
    var voxelShape = Shapes.empty()
    shape.toAabbs().forEach { aabb ->
        voxelShape = Shapes.join(voxelShape, Shapes.create(aabb.inflate(value)), BooleanOp.OR)
    }
    return voxelShape
}

fun isSingleplayer(): Boolean {
    val server = Minecraft.getInstance().singleplayerServer
    return server != null && server.isSingleplayer
}

fun reinitializeInventorySlots() {
    val player = Minecraft.getInstance().player
    if (player != null && player.gameMode() != GameType.CREATIVE) {
        // Re-initialize the inventory, to reset the slot positions modified by "Old Crafting Slots Position"
        (player as PlayerAccessor).`animatium$setInventoryMenu`(
            InventoryMenu(
                player.inventory,
                !player.level().isClientSide,
                player
            )
        )
    }
}

fun profile(name: String, lambda: () -> Unit) {
    val profiler = Profiler.get()
    profiler.push("${AnimatiumConstants.MOD_ID}_$name")
    lambda()
    profiler.pop()
}