package btw.mixces.animatium.mixins.screen;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DebugScreenOverlay.class)
public abstract class MixinDebugScreenOverlay {
    @WrapWithCondition(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private boolean animatium$removeDebugBackground(GuiGraphics instance, int x1, int y1, int x2, int y2, int color) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getRemoveDebugHudBackground();
    }

    @ModifyArg(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"), index = 5)
    private boolean animatium$addDebugShadow(boolean shadow) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDebugHudTextShadow()) {
            return true;
        } else {
            return shadow;
        }
    }

    @ModifyArg(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"), index = 4)
    private int animatium$oldDebugHudTextColor(int color) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldDebugHudTextColor()) {
            return -1;
        } else {
            return color;
        }
    }
}
