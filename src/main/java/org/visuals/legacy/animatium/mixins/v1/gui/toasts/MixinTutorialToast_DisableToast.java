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

package org.visuals.legacy.animatium.mixins.v1.gui.toasts;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModAbsent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import org.spongepowered.asm.mixin.Mixin;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@IfModAbsent(value = "sodium-extra")
@Mixin(TutorialToast.class)
public abstract class MixinTutorialToast_DisableToast {
    @WrapMethod(method = "update")
    private void animatium$disableTutorialToast(final ToastManager manager, final long fullyVisibleForMs, final Operation<Void> original) {
        if (!Animatium.isEnabled() || !AnimatiumConfig.instance().extras.disableRecipeAndTutorialToasts) {
            original.call(manager, fullyVisibleForMs);
        }
    }

    @WrapMethod(method = "render")
    private void animatium$disableTutorialToast(final GuiGraphics graphics, final Font font, final long fullyVisibleForMs, final Operation<Void> original) {
        if (!Animatium.isEnabled() || !AnimatiumConfig.instance().extras.disableRecipeAndTutorialToasts) {
            original.call(graphics, font, fullyVisibleForMs);
        }
    }
}
