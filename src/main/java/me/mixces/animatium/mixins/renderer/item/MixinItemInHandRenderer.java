package me.mixces.animatium.mixins.renderer.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import me.mixces.animatium.util.MathUtils;
import me.mixces.animatium.util.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemInHandRenderer.class, priority = 500)
public abstract class MixinItemInHandRenderer {
    @Shadow
    protected abstract void applyItemArmAttackTransform(PoseStack matrices, HumanoidArm arm, float swingProgress);

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private float mainHandHeight;

    @Unique
    private int currentSlot = -1;

    // TODO: Make arm partially translucent/transparent like the third-person player model (like on a team)
    @WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isInvisible()Z"))
    private boolean animatium$showArmWhileInvisible(AbstractClientPlayer instance, Operation<Boolean> original) {
        if (AnimatiumConfig.instance().getShowArmWhileInvisible()) {
            return false;
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 1))
    private void animatium$postBowTransform(PoseStack poseStack, float x, float y, float z, Operation<Void> original, @Local(argsOnly = true) AbstractClientPlayer player, @Local(argsOnly = true) InteractionHand hand) {
        int direction = PlayerUtils.getHandMultiplier(player, hand);
        if (AnimatiumConfig.instance().getTiltItemPositions()) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(direction * -335));
            poseStack.mulPose(Axis.YP.rotationDegrees(direction * -50.0F));
        }

        original.call(poseStack, x, y, z);
        if (AnimatiumConfig.instance().getTiltItemPositions()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(direction * 50.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 335));
        }
    }

    @WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
    private Item animatium$oldFirstPersonSwordBlock(ItemStack instance, Operation<Item> original, @Local(argsOnly = true) AbstractClientPlayer player, @Local(argsOnly = true) InteractionHand hand, @Local(argsOnly = true) PoseStack poseStack) {
        if (AnimatiumConfig.instance().getTiltItemPositions() && !(instance.getItem() instanceof ShieldItem)) {
            int direction = PlayerUtils.getHandMultiplier(player, hand);
            // We do this to fix a rounding error in Mojangs code.
            ItemUtils.applyLegacyFirstpersonTransforms(poseStack, direction, () -> {
                poseStack.translate(direction * -0.5F, 0.2F, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 30.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(-80.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 60.0F));
            });
            return Items.SHIELD; // Cancels the vanilla blocking code
        } else {
            return original.call(instance);
        }
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", ordinal = 1))
    private void animatium$tiltItemPositions(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equipProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci) {
        int direction = PlayerUtils.getHandMultiplier(player, hand);
        if (AnimatiumConfig.instance().getTiltItemPositions() && !ItemUtils.isBlock3d(stack, new ItemStackRenderState()) && !ItemUtils.isItemBlacklisted(stack)) {
            float angle = MathUtils.toRadians(25);
            if (ItemUtils.isFishingRodItem(stack)) {
                poseStack.mulPose(Axis.YP.rotationDegrees(direction * 180.0F));
            }

            poseStack.scale(0.6F, 0.6F, 0.6F);
            poseStack.mulPose(Axis.YP.rotationDegrees(direction * 275.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 25.0F));
            poseStack.translate(direction * (-0.2F * Math.sin(angle) + 0.4375F), -0.2F * Math.cos(angle) + 0.4375F, 0.03125F);

            poseStack.scale(1 / 0.68F, 1 / 0.68F, 1 / 0.68F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(direction * -25.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(direction * 90.0F));
            poseStack.translate(direction * -1.13 * 0.0625F, -3.2 * 0.0625F, -1.13 * 0.0625F);
        }

        if (AnimatiumConfig.instance().getOldSkullPosition() && ItemUtils.isSkullBlock(stack)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
            poseStack.scale(0.4F, 0.4F, 0.4F);

            // TODO: This is not quite right...
            poseStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
            poseStack.translate(0.0F, 0.25F, 0.0F);
            poseStack.scale(1.125F, 1.125F, 1.125F);
        }
    }

    @Inject(method = "renderArmWithItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/ItemUseAnimation;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", ordinal = 6)
            ))
    private void animatium$applyItemSwingUsage(AbstractClientPlayer abstractClientPlayer, float tickDelta, float pitch, InteractionHand interactionHand, float swingProgress, ItemStack itemStack, float equipProgress, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci, @Local HumanoidArm arm) {
        if (AnimatiumConfig.instance().getApplyItemSwingUsage()) {
            applyItemArmAttackTransform(poseStack, arm, swingProgress);
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getMainHandItem()Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack animatium$fixCopyStack(LocalPlayer instance, Operation<ItemStack> original) {
        // TODO/NOTE: This is 90% done. 10% not done. :)
        return AnimatiumConfig.instance().getFixEquipAnimationItemCheck() && !instance.isUsingItem() ? original.call(instance).copy() : original.call(instance);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isHandsBusy()Z"))
    private boolean animatium$showHeldItemInBoat(LocalPlayer instance, Operation<Boolean> original) {
        return !AnimatiumConfig.instance().getShowHeldItemInBoat() && original.call(instance);
    }

    // TODO/NOTE: I need to make sure this accounts for EVERYTHING.
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;shouldInstantlyReplaceVisibleItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z", ordinal = 0))
    private boolean animatium$fixEquipAnimationItemCheck(ItemInHandRenderer instance, ItemStack itemStack, ItemStack itemStack2, Operation<Boolean> original, @Local LocalPlayer localPlayer) {
        boolean value = original.call(instance, itemStack, itemStack2);
        if (AnimatiumConfig.instance().getFixEquipAnimationItemCheck()) {
            // In order to make sure this code doesn't break switching slots, lets ensure the slot changed
            boolean slotsMatch = this.currentSlot == localPlayer.getInventory().selected;
            // We make sure the actual item class is the same as the class of the item being swapped to (SwordItem, BowItem, etc...)
            boolean itemsMatch = ItemStack.isSameItem(itemStack, itemStack2);
            // Because the durability changes the itemstack and that results in the item equip update code reading it as a different itemstack,
            // we must ensure the durabilities are different before we skip the equip update
            boolean durabilitiesMatch = itemStack.getDamageValue() == itemStack2.getDamageValue();
            // Similar to the durability, the stack count changing results in the item equip update code reading the stack as a different stack
            boolean countMatch = itemStack.getCount() == itemStack2.getCount();
            // This check is not ideal. I need a better to check for inventory slots :( This presents a bug with mending items while a gui is open
            boolean notInGui = this.minecraft.screen == null;
            // If these conditions are met, the item update code will skip the equip animation as the stack will be immediately updated
            // Some of these checks may or may not be redundant. I will have to rewrite this to be simpler someday :)
            return (slotsMatch && itemsMatch && notInGui && (!durabilitiesMatch || !countMatch)) || value;
        } else {
            return value;
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void animatium$setEquippedItemSlot(CallbackInfo ci, @Local LocalPlayer localPlayer) {
        if (AnimatiumConfig.instance().getFixEquipAnimationItemCheck() && this.mainHandHeight < 0.1F) {
            // Cache the previous slot item to use in our comparison above
            this.currentSlot = localPlayer.getInventory().selected;
        }
    }
}
