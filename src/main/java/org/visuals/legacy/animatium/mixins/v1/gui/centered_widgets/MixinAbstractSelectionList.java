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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(AbstractSelectionList.class)
public abstract class MixinAbstractSelectionList<E extends AbstractSelectionList.Entry<E>> {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private void animatium$updateScroll(final GuiGraphics graphics, final int mouseX, final int mouseY, final float tickDelta, final E entry, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.centerScrollableListWidgets) {
            ((AbstractScrollArea) (Object) this).refreshScrollAmount();
        }
    }

    @WrapOperation(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;isFocused()Z"))
    private boolean animatium$listWidgetSelectedBorderColor(AbstractSelectionList<?> instance, Operation<Boolean> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.listWidgetSelectedBorderColor) {
            return false;
        } else {
            return original.call(instance);
        }
    }
}
