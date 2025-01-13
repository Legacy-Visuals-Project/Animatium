package btw.mixces.animatium.mixins.model.item;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.item.properties.conditional.FishingRodCast;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FishingRodCast.class)
public abstract class MixinFishingRodCast {
    @ModifyReturnValue(method = "get", at = @At(value = "RETURN", ordinal = 0))
    private boolean animatium$getValue(boolean original, @Local(argsOnly = true) ItemDisplayContext displayContext) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDisableItemUsingTextureInGui() && displayContext == ItemDisplayContext.GUI) {
            return false;
        } else {
            return (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldFishingRodTextureStackCheck()) || original;
        }
    }
}
