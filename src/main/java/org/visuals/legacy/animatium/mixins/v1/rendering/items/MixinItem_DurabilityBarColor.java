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

import net.minecraft.util.ARGB;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.util.ItemUtilKt;

@Mixin(Item.class)
public abstract class MixinItem_DurabilityBarColor {
    @Inject(method = "getBarColor", at = @At("HEAD"), cancellable = true)
    private void animatium$durabilityBarColors(final ItemStack stack, final CallbackInfoReturnable<Integer> cir) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.durabilityBarColors && !((Item) (Object) this instanceof BundleItem)) {
            final int value = ItemUtilKt.getLegacyDurabilityColorValue(stack);
            cir.setReturnValue(ARGB.color(255 - value, value, 0));
        }
    }
}
