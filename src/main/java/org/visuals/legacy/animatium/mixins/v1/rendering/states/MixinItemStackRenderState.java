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

package org.visuals.legacy.animatium.mixins.v1.rendering.states;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.visuals.legacy.animatium.util.states.ItemUtilityRenderState;

@Mixin(ItemStackRenderState.class)
public abstract class MixinItemStackRenderState implements ItemUtilityRenderState {
    @Unique
    private ItemStack animatium$stack = ItemStack.EMPTY;

    @Override
    public ItemStack animatium$getItemStack() {
        return animatium$stack;
    }

    @Override
    public void animatium$setItemStack(final ItemStack itemStack) {
        animatium$stack = itemStack;
    }
}
