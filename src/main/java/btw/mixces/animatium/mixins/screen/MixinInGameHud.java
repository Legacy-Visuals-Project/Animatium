package btw.mixces.animatium.mixins.screen;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.CameraType;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(Gui.class)
public abstract class MixinInGameHud {
    @WrapWithCondition(method = "onDisconnected", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;clearMessages(Z)V"))
    private boolean animatium$dontClearChatOnDisconnect(ChatComponent instance, boolean bl) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getDontClearChatOnDisconnect();
    }

    @WrapOperation(method = "renderChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;render(Lnet/minecraft/client/gui/GuiGraphics;IIIZ)V"))
    private void animatium$oldChatPosition(ChatComponent instance, GuiGraphics context, int currentTick, int mouseX, int mouseY, boolean focused, Operation<Void> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldChatPosition()) {
            context.pose().translate(0F, 12F, 0F);
        }

        original.call(instance, context, currentTick, mouseX, mouseY, focused);
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldChatPosition()) {
            context.pose().translate(0F, -12F, 0F);
        }
    }

    @WrapOperation(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/CameraType;isFirstPerson()Z"))
    private boolean animatium$showCrosshairInThirdperson(CameraType instance, Operation<Boolean> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getShowCrosshairInThirdperson()) {
            return true;
        } else {
            return original.call(instance);
        }
    }

    @WrapWithCondition(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2))
    private boolean animatium$fixHighAttackSpeedIndicator(GuiGraphics instance, Function<ResourceLocation, RenderType> function, ResourceLocation resourceLocation, int i, int j, int k, int l, @Local float f) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixHighAttackSpeedIndicator()) {
            return (int) (f * 17.0F) != 0;
        } else {
            return true;
        }
    }

    @WrapWithCondition(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V"))
    private boolean animatium$removeHeartFlash(Gui instance, GuiGraphics guiGraphics, Gui.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getRemoveHeartFlash() || !blinking || type == Gui.HeartType.CONTAINER;
    }
}
