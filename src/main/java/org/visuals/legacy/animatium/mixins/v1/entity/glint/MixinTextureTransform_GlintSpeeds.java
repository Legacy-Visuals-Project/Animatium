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

package org.visuals.legacy.animatium.mixins.v1.entity.glint;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.rendertype.TextureTransform;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(TextureTransform.class)
public abstract class MixinTextureTransform_GlintSpeeds {
    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/OptionsRenderState;glintSpeed:D", opcode = Opcodes.GETFIELD))
    private static double animatium$forceMaxGlintSpeed(final double original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.maxGlintProperties) {
            // 100% glint speed
            return 1.0D;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "doubleValue=8.0"))
    private static double animatium$glintSpeed(final double original, @Local(argsOnly = true, name = "scale") final float scale) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.legacyGlintSpeed && scale == 8.0F) {
            // Value taken from 1.8
            return 1.0D;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "floatValue=110000.0"))
    private static float animatium$glintSpeed$horizontal(final float original, @Local(argsOnly = true, name = "scale") final float scale) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.legacyGlintSpeed && scale == 8.0F) {
            // Value taken from 1.7/1.8
            return 4873.0F;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "floatValue=30000.0"))
    private static float animatium$glintSpeed$diagonal(final float original, @Local(argsOnly = true, name = "scale") final float scale) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.legacyGlintSpeed && scale == 8.0F) {
            // Value taken from 1.7/1.8
            return 3000.0F;
        } else {
            return original;
        }
    }
}
