package me.mixces.animatium.mixins.world.entity;

import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ViewBobbingStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tickPortalTeleportation()V", shift = At.Shift.AFTER))
    private void animatium$storePreviousHorizontalSpeed(CallbackInfo ci) {
        if (AnimatiumConfig.getInstance().getOldViewBobbing()) {
            this.animatium$previousHorizontalSpeed = this.animatium$horizontalSpeed;
        }
    }

    @Inject(method = "applyMoveEffect", at = @At("HEAD"))
    private void animatium$storeHorizontalSpeed(Entity.MoveEffect moveEffect, Vec3d vec3d, BlockPos landingPos, BlockState landingState, CallbackInfo ci) {
        if (AnimatiumConfig.getInstance().getOldViewBobbing()) {
            this.animatium$horizontalSpeed = this.animatium$horizontalSpeed + (float) vec3d.horizontalLength() * 0.6F;
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
