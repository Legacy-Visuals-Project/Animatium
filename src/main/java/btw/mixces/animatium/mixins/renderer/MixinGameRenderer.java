package btw.mixces.animatium.mixins.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.ViewBobbingStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Mutable
    @Shadow
    @Final
    private OverlayTexture overlayTexture;

    @Inject(method = "tick", at = @At("HEAD"))
    private void animatium$reloadOverlayTexture(CallbackInfo ci) {
        if (AnimatiumClient.getShouldReloadOverlayTexture()) {
            this.overlayTexture = new OverlayTexture();
            AnimatiumClient.setShouldReloadOverlayTexture(false);
        }
    }

    @WrapOperation(method = "bobHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getHurtDir()F"))
    private float animatium$revertYaw(LivingEntity instance, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldDamageTilt()) {
            return 0.0F;
        } else {
            return original.call(instance);
        }
    }

    @Inject(method = "bobView", at = @At("TAIL"))
    private void animatium$fixVerticalBobbingTilt(PoseStack poseStack, float tickDelta, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixVerticalBobbingTilt() && this.minecraft.getCameraEntity() instanceof Player player) {
            ViewBobbingStorage bobbingAccessor = (ViewBobbingStorage) player;
            float j = Mth.lerp(tickDelta, bobbingAccessor.animatium$getPreviousBobbingTilt(), bobbingAccessor.animatium$getBobbingTilt());
            poseStack.mulPose(Axis.XP.rotationDegrees(j));
        }
    }

    @WrapOperation(method = "bobView", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/AbstractClientPlayer;walkDist:F"))
    private float animatium$changeDistance(AbstractClientPlayer instance, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldViewBobbing()) {
            return ((ViewBobbingStorage) instance).animatium$getHorizontalSpeed();
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "bobView", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/AbstractClientPlayer;walkDistO:F"))
    private float animatium$changePreviousDistance(AbstractClientPlayer instance, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldViewBobbing()) {
            return ((ViewBobbingStorage) instance).animatium$getPreviousHorizontalSpeed();
        } else {
            return original.call(instance);
        }
    }

    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"))
    private boolean animatium$minimalViewBobbing(GameRenderer instance, PoseStack poseStack, float tickDelta) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getMinimalViewBobbing();
    }

    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;shouldRenderBlockOutline()Z"))
    private boolean animatium$persistentBlockOutline(GameRenderer instance, Operation<Boolean> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getPersistentBlockOutline()) {
            return true;
        } else {
            return original.call(instance);
        }
    }
}