package me.mixces.animatium.mixins.level;

import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem {
    @Inject(method = "getBarColor", at = @At("HEAD"), cancellable = true)
    private void animatium$oldDurabilityBarColors(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (AnimatiumConfig.instance().getOldDurabilityBarColors() && !((Item) (Object) this instanceof BundleItem)) {
            int value = ItemUtils.getLegacyDurabilityColorValue(stack);
            cir.setReturnValue(ARGB.color(255 - value, value, 0));
        }
    }
}
