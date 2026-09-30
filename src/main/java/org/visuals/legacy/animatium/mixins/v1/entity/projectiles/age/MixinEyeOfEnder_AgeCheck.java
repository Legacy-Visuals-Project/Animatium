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

package org.visuals.legacy.animatium.mixins.v1.entity.projectiles.age;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(EyeOfEnder.class)
public abstract class MixinEyeOfEnder_AgeCheck {
    @WrapOperation(method = "shouldRenderAtSqrDistance", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/projectile/EyeOfEnder;tickCount:I", opcode = Opcodes.GETFIELD))
    private int animatium$projectileAgeCheck(final EyeOfEnder instance, final Operation<Integer> original) {
        final int originalTick = original.call(instance);
        if (Animatium.isEnabled() && !AnimatiumConfig.instance().other.projectileAgeCheck) {
            return originalTick + 2;
        } else {
            return originalTick;
        }
    }
}
