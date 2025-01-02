package me.mixces.animatium.mixins.screen;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DebugScreenOverlay.class)
public abstract class MixinDebugScreenOverlay {
    @WrapWithCondition(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private boolean animatium$removeDebugBackground(GuiGraphics instance, int x1, int y1, int x2, int y2, int color) {
        return !AnimatiumConfig.getInstance().getRemoveDebugHudBackground();
    }

    @ModifyArg(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"), index = 5)
    private boolean animatium$addDebugShadow(boolean shadow) {
        if (AnimatiumConfig.getInstance().getDebugHudTextShadow()) {
            return true;
        } else {
            return shadow;
        }
    }

    @ModifyArg(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"), index = 4)
    private int animatium$oldDebugHudTextColor(int color) {
        if (AnimatiumConfig.getInstance().getOldDebugHudTextColor()) {
            return -1;
        } else {
            return color;
        }
    }
}
