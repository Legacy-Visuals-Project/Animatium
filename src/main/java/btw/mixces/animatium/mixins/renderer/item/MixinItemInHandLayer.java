package btw.mixces.animatium.mixins.renderer.item;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.EntityUtils;
import btw.mixces.animatium.util.ItemUtils;
import btw.mixces.animatium.util.PlayerUtils;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemUseAnimation;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ItemInHandLayer.class)
public abstract class MixinItemInHandLayer<S extends ArmedEntityRenderState, M extends EntityModel<S> & ArmedModel> extends RenderLayer<S, M> {
    public MixinItemInHandLayer(RenderLayerParent<S, M> context) {
        super(context);
    }

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    private void animatium$setRef(S armedEntityRenderState, ItemStackRenderState itemStackRenderState, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci, @Share("stack") LocalRef<ItemStack> stackRef) {
        if (AnimatiumClient.getEnabled() && ItemUtils.shouldTiltItemPositionsInThirdperson(armedEntityRenderState) && !itemStackRenderState.isEmpty()) {
            Entity entity = EntityUtils.getEntityByState(armedEntityRenderState);
            if (entity instanceof LivingEntity livingEntity && armedEntityRenderState instanceof ArmedEntityRenderState) {
                stackRef.set(livingEntity.getItemHeldByArm(humanoidArm));
            }
        }
    }

