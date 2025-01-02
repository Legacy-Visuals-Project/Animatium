package me.mixces.animatium.mixins.renderer;

import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class MixinDrawContext {
    @Shadow
    public abstract void fill(RenderType renderType, int i, int j, int k, int l, int m, int n);

    @Inject(method = "renderItemBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(Lnet/minecraft/client/renderer/RenderType;IIIIII)V", ordinal = 0, shift = At.Shift.AFTER))
    private void animatium$oldDurabilityBar(ItemStack stack, int x, int y, CallbackInfo ci) {
        if (AnimatiumConfig.getInstance().getOldDurabilityBarColors() && !(stack.getItem() instanceof BundleItem)) {
            int i = x + 2;
            int j = y + 13;
            int color = ARGB.color((255 - ItemUtils.getLegacyDurabilityColorValue(stack)) / 4, 64, 0);
            this.fill(RenderType.gui(), i, j, i + 12, j + 1, 200, ARGB.opaque(color));
        }
    }
}
