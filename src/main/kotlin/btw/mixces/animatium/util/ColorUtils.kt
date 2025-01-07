package btw.mixces.animatium.util

import kotlin.random.Random

object ColorUtils {
    @JvmStatic
    fun randomColor(): Int {
        return Random.nextInt(0xFFFFFF)
    }
}