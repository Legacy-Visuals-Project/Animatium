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

package org.visuals.legacy.animatium.mixins.v1.rendering.items;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(SpriteContents.class)
public abstract class MixinSpriteContents_FixTransparencyLimit {
    @WrapOperation(method = "isTransparent", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;alpha(I)I"))
    private int animatium$upMinPixelTransparencyLimit(final int color, final Operation<Integer> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().fixes.upMinPixelTransparencyLimit && ARGB.alphaFloat(color) <= 0.1F) {
            return 0;
        } else {
            return original.call(color);
        }
    }
}
