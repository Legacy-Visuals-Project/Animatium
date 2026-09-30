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

package org.visuals.legacy.animatium.util.states

interface CameraUtilityRenderState {
    fun `animatium$getPartialTickTime`(): Float {
        throw UnsupportedOperationException()
    }

    fun `animatium$setPartialTickTime`(partialTickTime: Float) {
        throw UnsupportedOperationException()
    }

    fun `animatium$getOldEyeHeight`(): Float {
        throw UnsupportedOperationException()
    }

    fun `animatium$setOldEyeHeight`(oldEyeHeight: Float) {
        throw UnsupportedOperationException()
    }

    fun `animatium$getEyeHeight`(): Float {
        throw UnsupportedOperationException()
    }

    fun `animatium$setEyeHeight`(eyeHeight: Float) {
        throw UnsupportedOperationException()
    }

    fun `animatium$getYRot`(): Float {
        throw UnsupportedOperationException()
    }

    fun `animatium$setYRot`(yRot: Float) {
        throw UnsupportedOperationException()
    }

    fun `animatium$getXRot`(): Float {
        throw UnsupportedOperationException()
    }

    fun `animatium$setXRot`(xRot: Float) {
        throw UnsupportedOperationException()
    }
}