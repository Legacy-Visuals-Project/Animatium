package btw.mixces.animatium.mixins.screen.components;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.mixins.accessor.AbstractSelectionListAccessor;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractScrollArea.class)
public abstract class MixinAbstractScrollArea {
    @Shadow
    private double scrollAmount;

    @Shadow
    protected abstract int contentHeight();

    @Shadow
    public abstract int maxScrollAmount();

    @Inject(method = "setScrollAmount", at = @At("HEAD"), cancellable = true)
    private void animatium$allowNegativeScrolling(double scrollY, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getCenterScrollableListWidgets() && (AbstractScrollArea) (Object) this instanceof AbstractSelectionList<?> abstractSelectionList) {
            ci.cancel();
            int maxScrollY = maxScrollAmount();
            if (maxScrollY < 0) {
                maxScrollY /= 2;
            }

            if (!((AbstractSelectionListAccessor) abstractSelectionList).shouldCenterVertically() && maxScrollY < 0) {
                maxScrollY = 0;
            }

            this.scrollAmount = Math.min(Math.max(0, scrollY), maxScrollY);
        }
    }

    @Inject(method = "maxScrollAmount", at = @At("HEAD"), cancellable = true)
    public void animatium$modifyMaxScroll(CallbackInfoReturnable<Integer> cir) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getCenterScrollableListWidgets() && (AbstractScrollArea) (Object) this instanceof AbstractSelectionList<?> abstractSelectionList) {
            cir.setReturnValue(this.contentHeight() - abstractSelectionList.getHeight());
        }
    }
}
