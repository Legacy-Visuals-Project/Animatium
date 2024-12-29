package me.mixces.animatium.mixins.renderer.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import me.mixces.animatium.util.MathUtils;
import me.mixces.animatium.util.PlayerUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class MixinHeldItemRenderer {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract void applySwingOffset(MatrixStack matrices, Arm arm, float swingProgress);

    // TODO: Make arm partially translucent/transparent like the third-person player model (like on a team)
    @WrapOperation(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isInvisible()Z"))
    private boolean animatium$showArmWhileInvisible(AbstractClientPlayerEntity instance, Operation<Boolean> original) {
        if (AnimatiumConfig.getInstance().getShowArmWhileInvisible()) {
            return false;
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V", ordinal = 1))
    private void animatium$postBowTransform(MatrixStack instance, float x, float y, float z, Operation<Void> original, @Local(argsOnly = true) AbstractClientPlayerEntity player, @Local(argsOnly = true) Hand hand) {
        int direction = PlayerUtils.getHandMultiplier(player, hand);
        if (AnimatiumConfig.getInstance().getTiltItemPositions()) {
            instance.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(direction * -335));
            instance.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(direction * -50.0F));
        }

        original.call(instance, x, y, z);
        if (AnimatiumConfig.getInstance().getTiltItemPositions()) {
            instance.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(direction * 50.0F));
            instance.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(direction * 335));
        }
    }

    @WrapOperation(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    private Item animatium$oldFirstPersonSwordBlock(ItemStack instance, Operation<Item> original, @Local(argsOnly = true) AbstractClientPlayerEntity player, @Local(argsOnly = true) Hand hand, @Local(argsOnly = true) MatrixStack matrices) {
        if (AnimatiumConfig.getInstance().getTiltItemPositions() && !(instance.getItem() instanceof ShieldItem)) {
            int direction = PlayerUtils.getHandMultiplier(player, hand);
            // We do this to fix a rounding error in Mojangs code.
            ItemUtils.applyLegacyFirstpersonTransforms(matrices, direction, () -> {
                matrices.translate(direction * -0.5F, 0.2F, 0.0F);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(direction * 30.0F));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(direction * 60.0F));
            });
            return Items.SHIELD; // Cancels the vanilla blocking code
        } else {
            return original.call(instance);
        }
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 1))
    private void animatium$tiltItemPositions(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack stack, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        int direction = PlayerUtils.getHandMultiplier(player, hand);
        if (AnimatiumConfig.getInstance().getTiltItemPositions() && !ItemUtils.isBlock3d(stack, new ItemRenderState()) && !ItemUtils.isItemBlacklisted(stack)) {
            float angle = MathUtils.toRadians(25);
            if (ItemUtils.isFishingRodItem(stack)) {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(direction * 180.0F));
            }

            matrices.scale(0.6F, 0.6F, 0.6F);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(direction * 275.0F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(direction * 25.0F));
            matrices.translate(direction * (-0.2F * Math.sin(angle) + 0.4375F), -0.2F * Math.cos(angle) + 0.4375F, 0.03125F);

            matrices.scale(1 / 0.68F, 1 / 0.68F, 1 / 0.68F);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(direction * -25.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(direction * 90.0F));
            matrices.translate(direction * -1.13 * 0.0625F, -3.2 * 0.0625F, -1.13 * 0.0625F);
        }

        if (AnimatiumConfig.getInstance().getOldSkullPosition() && ItemUtils.isSkullBlock(stack)) {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F));
            matrices.scale(0.4F, 0.4F, 0.4F);

            // TODO: This is not quite right...
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-180.0F));
            matrices.translate(0.0F, 0.25F, 0.0F);
            matrices.scale(1.125F, 1.125F, 1.125F);
        }
    }

    @Inject(method = "resetEquipProgress", at = @At("HEAD"), cancellable = true)
    private void animatium$removeEquipAnimationOnItemUse(Hand hand, CallbackInfo ci) {
        ClientPlayerEntity player = this.client.player;
        if (AnimatiumConfig.getInstance().getRemoveEquipAnimationOnItemUse() && player != null && player.isUsingItem()) {
            ci.cancel();
        }
    }

    @ModifyReturnValue(method = "shouldSkipHandAnimationOnSwap", at = @At("RETURN"))
    private boolean animatium$doNotSkipHandAnimationOnSwap(boolean original) {
        return !AnimatiumConfig.getInstance().getDoNotSkipHandAnimationOnSwap() && original;
    }

    @Inject(method = "renderFirstPersonItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V", shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/item/consume/UseAction;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V", ordinal = 6)
            ))
    private void animatium$applyItemSwingUsage(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci, @Local Arm arm) {
        if (AnimatiumConfig.getInstance().getApplyItemSwingUsage()) {
            applySwingOffset(matrices, arm, swingProgress);
        }
    }
}
