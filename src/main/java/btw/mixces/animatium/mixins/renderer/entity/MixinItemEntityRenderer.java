package btw.mixces.animatium.mixins.renderer.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.MathUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class MixinItemEntityRenderer {
    @WrapOperation(method = "render(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;getSpin(FF)F"))
    private float animatium$itemDropsFaceCamera(float age, float uniqueOffset, Operation<Float> original, @Local(argsOnly = true) ItemEntityRenderState itemEntityRenderState, @Local(argsOnly = true) PoseStack poseStack) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getItemDropsFaceCamera()) {
            if (!itemEntityRenderState.item.isGui3d()) {
                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
                return MathUtils.toRadians(180F - camera.getYRot());
            }
        }

        return original.call(age, uniqueOffset);
    }

    @Inject(method = "render(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V", shift = At.Shift.AFTER))
    private void animatium$fixItemDrops2dRotation(ItemEntityRenderState itemEntityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getItemDropsFaceCamera() && AnimatiumConfig.instance().getItemDropsFaceCameraRotationFix()) {
            if (!itemEntityRenderState.item.isGui3d()) {
                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
                poseStack.mulPose(Axis.XP.rotationDegrees(-camera.getXRot()));
            }
        }
    }
}
