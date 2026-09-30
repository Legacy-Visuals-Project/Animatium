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
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(Player.class)
public abstract class MixinPlayer_UncapHeadRotation {
    @WrapOperation(method = "getMaxHeadRotationRelativeToBody", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isBlocking()Z"))
    private boolean animatium$uncapBlockingHeadRotation(final Player instance, final Operation<Boolean> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.uncapBlockingHeadRotation) {
            return false;
        } else if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.alwaysBlockingHeadCap) {
            return true;
        } else {
            return original.call(instance);
        }
    }
}
