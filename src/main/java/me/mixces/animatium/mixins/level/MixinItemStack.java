package me.mixces.animatium.mixins.level;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @WrapOperation(method = "getStyledHoverName", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getRarity()Lnet/minecraft/world/item/Rarity;"))
    private Rarity animatium$oldItemRarities$getFormattedName(ItemStack instance, Operation<Rarity> original) {
        if (AnimatiumConfig.getInstance().getOldItemRarities()) {
            return ItemUtils.getOldItemRarity((ItemStack) (Object) this);
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getRarity()Lnet/minecraft/world/item/Rarity;"))
    private Rarity animatium$oldItemRarities$toHoverableText(ItemStack instance, Operation<Rarity> original) {
        if (AnimatiumConfig.getInstance().getOldItemRarities()) {
            return ItemUtils.getOldItemRarity((ItemStack) (Object) this);
        } else {
            return original.call(instance);
        }
    }
}
