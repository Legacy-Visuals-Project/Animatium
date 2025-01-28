package btw.mixces.animatium.mixins.model.item;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.model.ElytraModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraModel.class)
public class MixinElytraModel {
    @ModifyExpressionValue(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At(value = "CONSTANT", args = "floatValue=3.0"))
    private float animatium$fixSneakTranslationWhileFlying(float original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldSneakingFeetPosition()) {
            return 0.0F;
        } else {
            return original;
        }
    }
}
