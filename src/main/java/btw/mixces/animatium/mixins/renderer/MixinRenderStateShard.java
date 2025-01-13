package btw.mixces.animatium.mixins.renderer;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.RenderStateShard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RenderStateShard.class)
public class MixinRenderStateShard {
    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"))
    private static Object animatium$forceMaxGlintSpeed(Object original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getForceMaxGlintProperties()) {
            // 100% glint speed
            return 1.0D;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "doubleValue=8.0"))
    private static double animatium$oldGlintSpeed(double original, @Local(argsOnly = true) float f) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldGlintSpeed() && f == 8.0F) {
            // Value taken from 1.8
            return 1.0D;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "floatValue=110000.0"))
    private static float animatium$oldGlintSpeed$horizontal(float original, @Local(argsOnly = true) float f) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldGlintSpeed() && f == 8.0F) {
            // Value taken from 1.7/1.8
            return 4873.0F;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "floatValue=30000.0"))
    private static float animatium$oldGlintSpeed$diagonal(float original, @Local(argsOnly = true) float f) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldGlintSpeed() && f == 8.0F) {
            // Value taken from 1.7/1.8
            return 3000.0F;
        } else {
            return original;
        }
    }
}
