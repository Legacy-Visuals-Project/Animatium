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

package org.visuals.legacy.animatium.mixins.v1.gui.remove_recipe_book;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(AbstractRecipeBookScreen.class)
public abstract class MixinAbstractRecipeBookScreen_ToggleRecipeBookWidget {
    @WrapOperation(method = "initButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractRecipeBookScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    private GuiEventListener animatium$hideRecipeBook$button(final AbstractRecipeBookScreen<?> instance, final GuiEventListener guiEventListener, final Operation<GuiEventListener> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.hideRecipeBook) {
            return null;
        } else {
            return original.call(instance, guiEventListener);
        }
    }

    @WrapOperation(method = "initButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractRecipeBookScreen;addWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    private GuiEventListener animatium$hideRecipeBook$widget(final AbstractRecipeBookScreen<?> instance, final GuiEventListener guiEventListener, final Operation<GuiEventListener> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.hideRecipeBook) {
            return null;
        } else {
            return original.call(instance, guiEventListener);
        }
    }
}
