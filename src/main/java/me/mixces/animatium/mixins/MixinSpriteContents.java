package me.mixces.animatium.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpriteContents.class)
public abstract class MixinSpriteContents {
    @WrapOperation(method = "isPixelTransparent", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/ColorHelper;getAlpha(I)I"))
    private int animatium$upMinPixelTransparencyLimit(int argb, Operation<Integer> original) {
        int alpha = original.call(argb);
        // NOTE: Would have used ColorHelper#floatFromChannel, but it is private & accessWidener stinky
        if (AnimatiumConfig.getInstance().getUpMinPixelTransparencyLimit() && (alpha / 255.0F) <= 0.1F) {
            return 0;
        } else {
            return alpha;
        }
    }
}
