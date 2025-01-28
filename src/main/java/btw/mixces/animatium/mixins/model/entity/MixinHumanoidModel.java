package btw.mixces.animatium.mixins.model.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.EntityUtils;
import btw.mixces.animatium.util.PlayerUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(HumanoidModel.class)
public abstract class MixinHumanoidModel<T extends HumanoidRenderState> extends EntityModel<T> {
    @Shadow
    @Final
    public ModelPart rightArm;

    @Shadow
    @Final
    public ModelPart leftArm;

    @Shadow
    @Final
    public ModelPart head;

    @Shadow
    @Final
    public ModelPart body;

    @Shadow
    @Final
    public ModelPart rightLeg;

    @Shadow
    @Final
    public ModelPart leftLeg;

    protected MixinHumanoidModel(ModelPart modelPart, Function<ResourceLocation, RenderType> function) {
        super(modelPart, function);
    }

    @WrapOperation(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;isCrouching:Z"))
    private boolean animatium$oldSneakingFeetPosition(HumanoidRenderState instance, Operation<Boolean> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldSneakingFeetPosition() && instance.isCrouching) {
            // Values sourced from older versions
            // TODO/NOTE: Better way to do this possibly?
            body.xRot = 0.5F;
            rightArm.xRot += 0.4F;
            leftArm.xRot += 0.4F;
            rightLeg.z = 4.0F;
            leftLeg.z = 4.0F;
            rightLeg.y = 9.0F;
            leftLeg.y = 9.0F;
            head.y = 1.0F;
            return false;
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "setupAttackAnimation", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/HumanoidModel;leftArm:Lnet/minecraft/client/model/geom/ModelPart;", ordinal = 3, opcode = Opcodes.GETFIELD))
    public ModelPart animatium$fixMirrorArmSwing$field(HumanoidModel<?> instance, Operation<ModelPart> original, @Local ModelPart modelPart) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixMirrorArmSwing()) {
            return modelPart;
        } else {
            return original.call(instance);
        }
    }

    @ModifyExpressionValue(method = "setupAttackAnimation", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;sin(F)F", ordinal = 5))
    public float animatium$fixMirrorArmSwing$sin(float original, @Local HumanoidArm arm) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixMirrorArmSwing()) {
            return PlayerUtils.getArmMultiplier(arm) * original; // NOTE: i changed this loloolool
        } else {
            return original;
        }
    }

    @WrapOperation(method = "poseBlockingArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F"))
    private float animatium$lockBlockingArmRotation(float value, float min, float max, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getLockBlockingArmRotation()) {
            return 0.0F;
        } else {
            return original.call(value, min, max);
        }
    }

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At(value = "CONSTANT", args = "floatValue=0.0", ordinal = 1))
    private void animatium$fixBowArmMovement(T humanoidRenderState, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixBowArmMovement()) {
            HumanoidModel.ArmPose leftArmPose = humanoidRenderState.leftArmPose;
            HumanoidModel.ArmPose rightArmPose = humanoidRenderState.rightArmPose;
            final boolean isRightArmPose = rightArmPose == HumanoidModel.ArmPose.BOW_AND_ARROW;
            final boolean isLeftArmPose = leftArmPose == HumanoidModel.ArmPose.BOW_AND_ARROW;
            if (isRightArmPose || isLeftArmPose) {
                if (isRightArmPose) {
                    rightArm.zRot = 0.0F;
                    rightArm.yRot = -0.1F + head.yRot;
                    leftArm.yRot = 0.1F + head.yRot + 0.4F;
                }

                if (isLeftArmPose) {
                    leftArm.zRot = 0.0F;
                    rightArm.yRot = -0.1F + head.yRot - 0.4F;
                    leftArm.yRot = 0.1F + head.yRot;
                }

                rightArm.xRot = (float) (-Math.PI / 2) + head.xRot;
                leftArm.xRot = (float) (-Math.PI / 2) + head.xRot;
            }
        }
    }

    @WrapOperation(method = {"poseLeftArm", "poseRightArm"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;poseBlockingArm(Lnet/minecraft/client/model/geom/ModelPart;Z)V"))
    private void animatium$oldSwordBlockArm(HumanoidModel<?> instance, ModelPart arm, boolean rightArm, Operation<Void> original, @Local(argsOnly = true) T state) {
        original.call(instance, arm, rightArm);
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getLegacyThirdpersonSwordBlockingPosition()) {
            Entity entity = EntityUtils.getEntityByState(state);
            if (entity instanceof LivingEntity livingEntity && state instanceof HumanoidRenderState) {
                ItemStack stack = rightArm ? livingEntity.getItemHeldByArm(HumanoidArm.RIGHT) : livingEntity.getItemHeldByArm(HumanoidArm.LEFT);
                if (!(stack.getItem() instanceof ShieldItem)) {
                    arm.yRot = 0;
                }
            }
        }
    }

    @ModifyExpressionValue(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;isUsingItem:Z", ordinal = 0))
    private boolean animatium$disableOffHandUsePoseNone(boolean original) {
        return (!AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getDisableOffHandUsePoseNone()) && original;
    }
}
