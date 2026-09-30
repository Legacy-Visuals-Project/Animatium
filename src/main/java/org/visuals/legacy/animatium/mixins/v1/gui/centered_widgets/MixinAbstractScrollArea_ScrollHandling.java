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

package org.visuals.legacy.animatium.mixins.v1.gui.centered_widgets;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.mixins.accessor.AbstractSelectionListAccessor;

@Mixin(AbstractScrollArea.class)
public abstract class MixinAbstractScrollArea_ScrollHandling {
    @Shadow
    private double scrollAmount;

    @Shadow
    protected abstract int contentHeight();

    @Shadow
    public abstract int maxScrollAmount();

    @Inject(method = "setScrollAmount", at = @At("HEAD"), cancellable = true)
    private void animatium$allowNegativeScrolling(final double scrollAmount, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.centerScrollableListWidgets && (Object) this instanceof AbstractSelectionList<?> abstractSelectionList) {
            ci.cancel();
            int maxScrollY = maxScrollAmount();
            if (maxScrollY < 0) {
                maxScrollY /= 2;
            }

            if (!((AbstractSelectionListAccessor) abstractSelectionList).animatium$shouldCenterVertically() && maxScrollY < 0) {
                maxScrollY = 0;
            }

            this.scrollAmount = Math.min(Math.max(0, scrollAmount), maxScrollY);
        }
    }

    @WrapOperation(method = "maxScrollAmount", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
    public int animatium$modifyMaxScroll(final int left, final int right, final Operation<Integer> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.centerScrollableListWidgets && (Object) this instanceof AbstractSelectionList<?> abstractSelectionList) {
            return this.contentHeight() - abstractSelectionList.getHeight();
        } else {
            return original.call(left, right);
        }
    }
}
