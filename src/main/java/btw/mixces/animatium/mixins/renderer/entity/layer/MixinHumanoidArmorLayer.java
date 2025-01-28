package btw.mixces.animatium.mixins.renderer.entity.layer;

import btw.mixces.animatium.util.EntityUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class MixinHumanoidArmorLayer<S extends HumanoidRenderState> {
    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V", at = @At("HEAD"))
    private void animatium$captureHumanRenderState(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, S humanoidRenderState, float f, float g, CallbackInfo ci) {
        EntityUtils.setHumanRenderState(humanoidRenderState);
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V", at = @At("TAIL"))
    private void animatium$releaseHumanRenderState(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, S humanoidRenderState, float f, float g, CallbackInfo ci) {
        EntityUtils.removeHumanRenderState();
    }
}