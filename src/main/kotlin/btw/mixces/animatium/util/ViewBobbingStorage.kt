package btw.mixces.animatium.util

interface ViewBobbingStorage {
    // Bobbing Tilt
    fun `animatium$setBobbingTilt`(bobbingTilt: Float)

    fun `animatium$setPreviousBobbingTilt`(previousBobbingTilt: Float)

    fun `animatium$getBobbingTilt`(): Float

    fun `animatium$getPreviousBobbingTilt`(): Float

    // Horizontal Speed
    fun `animatium$setHorizontalSpeed`(horizontalSpeed: Float)

    fun `animatium$setPreviousHorizontalSpeed`(previousHorizontalSpeed: Float)

    fun `animatium$getHorizontalSpeed`(): Float

    fun `animatium$getPreviousHorizontalSpeed`(): Float
}