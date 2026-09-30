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

import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.state.level.CameraRenderState
import net.minecraft.util.Mth
import org.visuals.legacy.animatium.mixins.accessor.CameraAccessor

fun Camera.getPositionLerped(): Float {
    val cameraAccessor = this as CameraAccessor
    return Mth.lerp(
        this.getCameraEntityPartialTicks(Minecraft.getInstance().deltaTracker),
        cameraAccessor.`animatium$getOldEyeHeight`(),
        cameraAccessor.`animatium$getEyeHeight`()
    )
}

fun CameraRenderState.getPositionLerped() = Mth.lerp(
    this.`animatium$getPartialTickTime`(),
    this.`animatium$getOldEyeHeight`(),
    this.`animatium$getEyeHeight`()
)