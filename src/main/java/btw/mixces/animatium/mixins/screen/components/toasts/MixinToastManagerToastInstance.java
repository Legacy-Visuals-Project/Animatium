package btw.mixces.animatium.mixins.screen.components.toasts;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ToastManager.ToastInstance.class)
public abstract class MixinToastManagerToastInstance<T extends Toast> {
    @Shadow
    @Final
    private T toast;

    @WrapWithCondition(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;playSound(Lnet/minecraft/client/sounds/SoundManager;)V"))
    private boolean animatium$disableRecipeAndTutorialToasts(Toast.Visibility instance, SoundManager soundManager) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getDisableRecipeAndTutorialToasts() || (!(this.toast instanceof RecipeToast) && !(this.toast instanceof TutorialToast));
    }
}
