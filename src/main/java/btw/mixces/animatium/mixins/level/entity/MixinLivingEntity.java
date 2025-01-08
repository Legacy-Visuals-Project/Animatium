package btw.mixces.animatium.mixins.level.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.ViewBobbingStorage;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements ViewBobbingStorage {
    @Unique
    private float animatium$bobbingTilt = 0.0F;

    @Unique
    private float animatium$previousBobbingTilt = 0.0F;

    public MixinLivingEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow
    public float yBodyRot;

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;abs(F)F"))
    private float animatium$rotateBackwardsWalking(float value, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getRotateBackwardsWalking()) {
            return 0F;
        } else {
            return original.call(value);
        }
    }

    @WrapOperation(method = "tickHeadTurn", at = @At(value = "INVOKE", target = "Ljava/lang/Math;abs(F)F"))
    private float animatium$removeHeadRotationInterpolation(float g, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getRotateBackwardsWalking()) {
            g = Mth.clamp(g, -75.0F, 75.0F);
            this.yBodyRot = this.getYRot() - g;
            if (Math.abs(g) > 50.0F) {
                this.yBodyRot += g * 0.2F;
            }
            return Float.MIN_VALUE;
        } else {
            return original.call(g);
        }
    }

    @WrapOperation(method = "lerpHeadRotationStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(DDD)D"))
    public double animatium$removeHeadRotationInterpolation(double delta, double start, double end, Operation<Double> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getRemoveHeadRotationInterpolation()) {
            return end;
        } else {
            return original.call(delta, start, end);
        }
    }

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;tickEffects()V", shift = At.Shift.BEFORE))
    private void animatium$updatePreviousBobbingTiltValue(CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixVerticalBobbingTilt()) {
            this.animatium$previousBobbingTilt = this.animatium$bobbingTilt;
        }
    }

    @ModifyExpressionValue(method = "getItemBlockingWith", at = @At(value = "CONSTANT", args = "intValue=5"))
    private int animatium$removeClientsideBlockingDelay(int original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getRemoveClientsideBlockingDelay()) {
            return 0;
        } else {
            return original;
        }
    }

    @WrapOperation(method = "updatingUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isSameItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean animatium$fixItemUsageCheck(ItemStack left, ItemStack right, Operation<Boolean> original) {
        boolean value = original.call(left, right);
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixItemUsageCheck()) {
            return left.getDamageValue() == right.getDamageValue() ? left == right : value;
        } else {
            return value;
        }
    }

    @WrapOperation(method = "tickEffects", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private boolean animatium$hideFirstpersonParticles(List<ParticleOptions> instance, Operation<Boolean> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getHideFirstpersonParticles() && (Object) this == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            return true;
        } else {
            return original.call(instance);
        }
    }

    @Override
    public void animatium$setBobbingTilt(float bobbingTilt) {
        this.animatium$bobbingTilt = bobbingTilt;
    }

    @Override
    public void animatium$setPreviousBobbingTilt(float previousBobbingTilt) {
        this.animatium$previousBobbingTilt = previousBobbingTilt;
    }

    @Override
    public float animatium$getBobbingTilt() {
        return this.animatium$bobbingTilt;
    }

    @Override
    public float animatium$getPreviousBobbingTilt() {
        return this.animatium$previousBobbingTilt;
    }
}