    @ModifyArgs(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private void animatium$oldTransformTranslation(Args args, @Local(argsOnly = true) S entityState, @Share("stack") LocalRef<ItemStack> stackRef) {
        if (AnimatiumClient.getEnabled() && ItemUtils.shouldTiltItemPositionsInThirdperson(entityState) && !ItemUtils.isItemBlacklisted(stackRef.get())) {
            args.setAll((float) args.get(0) * -1.0F, 0.4375F, (float) args.get(2) / 10 * -1.0F);
        }
    }

    @WrapWithCondition(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V"))
    private boolean animatium$removeTransformMultiply(PoseStack instance, Quaternionf quaternionf, @Local(argsOnly = true) S entityState, @Share("stack") LocalRef<ItemStack> stackRef) {
        return !AnimatiumClient.getEnabled() || !ItemUtils.shouldTiltItemPositionsInThirdperson(entityState) || ItemUtils.isItemBlacklisted(stackRef.get());
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"))
    private void animatium$tiltItemPositionsThird(S entityRenderState, ItemStackRenderState itemStackRenderState, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && ItemUtils.shouldTiltItemPositionsInThirdperson(entityRenderState)) {
            Entity entity = EntityUtils.getEntityByState(entityRenderState);
            if (entity instanceof LivingEntity livingEntity && entityRenderState instanceof ArmedEntityRenderState armedEntityRenderState) {
                int direction = PlayerUtils.getArmMultiplier(humanoidArm);
                ItemStack stack = livingEntity.getItemHeldByArm(humanoidArm);
                Item item = stack.getItem();
                if (!stack.isEmpty() && !ItemUtils.isItemBlacklisted(stack)) {
                    boolean isStickRod = AnimatiumClient.getEnabled() &&
                            AnimatiumConfig.instance().getUseStickModelWhenCastInThirdperson() &&
                            item == Items.FISHING_ROD &&
                            (livingEntity instanceof Player player && player.fishing != null);
                    if (ItemUtils.isBlock3d(stack, itemStackRenderState)) {
                        float scale = 0.375F;
                        poseStack.translate(0.0F, 0.1875F, -0.3125F);
                        poseStack.mulPose(Axis.XP.rotationDegrees(20.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * 45.0F));
                        poseStack.scale(-scale, -scale, scale);
                    } else if (item instanceof BowItem) {
                        float scale = 0.625F;
                        poseStack.translate(direction * 0.0F, 0.125F, 0.3125F);
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * -20.0F));
                        poseStack.translate(direction * -0.0625F, 0.0F, 0.0F);
                        poseStack.scale(scale, scale, scale);
                        poseStack.mulPose(Axis.XP.rotationDegrees(180));
                        poseStack.mulPose(Axis.XP.rotationDegrees(100.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * -145.0F));
                        poseStack.translate(-0.011765625F, 0.0F, 0.002125F);
                    } else if (ItemUtils.isHandheldItem(stack)) {
                        float scale = 0.625F;
                        if (ItemUtils.isFishingRodItem(stack) && !isStickRod) {
                            poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 180.0F));
                            poseStack.translate(0.0F, -0.125F, 0.0F);
                        }

                        if (livingEntity instanceof Player && livingEntity.getUseItemRemainingTicks() > 0 && item.getUseAnimation(stack) == ItemUseAnimation.BLOCK && PlayerUtils.isBlockingArm(humanoidArm, armedEntityRenderState)) {
                            poseStack.translate(direction * 0.05F, 0.0F, -0.1F);
                            poseStack.mulPose(Axis.YP.rotationDegrees(direction * -50.0F));
                            poseStack.mulPose(Axis.XP.rotationDegrees(-10.0F));
                            poseStack.mulPose(Axis.ZP.rotationDegrees(direction * -60.0F));
                        }

                        poseStack.translate(direction * -0.0625F, 0.1875F, 0.0F);
                        poseStack.scale(scale, scale, scale);
                        poseStack.mulPose(Axis.XP.rotationDegrees(180));
                        poseStack.mulPose(Axis.XP.rotationDegrees(100));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * -145));
                        poseStack.translate(-0.011765625F, 0.0F, 0.002125F);
                    } else {
                        float scale = 0.375F;
                        poseStack.translate(direction * 0.25F, 0.1875F, -0.1875F);
                        poseStack.scale(scale, scale, scale);
                        poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 60.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 20.0F));
                    }

                    if (!ItemUtils.isBlock3d(stack, itemStackRenderState)) {
                        poseStack.translate(0.0F, -0.3F, 0.0F);
                        poseStack.scale(1.5F, 1.5F, 1.5F);
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * 50.0F));
                        poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 335.0F));
                        poseStack.translate(direction * -0.9375F, -0.0625F, 0.0F);

                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * 180.0F));
                        poseStack.translate(direction * -0.5F, 0.5F, 0.03125F);
                    }

                    if (ItemUtils.isBlock3d(stack, itemStackRenderState)) {
                        poseStack.scale(1 / 0.375F, 1 / 0.375F, 1 / 0.375F);
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * -45.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(-75.0F));
                        poseStack.translate(0.0F, -2.5F * 0.0625F, 0.0F);
                    } else if (item instanceof BowItem) {
                        poseStack.scale(1 / 0.9F, 1 / 0.9F, 1 / 0.9F);
                        poseStack.mulPose(Axis.ZP.rotationDegrees(direction * 40.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * -260.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(80.0F));
                        poseStack.translate(direction * 0.0625F, 2.0F * 0.0625F, -2.5F * 0.0625F);
                    } else if (ItemUtils.isHandheldItem(stack)) {
                        boolean isRod = ItemUtils.isFishingRodItem(stack) && !isStickRod;
                        poseStack.scale(1 / 0.85F, 1 / 0.85F, 1 / 0.85F);
                        poseStack.mulPose(Axis.ZP.rotationDegrees(direction * -55.0F));
                        poseStack.mulPose(Axis.YP.rotationDegrees(direction * 90.0F));
                        if (isRod) {
                            poseStack.mulPose(Axis.YP.rotationDegrees(direction * -180.0F));
                        }

                        poseStack.translate(0.0F, -4.0F * 0.0625F, -0.5F * 0.0625F);
                        if (isRod) {
                            poseStack.translate(0.0F, 0.0F, -2.0F * 0.0625F);
                        }
                    } else {
                        poseStack.scale(1 / 0.55F, 1 / 0.55F, 1 / 0.55F);
                        poseStack.translate(0.0F, -3.0F * 0.0625F, -1.0F * 0.0625F);
                    }
                }
            }
        }
    }
}
