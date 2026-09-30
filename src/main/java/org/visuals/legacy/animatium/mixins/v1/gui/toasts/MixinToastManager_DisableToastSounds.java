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

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModAbsent;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@IfModAbsent(value = "sodium-extra")
@Mixin(ToastManager.class)
public abstract class MixinToastManager_DisableToastSounds {
    @WrapWithCondition(method = "method_61991", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;playSound(Lnet/minecraft/client/sounds/SoundManager;)V"))
    private boolean animatium$disableToastSounds(final Toast.Visibility instance, final SoundManager manager, @Local(argsOnly = true, ordinal = 0) final ToastManager.ToastInstance<Toast> toastInstance) {
        final Toast toast = toastInstance.getToast();
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().extras.disableRecipeAndTutorialToasts || (!(toast instanceof RecipeToast) && !(toast instanceof TutorialToast));
    }
}
