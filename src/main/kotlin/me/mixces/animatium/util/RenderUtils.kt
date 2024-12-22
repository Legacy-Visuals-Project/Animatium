package me.mixces.animatium.util

import com.mojang.blaze3d.systems.RenderSystem

abstract class RenderUtils {
    companion object {
        private var lineWidth: Float = -1.0F

        @JvmStatic
        fun getLineWidth(): Float {
            return if (lineWidth == -1.0F) RenderSystem.getShaderLineWidth() else lineWidth
        }

        @JvmStatic
        fun getLineWidth(default: Float): Float {
            return if (lineWidth == -1.0F) default else lineWidth
        }

        @JvmStatic
        fun setLineWidth(width: Float) {
            lineWidth = width
        }
    }
}