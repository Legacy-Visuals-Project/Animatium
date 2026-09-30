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

package org.visuals.legacy.animatium.mixins.v1.rendering.items.usage;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.item.properties.conditional.IsUsingItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.util.ItemUtilKt;

@Mixin(IsUsingItem.class)
public abstract class MixinIsUsingItem {
    @ModifyReturnValue(method = "get", at = @At(value = "RETURN"))
    private boolean animatium$getValue(final boolean original, @Local(argsOnly = true, name = "itemStack") final ItemStack itemStack, @Local(argsOnly = true, name = "displayContext") final ItemDisplayContext displayContext) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.disableItemUsingTextureInGUI && ItemUtilKt.isRangedWeaponItem(itemStack) && displayContext == ItemDisplayContext.GUI) {
            return false;
        } else {
            return original;
        }
    }
}
