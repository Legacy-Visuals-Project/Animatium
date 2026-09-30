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

package org.visuals.legacy.animatium.mixins.v1.entity.rotations;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow
    public float yBodyRot;

    public MixinLivingEntity(final EntityType<?> type, final Level level) {
        super(type, level);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;abs(F)F"))
    private float animatium$rotateBackwardsWalking(final float value, final Operation<Float> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.rotateBackwardsWalking) {
            return 0F;
        } else {
            return original.call(value);
        }
    }

    // TODO/NOTE: Might can be improved/shortened
    @WrapOperation(method = "tickHeadTurn", at = @At(value = "INVOKE", target = "Ljava/lang/Math;abs(F)F"))
    private float animatium$backwardsWalkingHeadRotation(final float value, final Operation<Float> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.rotateBackwardsWalking) {
            final float rotation = Mth.clamp(value, -75.0F, 75.0F);
            this.yBodyRot = this.getYRot() - rotation;
            if (Math.abs(rotation) > 50.0F) {
                this.yBodyRot += rotation * 0.2F;
            }

            return Float.MIN_VALUE;
        } else {
            return original.call(value);
        }
    }

    @WrapOperation(method = "lerpHeadRotationStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(DDD)D"))
    public double animatium$disableHeadRotationInterpolation(final double delta, final double start, final double end, final Operation<Double> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.disableHeadRotationInterpolation) {
            return end;
        } else {
            return original.call(delta, start, end);
        }
    }
}
