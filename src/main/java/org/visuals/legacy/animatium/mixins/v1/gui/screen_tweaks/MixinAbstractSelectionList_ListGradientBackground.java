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

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(AbstractSelectionList.class)
public abstract class MixinAbstractSelectionList_ListGradientBackground extends AbstractContainerWidget {
    public MixinAbstractSelectionList_ListGradientBackground(final int x, final int y, final int width, final int height, final Component message) {
        super(x, y, width, height, message);
    }

    @Inject(method = "renderListBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.AFTER))
    private void animatium$renderListBackgroundGradient(final GuiGraphics graphics, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.listBackgroundGradient) {
            graphics.fillGradient(this.getX(), this.getY(), this.getRight(), this.getY() + 4, -16777216, 0);
            graphics.fillGradient(this.getX(), this.getBottom() - 4, this.getRight(), this.getBottom(), 0, -16777216);
        }
    }
}
