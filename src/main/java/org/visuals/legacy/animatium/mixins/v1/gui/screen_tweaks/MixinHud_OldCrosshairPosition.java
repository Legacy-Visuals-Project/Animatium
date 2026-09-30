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

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(Hud.class)
public abstract class MixinHud_OldCrosshairPosition {
    @Definition(id = "graphics", local = @Local(type = GuiGraphicsExtractor.class, argsOnly = true))
    @Definition(id = "guiWidth", method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;guiWidth()I")
    @Expression("(graphics.guiWidth() - 15) / 2")
    @ModifyExpressionValue(method = "extractCrosshair", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int animatium$oldCrosshairPosition(final int original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.oldCrosshairPosition) {
            return original + 1;
        } else {
            return original;
        }
    }
}
