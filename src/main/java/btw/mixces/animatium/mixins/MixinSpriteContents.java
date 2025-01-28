package btw.mixces.animatium.mixins;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpriteContents.class)
public abstract class MixinSpriteContents {
    @WrapOperation(method = "isTransparent", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;alpha(I)I"))
    private int animatium$upMinPixelTransparencyLimit(int argb, Operation<Integer> original) {
        int alpha = original.call(argb);
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getUpMinPixelTransparencyLimit() && (alpha / 255.0F) <= 0.1F) {
            return 0;
        } else {
            return alpha;
        }
    }
}
