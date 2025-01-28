package btw.mixces.animatium.mixins.level.item;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.ItemUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @WrapOperation(method = "getStyledHoverName", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getRarity()Lnet/minecraft/world/item/Rarity;"))
    private Rarity animatium$oldItemRarities$getFormattedName(ItemStack instance, Operation<Rarity> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldItemRarities()) {
            return ItemUtils.getOldItemRarity((ItemStack) (Object) this);
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getRarity()Lnet/minecraft/world/item/Rarity;"))
    private Rarity animatium$oldItemRarities$toHoverableText(ItemStack instance, Operation<Rarity> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldItemRarities()) {
            return ItemUtils.getOldItemRarity((ItemStack) (Object) this);
        } else {
            return original.call(instance);
        }
    }
}
