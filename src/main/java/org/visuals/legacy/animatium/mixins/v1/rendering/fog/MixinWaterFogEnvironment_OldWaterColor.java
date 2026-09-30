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

package org.visuals.legacy.animatium.mixins.v1.rendering.fog;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.environment.WaterFogEnvironment;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(WaterFogEnvironment.class)
public abstract class MixinWaterFogEnvironment_OldWaterColor {
    @ModifyReturnValue(method = "getBaseColor", at = @At("RETURN"))
    private int animatium$oldWaterFogColor(final int original, @Local(argsOnly = true, ordinal = 0) final Camera camera, @Local(argsOnly = true, ordinal = 0) final ClientLevel level) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldWaterColorFog) {
            float value = 0.0F;
            if (camera.entity() instanceof LivingEntity livingEntity) {
                value = EnchantmentHelper.getEnchantmentLevel(level.registryAccess().getOrThrow(Enchantments.RESPIRATION), livingEntity) * 0.2F;
                if (livingEntity.hasEffect(MobEffects.WATER_BREATHING)) {
                    value *= 0.9F;
                }
            }

            return ARGB.colorFromFloat(1.0F, 0.02F + value, 0.02F + value, 0.2F + value);
        } else {
            return original;
        }
    }
}
