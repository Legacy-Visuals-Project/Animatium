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

package org.visuals.legacy.animatium.mixins.v1.rendering.sky.horizon_height;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.LevelHeightAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(ClientLevel.ClientLevelData.class)
public abstract class MixinClientLevel_ClientLevelData_OldMinY {
    @WrapOperation(method = "getHorizonHeight", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelHeightAccessor;getMinY()I"))
    private int animatium$skyHorizonHeight(final LevelHeightAccessor instance, final Operation<Integer> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldY0Height) {
            return 0;
        } else {
            return original.call(instance);
        }
    }
}