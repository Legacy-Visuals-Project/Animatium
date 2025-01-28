package btw.mixces.animatium.mixins.model.item;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.ElytraAnimationState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraAnimationState.class)
public class MixinElytraAnimationState {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isCrouching()Z"))
    private boolean animatium$sneakAnimationWhileFlying(LivingEntity livingEntity, Operation<Boolean> original) {
        boolean isCrouching = original.call(livingEntity);
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getSneakAnimationWhileFlying()) {
            return isCrouching || livingEntity.isShiftKeyDown();
        } else {
            return isCrouching;
        }
    }
}
