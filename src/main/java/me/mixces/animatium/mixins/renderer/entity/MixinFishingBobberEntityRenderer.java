package me.mixces.animatium.mixins.renderer.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.mixins.accessor.CameraAccessor;
import me.mixces.animatium.util.PlayerUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.render.entity.state.FishingBobberEntityState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.util.Arm;
import net.minecraft.util.Colors;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Objects;

@Mixin(FishingBobberEntityRenderer.class)
public abstract class MixinFishingBobberEntityRenderer extends EntityRenderer<FishingBobberEntity, FishingBobberEntityState> {
    protected MixinFishingBobberEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @ModifyExpressionValue(method = "getHandPos", at = @At(value = "CONSTANT", args = "floatValue=0.525"))
    private float animatium$moveCastLineX(float original) {
        return original - 0.06F;
    }

    @ModifyArg(method = "getHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera$Projection;getPosition(FF)Lnet/minecraft/util/math/Vec3d;"), index = 1)
    private float animatium$moveCastLineY(float factorX) {
        return factorX + (AnimatiumConfig.getInstance().getTiltItemPositions() ? 0.15F : 0.0F);
    }

    @ModifyArg(method = "getHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(D)Lnet/minecraft/util/math/Vec3d;"))
    private double animatium$removeFOVBasedLinePos(double value) {
        return AnimatiumConfig.getInstance().getRemoveFOVBasedProjection() ? 70 : value;
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

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    private static void renderFishingLine(float x, float y, float z, VertexConsumer buffer, MatrixStack.Entry matrices, float segmentStart, float segmentEnd) {
        float f = x * segmentStart;
        float g = y * (segmentStart * segmentStart + segmentStart) * 0.5F + 0.25F;
        float h = z * segmentStart;
        float i = x * segmentEnd - f;
        float j = y * (segmentEnd * segmentEnd + segmentEnd) * 0.5F + 0.25F - g;
        float k = z * segmentEnd - h;
        float l = MathHelper.sqrt(i * i + j * j + k * k);
        i /= l;
        j /= l;
        k /= l;
        buffer.vertex(matrices, f, g, h).color(Colors.BLACK).normal(matrices, i, j, k);
    }
}
