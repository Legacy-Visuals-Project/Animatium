package btw.mixces.animatium.mixins.screen;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSelectionList.class)
public abstract class MixinAbstractSelectionList {
    @Inject(method = "renderWidget", at = @At("HEAD"))
    private void animatium$updateScroll(GuiGraphics context, int mouseX, int mouseY, float tickDelta, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getCenterScrollableListWidgets()) {
            ((AbstractScrollArea) (Object) this).refreshScrollAmount();
        }
    }

    @WrapOperation(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;isFocused()Z"))
    private boolean animatium$oldListWidgetSelectedBorderColor(AbstractSelectionList<?> instance, Operation<Boolean> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldListWidgetSelectedBorderColor()) {
            return false;
        } else {
            return original.call(instance);
        }
    }
}
