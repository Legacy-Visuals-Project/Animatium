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

package org.visuals.legacy.animatium.mixins.v1.gui.screen_tweaks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.mixins.accessor.SlotAccessor;

// TODO: Move recipe book button
@Mixin(InventoryMenu.class)
public abstract class MixinInventoryMenu_OldCraftingSlots {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addCraftingGridSlots(II)V"))
    private void animatium$modifyCraftingSlotsPosition(final InventoryMenu instance, final int x, final int y, final Operation<Void> original) {
        int newX = x, newY = y;
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.oldCraftingSlotsPosition) {
            newX -= 10;
            newY += 8;
        }

        original.call(instance, newX, newY);
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addResultSlot(Lnet/minecraft/world/entity/player/Player;II)Lnet/minecraft/world/inventory/Slot;"))
    private Slot animatium$modifyCraftingResultSlotPosition(final InventoryMenu instance, final Player player, final int x, final int y, final Operation<Slot> original) {
        int newX = x, newY = y;
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.oldCraftingSlotsPosition) {
            newX -= 10;
            newY += 8;
        }

        return original.call(instance, player, newX, newY);
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;", ordinal = 1))
    private Slot animatium$moveOffhandSlot(final InventoryMenu instance, final Slot slot, final Operation<Slot> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.oldCraftingSlotsPosition) {
            ((SlotAccessor) slot).animatium$setX(slot.x + 75);
        }

        return original.call(instance, slot);
    }
}