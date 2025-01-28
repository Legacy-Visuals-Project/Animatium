package btw.mixces.animatium.mixins.renderer.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidMobRenderer.class)
public abstract class MixinHumanoidMobRenderer {
    @WrapOperation(method = "extractHumanoidRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isCrouching()Z"))
    private static boolean animatium$sneakAnimationWhileFlying(LivingEntity livingEntity, Operation<Boolean> original) {
        boolean isCrouching = original.call(livingEntity);
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getSneakAnimationWhileFlying()) {
            return isCrouching || livingEntity.isShiftKeyDown();
        } else {
            return isCrouching;
        }
    }
}
