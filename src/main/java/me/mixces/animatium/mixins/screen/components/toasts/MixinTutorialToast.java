package me.mixces.animatium.mixins.screen.components.toasts;

import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TutorialToast.class)
public abstract class MixinTutorialToast {
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void animatium$disableRecipeAndTutorialToasts(ToastManager toastManager, long l, CallbackInfo ci) {
        if (AnimatiumConfig.getInstance().getDisableRecipeAndTutorialToasts()) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void animatium$disableRecipeAndTutorialToasts(GuiGraphics guiGraphics, Font font, long l, CallbackInfo ci) {
        if (AnimatiumConfig.getInstance().getDisableRecipeAndTutorialToasts()) {
            ci.cancel();
        }
    }
}