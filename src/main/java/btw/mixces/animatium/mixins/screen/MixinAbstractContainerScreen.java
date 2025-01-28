package btw.mixces.animatium.mixins.screen;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreen {
    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderSlotHighlightBack(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    private boolean animatium$oldSlotHoverStyleRendering$disableBack(AbstractContainerScreen<?> instance, GuiGraphics context) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getOldSlotHoverStyleRendering();
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderSlotHighlightFront(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    private void animatium$oldSlotHoverStyleRendering(AbstractContainerScreen<?> instance, GuiGraphics context, Operation<Void> original) {
        Slot slot = this.hoveredSlot;
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldSlotHoverStyleRendering() && slot != null && slot.isHighlightable()) {
            context.fillGradient(RenderType.guiOverlay(), slot.x, slot.y, slot.x + 16, slot.y + 16, -2130706433, -2130706433, 0);
        } else {
            original.call(instance, context);
        }
    }
}
