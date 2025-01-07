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
