package me.mixces.animatium.util

import com.mojang.blaze3d.systems.RenderSystem
import me.mixces.animatium.config.AnimatiumConfig
import me.mixces.animatium.mixins.accessor.ClientLevelDataAccessor
import net.minecraft.client.multiplayer.ClientLevel

object RenderUtils {
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

    @JvmStatic
    fun getLevelHorizonHeight(level: ClientLevel): Double {
        return if (AnimatiumConfig.instance().oldSkyHorizonHeight) {
            if ((level.getLevelData() as ClientLevelDataAccessor).isFlatWorld())
                0.0
            else
                63.0

        } else {
            level.levelData.getHorizonHeight(level)
        }
    }
}