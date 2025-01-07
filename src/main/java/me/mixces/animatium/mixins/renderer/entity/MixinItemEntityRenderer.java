package me.mixces.animatium.mixins.renderer.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import me.mixces.animatium.AnimatiumClient;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.EntityUtils;
import me.mixces.animatium.util.MathUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntityRenderer.class)
public abstract class MixinItemEntityRenderer {
    @WrapOperation(method = "render(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;getSpin(FF)F"))
    private float animatium$itemDropsFaceCamera(float age, float uniqueOffset, Operation<Float> original, @Local(argsOnly = true) ItemEntityRenderState itemEntityRenderState, @Local(argsOnly = true) PoseStack poseStack) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getItemDropsFaceCamera()) {
            Entity entity = EntityUtils.getEntityByState(itemEntityRenderState);
            if (entity instanceof ItemEntity && !itemEntityRenderState.item.isGui3d()) {
                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
                return MathUtils.toRadians(180F - camera.getYRot());
            }
        }

        return original.call(age, uniqueOffset);
    }
}
