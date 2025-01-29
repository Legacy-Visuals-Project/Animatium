/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2025 lowercasebtw
 * Copyright (C) 2024-2025 mixces
 * Copyright (C) 2024-2025 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package btw.mixces.animatium.mixins.level.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.ViewBobbingStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity implements ViewBobbingStorage {
    @Unique
    private float animatium$horizontalSpeed = 0.0F;

    @Unique
    private float animatium$previousHorizontalSpeed = 0.0F;

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;handlePortal()V", shift = At.Shift.AFTER))
    private void animatium$storePreviousHorizontalSpeed(CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldViewBobbing()) {
            this.animatium$previousHorizontalSpeed = this.animatium$horizontalSpeed;
        }
    }

    @Inject(method = "applyMovementEmissionAndPlaySound", at = @At("HEAD"))
    private void animatium$storeHorizontalSpeed(Entity.MovementEmission movementEmission, Vec3 vec3d, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldViewBobbing()) {
            this.animatium$horizontalSpeed = this.animatium$horizontalSpeed + (float) vec3d.horizontalDistance() * 0.6F;
        }
    }

    @Override
    public void animatium$setHorizontalSpeed(float horizontalSpeed) {
        this.animatium$horizontalSpeed = horizontalSpeed;
    }

    @Override
    public void animatium$setPreviousHorizontalSpeed(float previousHorizontalSpeed) {
        this.animatium$previousHorizontalSpeed = previousHorizontalSpeed;
    }

    @Override
    public float animatium$getHorizontalSpeed() {
        return this.animatium$horizontalSpeed;
    }

    @Override
    public float animatium$getPreviousHorizontalSpeed() {
        return this.animatium$previousHorizontalSpeed;
    }
}
