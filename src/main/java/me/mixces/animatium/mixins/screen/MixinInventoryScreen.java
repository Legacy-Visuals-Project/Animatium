package me.mixces.animatium.mixins.screen;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.mixces.animatium.AnimatiumClient;
import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InventoryScreen.class)
public abstract class MixinInventoryScreen {
    @WrapWithCondition(method = "renderEntityInInventoryFollowsMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;enableScissor(IIII)V"))
    private static boolean animatium$disableEntityScissor(GuiGraphics instance, int i, int j, int k, int l) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getDisableInventoryEntityScissor();
    }

    @WrapWithCondition(method = "renderEntityInInventoryFollowsMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;disableScissor()V"))
    private static boolean animatium$disableEntityScissor(GuiGraphics instance) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getDisableInventoryEntityScissor();
    }
}
