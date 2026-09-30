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

package org.visuals.legacy.animatium.mixins.v1.rendering.blocks;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(BiomeSpecialEffects.class)
public abstract class MixinBiomeSpecialEffects_DisableWaterBiomeTint {
    @Shadow
    @Final
    private int waterColor;

    @Definition(id = "waterColor", field = "Lnet/minecraft/world/level/biome/BiomeSpecialEffects;waterColor:I")
    @Expression("this.waterColor")
    @ModifyExpressionValue(method = "waterColor", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int animatium$oldWaterColor(final int original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.oldWaterColorEffects) {
            if (this.waterColor == 6388580/*Swamp Water Color*/) {
                return 0xFFe0FFAE;
            } else {
                return -1;
            }
        } else {
            return original;
        }
    }
}
