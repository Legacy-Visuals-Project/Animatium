package btw.mixces.animatium.mixins.renderer;

import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.RenderStateShard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RenderStateShard.class)
public class MixinRenderStateShard {
    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "doubleValue=8.0"))
    private static double animatium$oldGlintSpeed(double original) {
        return AnimatiumConfig.instance().getOldGlintSpeed() ? 1.0D : original;
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "floatValue=110000.0"))
    private static float animatium$oldGlintSpeed$4873(float original) {
        return AnimatiumConfig.instance().getOldGlintSpeed() ? 4873.0F : original;
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "floatValue=30000.0"))
    private static float animatium$oldGlintSpeed$3000(float original) {
        return AnimatiumConfig.instance().getOldGlintSpeed() ? 3000.0F : original;
    }
}
