package btw.mixces.animatium.mixins.renderer;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SkyRenderer.class)
public abstract class MixinSkyRenderer {
    @WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
    private void animatium$pushMatrix(PoseStack instance, Operation<Void> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldVoidSkyFogHeight()) {
            RenderSystem.getModelViewStack().pushMatrix();
        }
    }

    @WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private void animatium$translate(PoseStack instance, float x, float y, float z, Operation<Void> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldVoidSkyFogHeight()) {
            RenderSystem.getModelViewStack().translate(x, y, z);
        }
    }

    @WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"))
    private void animatium$popMatrix(PoseStack instance, Operation<Void> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldVoidSkyFogHeight()) {
            RenderSystem.getModelViewStack().popMatrix();
        }
    }
}
