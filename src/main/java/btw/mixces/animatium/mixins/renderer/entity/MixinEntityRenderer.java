package btw.mixces.animatium.mixins.renderer.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.EntityUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private <T extends Entity, S extends EntityRenderState> void animatium$saveEntityByState(T entity, S state, float tickDelta, CallbackInfo ci) {
        EntityUtils.setEntityByState(state, entity);
    }

    @WrapOperation(method = "renderNameTag", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;isDiscrete:Z"))
    private boolean animatium$sneakAnimationWhileFlying(EntityRenderState instance, Operation<Boolean> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getSneakAnimationWhileFlying() && instance instanceof LivingEntityRenderState livingEntityRenderState) {
            return livingEntityRenderState.isDiscrete || livingEntityRenderState.hasPose(Pose.CROUCHING);
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;getBackgroundOpacity(F)F"))
    private float animatium$hideNameTagBackground(Options instance, float fallback, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getHideNameTagBackground()) {
            return 0F;
        } else {
            return original.call(instance, fallback);
        }
    }

    @ModifyArg(method = "renderNameTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"), index = 4)
    private boolean animatium$applyTextShadowToNametag(boolean shadow) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getApplyTextShadowToNametag()) {
            return true;
        } else {
            return shadow;
        }
    }
}
