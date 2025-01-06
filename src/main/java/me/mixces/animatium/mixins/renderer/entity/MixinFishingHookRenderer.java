package me.mixces.animatium.mixins.renderer.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.mixins.accessor.CameraAccessor;
import me.mixces.animatium.util.PlayerUtils;
import me.mixces.animatium.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.state.FishingHookRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(FishingHookRenderer.class)
public abstract class MixinFishingHookRenderer extends EntityRenderer<FishingHook, FishingHookRenderState> {
    protected MixinFishingHookRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

//    @ModifyExpressionValue(method = "getPlayerHandPos", at = @At(value = "CONSTANT", args = "floatValue=0.525"))
//    private float animatium$moveCastLineX(float original) {
//        /* this only needs to be done if the remove fov based projection is enabled */
//        return original - (AnimatiumConfig.getInstance().getRemoveFOVBasedProjection() ? 0.06F : 0.0F);
//    }

    @ModifyArg(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera$NearPlane;getPointOnPlane(FF)Lnet/minecraft/world/phys/Vec3;"), index = 1)
    private float animatium$moveCastLineY(float factorX) {
        return factorX + (AnimatiumConfig.instance().getTiltItemPositions() ? 0.15F : 0.0F);
    }

    @Inject(method = "render(Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;last()Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", ordinal = 1, shift = At.Shift.AFTER))
    private void animatium$oldFishingRodLineThickness(FishingHookRenderState fishingHookRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        // TODO/NOTE: Seems to be ok to set it like this and not have to set -1.0F after?
        if (AnimatiumConfig.instance().getThinFishingRodLineThickness()) {
            RenderUtils.setLineWidth(1.0F);
        } else if (AnimatiumConfig.instance().getOldFishingRodLineThickness()) {
            RenderUtils.setLineWidth(2.0F);
        }
    }

    @WrapOperation(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getEyePosition(F)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 animatium$fishingRodLineInterpolation(Player instance, float v, Operation<Vec3> original) {
        if (AnimatiumConfig.instance().getFishingRodLineInterpolation()) {
            CameraAccessor cameraAccessor = (CameraAccessor) entityRenderDispatcher.camera;
            float eyeHeight = Mth.lerp(v, cameraAccessor.getEyeHeightOld(), cameraAccessor.getEyeHeight());
            return PlayerUtils.lerpPlayerWithEyeHeight(instance, v, eyeHeight);
        } else {
            return original.call(instance, v);
        }
    }

    @ModifyExpressionValue(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isCrouching()Z"))
    private boolean animatium$noMoveFishingRodLine(boolean original) {
        return !AnimatiumConfig.instance().getNoMoveFishingRodLine() && original;
    }

    @ModifyExpressionValue(method = "getPlayerHandPos", at = @At(value = "CONSTANT", args = "doubleValue=0.8"))
    private double animatium$oldFishingRodLinePositionThirdPerson(double original) {
        return original + (AnimatiumConfig.instance().getOldFishingRodLinePositionThirdPerson() ? 0.05 : 0.0);
    }

    @WrapOperation(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;getHoldingArm(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/entity/HumanoidArm;"))
    private HumanoidArm animatium$fixCastLineCheck(Player player, Operation<HumanoidArm> original) {
        HumanoidArm value = original.call(player);
        if (AnimatiumConfig.instance().getFixCastLineCheck() && value != player.getMainArm() && !(player.getOffhandItem().getItem() instanceof FishingRodItem)) {
            return value.getOpposite();
        } else {
            return value;
        }
    }

    @ModifyArg(method = "extractRenderState(Lnet/minecraft/world/entity/projectile/FishingHook;Lnet/minecraft/client/renderer/entity/state/FishingHookRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/FishingHookRenderer;getPlayerHandPos(Lnet/minecraft/world/entity/player/Player;FF)Lnet/minecraft/world/phys/Vec3;"), index = 1)
    private float animatium$fixCastLineSwing(float original) {
        if (AnimatiumConfig.instance().getFixCastLineSwing()) {
            int multiplier = PlayerUtils.getHandMultiplier(Objects.requireNonNull(Minecraft.getInstance().player));
            return original * multiplier;
        } else {
            return original;
        }
    }

//    @ModifyArg(method = "getPlayerHandPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;scale(D)Lnet/minecraft/world/phys/Vec3;"))
//    private double animatium$removeFOVBasedLinePos(double value) {
//        if (AnimatiumConfig.getInstance().getRemoveFOVBasedProjection()) {
//            return 70;
//        } else {
//            return value;
//        }
//    }
}
