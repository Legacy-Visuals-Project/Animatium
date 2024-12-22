package me.mixces.animatium.mixins.renderer.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.mixins.accessor.CameraAccessor;
import me.mixces.animatium.util.PlayerUtils;
import me.mixces.animatium.util.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.render.entity.state.FishingBobberEntityState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(FishingBobberEntityRenderer.class)
public abstract class MixinFishingBobberEntityRenderer extends EntityRenderer<FishingBobberEntity, FishingBobberEntityState> {
    protected MixinFishingBobberEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @ModifyExpressionValue(method = "getHandPos", at = @At(value = "CONSTANT", args = "floatValue=0.525"))
    private float animatium$moveCastLineX(float original) {
        /* this only needs to be done if the remove fov based projection is enabled */
        return original - (AnimatiumConfig.getInstance().getRemoveFOVBasedProjection() ? 0.06F : 0.0F);
    }

    @ModifyArg(method = "getHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera$Projection;getPosition(FF)Lnet/minecraft/util/math/Vec3d;"), index = 1)
    private float animatium$moveCastLineY(float factorX) {
        return factorX + (AnimatiumConfig.getInstance().getTiltItemPositions() ? 0.15F : 0.0F);
    }

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/FishingBobberEntityState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;peek()Lnet/minecraft/client/util/math/MatrixStack$Entry;", ordinal = 1, shift = At.Shift.AFTER))
    private void animatium$oldFishingRodLineThickness(FishingBobberEntityState fishingBobberEntityState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        // TODO/NOTE: Seems to be ok to set it like this and not have to set -1.0F after?
        if (AnimatiumConfig.getInstance().getThinFishingRodLineThickness()) {
            RenderUtils.setLineWidth(1.0F);
        } else if (AnimatiumConfig.getInstance().getOldFishingRodLineThickness()) {
            RenderUtils.setLineWidth(2.0F);
        }
    }

    @WrapOperation(method = "getHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getCameraPosVec(F)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d animatium$fishingRodLineInterpolation(PlayerEntity instance, float v, Operation<Vec3d> original) {
        if (AnimatiumConfig.getInstance().getFishingRodLineInterpolation()) {
            CameraAccessor cameraAccessor = (CameraAccessor) dispatcher.camera;
            float eyeHeight = MathHelper.lerp(v, cameraAccessor.getLastCameraY(), cameraAccessor.getCameraY());
            return PlayerUtils.lerpPlayerWithEyeHeight(instance, v, eyeHeight);
        } else {
            return original.call(instance, v);
        }
    }

    @ModifyExpressionValue(method = "getHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isInSneakingPose()Z"))
    private boolean animatium$noMoveFishingRodLine(boolean original) {
        return !AnimatiumConfig.getInstance().getNoMoveFishingRodLine() && original;
    }

    @ModifyExpressionValue(method = "getHandPos", at = @At(value = "CONSTANT", args = "doubleValue=0.8"))
    private double animatium$oldFishingRodLinePositionThirdPerson(double original) {
        return original + (AnimatiumConfig.getInstance().getOldFishingRodLinePositionThirdPerson() ? 0.05 : 0.0);
    }

    @WrapOperation(method = "getHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/FishingBobberEntityRenderer;getArmHoldingRod(Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/util/Arm;"))
    private Arm animatium$fixCastLineCheck(PlayerEntity player, Operation<Arm> original) {
        Arm value = original.call(player);
        if (AnimatiumConfig.getInstance().getFixCastLineCheck() && value != player.getMainArm() && !(player.getOffHandStack().getItem() instanceof FishingRodItem)) {
            return value.getOpposite();
        } else {
            return value;
        }
    }

    @ModifyArg(method = "updateRenderState(Lnet/minecraft/entity/projectile/FishingBobberEntity;Lnet/minecraft/client/render/entity/state/FishingBobberEntityState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/FishingBobberEntityRenderer;getHandPos(Lnet/minecraft/entity/player/PlayerEntity;FF)Lnet/minecraft/util/math/Vec3d;"), index = 1)
    private float animatium$fixCastLineSwing(float original) {
        if (AnimatiumConfig.getInstance().getFixCastLineSwing()) {
            int multiplier = PlayerUtils.getHandMultiplier(Objects.requireNonNull(MinecraftClient.getInstance().player));
            return original * multiplier;
        } else {
            return original;
        }
    }

    @ModifyArg(method = "getHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(D)Lnet/minecraft/util/math/Vec3d;"))
    private double animatium$removeFOVBasedLinePos(double value) {
        if (AnimatiumConfig.getInstance().getRemoveFOVBasedProjection()) {
            return 70;
        } else {
            return value;
        }
    }
}
