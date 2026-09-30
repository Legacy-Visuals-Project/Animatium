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

package org.visuals.legacy.animatium.mixins.v1.gui.hover_text_color;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(AbstractButton.class)
public abstract class MixinAbstractButton_LegacyTextHoverColor extends AbstractWidget {
    public MixinAbstractButton_LegacyTextHoverColor(final int x, final int y, final int width, final int height, final Component message) {
        super(x, y, width, height, message);
    }

    @WrapOperation(method = "extractDefaultLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractButton;getMessage()Lnet/minecraft/network/chat/Component;"))
    private Component animatium$renderWidget$old$textColor(final AbstractButton instance, final Operation<Component> original) {
        final Component component = original.call(instance);
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.legacyWidgetHoverTextColor) {
            return component.copy().withColor(!this.active ? 0xA0A0A0 : (this.isHoveredOrFocused() ? 0xFFFFA0 : 0xE0E0E0));
        } else {
            return component;
        }
    }
}
