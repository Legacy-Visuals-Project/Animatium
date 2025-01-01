package me.mixces.animatium.mixins.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ViewBobbingStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    @Shadow
    @Final
    private MinecraftClient client;

    @WrapOperation(method = "tiltViewWhenHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDamageTiltYaw()F"))
    private float animatium$revertYaw(LivingEntity instance, Operation<Float> original) {
        if (AnimatiumConfig.getInstance().getOldDamageTilt()) {
            return 0.0F;
        } else {
            return original.call(instance);
        }
    }

    @Inject(method = "bobView", at = @At("TAIL"))
    private void animatium$fixVerticalBobbingTilt(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (AnimatiumConfig.getInstance().getFixVerticalBobbingTilt() && this.client.getCameraEntity() instanceof PlayerEntity playerEntity) {
            ViewBobbingStorage bobbingAccessor = (ViewBobbingStorage) playerEntity;
            float j = MathHelper.lerp(tickDelta, bobbingAccessor.animatium$getPreviousBobbingTilt(), bobbingAccessor.animatium$getBobbingTilt());
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(j));
        }
    }

    @WrapOperation(method = "bobView", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;distanceMoved:F"))
    private float animatium$changeDistance(AbstractClientPlayerEntity instance, Operation<Float> original) {
        if (AnimatiumConfig.getInstance().getOldViewBobbing()) {
            return ((ViewBobbingStorage) instance).animatium$getHorizontalSpeed();
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "bobView", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;lastDistanceMoved:F"))
    private float animatium$changePreviousDistance(AbstractClientPlayerEntity instance, Operation<Float> original) {
        if (AnimatiumConfig.getInstance().getOldViewBobbing()) {
            return ((ViewBobbingStorage) instance).animatium$getPreviousHorizontalSpeed();
        } else {
            return original.call(instance);
        }
    }

    @WrapWithCondition(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;bobView(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    private boolean animatium$minimalViewBobbing(GameRenderer instance, MatrixStack matrices, float tickDelta) {
        return !AnimatiumConfig.getInstance().getMinimalViewBobbing();
    }

    @WrapOperation(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;shouldRenderBlockOutline()Z"))
    private boolean animatium$persistentBlockOutline(GameRenderer instance, Operation<Boolean> original) {
        if (AnimatiumConfig.getInstance().getPersistentBlockOutline()) {
            return true;
        } else {
            return original.call(instance);
        }
    }
}
