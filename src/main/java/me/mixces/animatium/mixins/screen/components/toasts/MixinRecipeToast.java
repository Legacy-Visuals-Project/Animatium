package me.mixces.animatium.mixins.screen.components.toasts;

import me.mixces.animatium.AnimatiumClient;
import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeToast.class)
public abstract class MixinRecipeToast {
    @Inject(method = "addOrUpdate", at = @At("HEAD"), cancellable = true)
    private static void animatium$disableRecipeAndTutorialToasts(ToastManager toastManager, RecipeDisplay recipeDisplay, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDisableRecipeAndTutorialToasts()) {
            ci.cancel();
        }
    }
}
