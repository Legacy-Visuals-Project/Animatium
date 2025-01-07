package btw.mixces.animatium.mixins.level.item;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.ItemUtils;
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
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldDurabilityBarColors() && !((Item) (Object) this instanceof BundleItem)) {
            int value = ItemUtils.getLegacyDurabilityColorValue(stack);
            cir.setReturnValue(ARGB.color(255 - value, value, 0));
        }
    }
}
