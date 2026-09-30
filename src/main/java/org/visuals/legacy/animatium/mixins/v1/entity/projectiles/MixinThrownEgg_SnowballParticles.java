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

package org.visuals.legacy.animatium.mixins.v1.entity.projectiles;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEgg;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(ThrownEgg.class)
public abstract class MixinThrownEgg_SnowballParticles {
    // TODO/NOTE: Should x/y/z speed be 0.0?
    @WrapOperation(method = "handleEntityEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/throwableitemprojectile/ThrownEgg;getItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack animatium$eggSnowballParticles(final ThrownEgg instance, final Operation<ItemStack> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.eggSnowballParticles) {
            return new ItemStack(Items.SNOWBALL);
        } else {
            return original.call(instance);
        }
    }
}
