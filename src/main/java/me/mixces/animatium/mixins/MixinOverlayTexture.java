package me.mixces.animatium.mixins;

import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.awt.*;

@Mixin(OverlayTexture.class)
public abstract class MixinOverlayTexture {
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = -1291911168))
    private int animatium$modifyDamageTintColor(int constant) {
        Color hitColor = AnimatiumConfig.getInstance().getCustomHitColor();
        return ColorHelper.getArgb(AnimatiumConfig.getInstance().getDeepRedHurtTint() ? 128 : 178, hitColor.getRed(), hitColor.getGreen(), hitColor.getBlue());
    }
}