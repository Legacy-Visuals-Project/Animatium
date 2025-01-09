package btw.mixces.animatium.util

import btw.mixces.animatium.config.AnimatiumConfig
import btw.mixces.animatium.mixins.accessor.ClientLevelDataAccessor
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
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

    @JvmStatic
    fun fillVerticalLine(context: GuiGraphics, startX: Int, startY: Int, endX: Int, endY: Int, color: Int) {
        context.fill(startX, startY, startX + 1, startY + endX, endY, color);
    }

    @JvmStatic
    fun fillVerticalGradientLine(context: GuiGraphics, i: Int, j: Int, k: Int, l: Int, startColor: Int, endColor: Int) {
        context.fillGradient(i, j, i + 1, j + k, l, startColor, endColor);
    }

    @JvmStatic
    fun fillHorizontalLine(context: GuiGraphics, startX: Int, startY: Int, endX: Int, endY: Int, color: Int) {
        context.fill(startX, startY, startX + endX, startY + 1, endY, color);
    }

    @JvmStatic
    fun fillRectangle(context: GuiGraphics, startX: Int, startY: Int, endX: Int, endY: Int, padding: Int, color: Int) {
        context.fill(startX, startY, startX + endX, startY + endY, padding, color);
    }
}