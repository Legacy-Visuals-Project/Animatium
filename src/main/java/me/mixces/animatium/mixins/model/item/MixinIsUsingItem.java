package me.mixces.animatium.mixins.model.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import net.minecraft.client.renderer.item.properties.conditional.IsUsingItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IsUsingItem.class)
public abstract class MixinIsUsingItem {
    @ModifyReturnValue(method = "get", at = @At(value = "RETURN"))
    private boolean animatium$getValue(boolean original, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ItemDisplayContext displayContext) {
        if (AnimatiumConfig.instance().getDisableItemUsingTextureInGui() && ItemUtils.isRangedWeaponItem(stack) && displayContext == ItemDisplayContext.GUI) {
            return false;
        } else {
            return original;
        }
    }
}
