package me.mixces.animatium.mixins.renderer.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.AnimatiumClient;
import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidMobRenderer.class)
public abstract class MixinHumanoidMobRenderer {
    @WrapOperation(method = "extractHumanoidRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isCrouching()Z"))
    private static boolean animatium$sneakAnimationWhileFlying(LivingEntity instance, Operation<Boolean> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getSneakAnimationWhileFlying()) {
            return instance.isCrouching() || instance.isShiftKeyDown();
        } else {
            return original.call(instance);
        }
    }
}
