/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * Licensed under the PolyForm Shield License 1.0.0.
 * You may obtain a copy of the license at
 * https://polyformproject.org/licenses/shield/1.0.0
 */

package org.visuals.legacy.animatium.mixins.v1.rendering.items;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.util.ItemUtilKt;

@Mixin(ItemStack.class)
public abstract class MixinItemStack_LegacyRarities {
    @WrapOperation(method = "getStyledHoverName", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getRarity()Lnet/minecraft/world/item/Rarity;"))
    private Rarity animatium$itemRarities$getFormattedName(final ItemStack instance, final Operation<Rarity> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.legacyItemRarities) {
            return ItemUtilKt.getLegacyItemRarity((ItemStack) (Object) this);
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getRarity()Lnet/minecraft/world/item/Rarity;"))
    private Rarity animatium$itemRarities$toHoverableText(final ItemStack instance, final Operation<Rarity> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.legacyItemRarities) {
            return ItemUtilKt.getLegacyItemRarity((ItemStack) (Object) this);
        } else {
            return original.call(instance);
        }
    }
}
