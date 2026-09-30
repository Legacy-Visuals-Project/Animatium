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

package org.visuals.legacy.animatium.mixins.v1.gui.old_inventory_rendering;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.mixins.accessor.AbstractRecipeBookScreenAccessor;

@Mixin(EffectsInInventory.class)
public abstract class MixinEffectsInInventory_EffectBackgroundWidthAndPosition {
    @Shadow
    @Final
    private AbstractContainerScreen<?> screen;

    @Expression("? - 7")
    @ModifyExpressionValue(method = "render", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int animatium$fullWidthInventoryEffects(final int original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.fullWidthInventoryEffects && !(this.screen instanceof AbstractRecipeBookScreen<?> recipeBookScreen && ((AbstractRecipeBookScreenAccessor) recipeBookScreen).animatium$getRecipeBookComponent().isVisible())) {
            return 120;
        } else {
            return original;
        }
    }

    @WrapOperation(method = "renderBackground", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    private int animatium$fullWidthInventoryEffects$useOldWidth(final int min, final int max, final Operation<Integer> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.fullWidthInventoryEffects) {
            return min == 120 ? 120 : 32; // Hardcode old width values
        } else {
            return original.call(min, max);
        }
    }

    @WrapOperation(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;imageWidth:I", opcode = Opcodes.GETFIELD))
    private int animatium$effectsInventoryPosition(final AbstractContainerScreen<?> instance, final Operation<Integer> original) {
        final int imageWidth = original.call(instance);
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.inventoryEffectsPosition && !(this.screen instanceof AbstractRecipeBookScreen<?> recipeBookScreen && ((AbstractRecipeBookScreenAccessor) recipeBookScreen).animatium$getRecipeBookComponent().isVisible())) {
            return 0;
        } else {
            return imageWidth;
        }
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "intValue=2"))
    private int animatium$effectsInventoryPosition(final int original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.inventoryEffectsPosition && !(this.screen instanceof AbstractRecipeBookScreen<?> recipeBookScreen && ((AbstractRecipeBookScreenAccessor) recipeBookScreen).animatium$getRecipeBookComponent().isVisible())) {
            return AnimatiumConfig.instance().screen.fullWidthInventoryEffects ? -124 : 0; // TODO: Modern
        } else {
            return original;
        }
    }
}
