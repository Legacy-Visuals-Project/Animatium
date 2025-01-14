package btw.mixces.animatium.mixins;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.awt.*;

@Mixin(OverlayTexture.class)
public abstract class MixinOverlayTexture {
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = -1291911168))
    private int animatium$modifyDamageTintColor(int constant) {
        if (AnimatiumClient.getEnabled()) {
            Color hitColor = AnimatiumConfig.instance().getCustomHitColor();
            return ARGB.color(AnimatiumConfig.instance().getDeepRedHurtTint() ? 128 : 178, hitColor.getRed(), hitColor.getGreen(), hitColor.getBlue());
        } else {
            return constant;
        }
    }
}
