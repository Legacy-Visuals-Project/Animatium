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

package org.visuals.legacy.animatium.mixins.v1.rendering;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(ScreenEffectRenderer.class)
public abstract class MixinScreenEffectRenderer_TextureOverlays {
	@ModifyExpressionValue(method = "submitWater", at = @At(value = "CONSTANT", args = "floatValue=0.1"))
	private static float animatium$useOldWaterOverlayOpacity(final float original) {
		if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldWaterOverlayOpacity) {
			return 0.5F;
		} else {
			return original;
		}
	}
}
