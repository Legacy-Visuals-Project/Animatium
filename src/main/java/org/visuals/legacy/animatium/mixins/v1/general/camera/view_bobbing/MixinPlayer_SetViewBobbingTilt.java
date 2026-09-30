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

package org.visuals.legacy.animatium.mixins.v1.general.camera.view_bobbing;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MixinPlayer_SetViewBobbingTilt extends LivingEntity {
    protected MixinPlayer_SetViewBobbingTilt(final EntityType<? extends LivingEntity> entityType, final Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setSpeed(F)V", shift = At.Shift.AFTER))
    private void animatium$updateBobbingTiltValues(final CallbackInfo ci) {
        final float tilt = this.onGround() || this.getHealth() <= 0.0F ? 0.0F : (float) (Math.atan(-this.getDeltaMovement().y * (double) 0.2F) * 15.0F);
        this.animatium$setBobbingTilt(Mth.lerp(0.8F, this.animatium$getBobbingTilt(), tilt));
    }
}
