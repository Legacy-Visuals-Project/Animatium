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

import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(InventoryScreen.class)
public abstract class MixinInventoryScreen_ModifyTitlePosition extends AbstractRecipeBookScreen<InventoryMenu> {
    public MixinInventoryScreen_ModifyTitlePosition(final InventoryMenu menu, final RecipeBookComponent<?> recipeBookComponent, final Inventory playerInventory, final Component title) {
        super(menu, recipeBookComponent, playerInventory, title);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void animatium$modifyTitlePosition(final Player player, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.oldCraftingSlotsPosition) {
            this.titleLabelX -= 10;
            this.titleLabelY += 8;
        }
    }
}
