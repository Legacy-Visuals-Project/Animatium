package btw.mixces.animatium.mixins.screen;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.gui.Font;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Font.StringRenderOutput.class)
public abstract class MixinFontStringRenderOutput {
    @Unique
    private static final float animatium$strikethroughOffset = 0.5F;

    @ModifyArg(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;<init>(FFFFFIIF)V", ordinal = 0), index = 1)
    private float animatium$fixTextStrikethroughStyle$minY(float minY) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixTextStrikethroughStyle()) {
            return minY - animatium$strikethroughOffset;
        } else {
            return minY;
        }
    }

    @ModifyArg(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;<init>(FFFFFIIF)V", ordinal = 0), index = 3)
    private float animatium$fixTextStrikethroughStyle$maxY(float maxY) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixTextStrikethroughStyle()) {
            return maxY - animatium$strikethroughOffset;
        } else {
            return maxY;
        }
    }
}
