package btw.mixces.animatium.mixins.renderer.entity.layer;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public abstract class MixinCapeLayer {
    @Shadow
    @Final
    private HumanoidModel<PlayerRenderState> model;

    @ModifyExpressionValue(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/CapeLayer;hasLayer(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;)Z", ordinal = 1))
    private boolean animatium$removeChestplateCheck(boolean original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getCapeChestplateTranslation()) {
            return false;
        } else {
            return original;
        }
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private void animatium$capeSneakingOffset(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, PlayerRenderState playerRenderState, float f, float g, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldCapePosition() && playerRenderState.isCrouching) {
            poseStack.translate(0.0F, playerRenderState.scale * 2.0F / 16.0F, 0.0F);
        }
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", shift = At.Shift.AFTER))
    private void animatium$capeSwingRotation(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, PlayerRenderState playerRenderState, float f, float g, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getCapeSwingRotation()) {
            model.body.yRot = 0;
        }
    }
}
