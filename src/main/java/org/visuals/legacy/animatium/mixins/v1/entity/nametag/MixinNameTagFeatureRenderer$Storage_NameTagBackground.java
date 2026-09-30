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

package org.visuals.legacy.animatium.mixins.v1.entity.nametag;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(NameTagFeatureRenderer.Storage.class)
public abstract class MixinNameTagFeatureRenderer$Storage_NameTagBackground {
    @WrapOperation(method = "add", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;getBackgroundOpacity(F)F"))
    private float animatium$nameTagBackground(final Options instance, final float defaultOpacity, final Operation<Float> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.hideNameTagBackground) {
            return 0F;
        } else {
            return original.call(instance, defaultOpacity);
        }
    }
}
