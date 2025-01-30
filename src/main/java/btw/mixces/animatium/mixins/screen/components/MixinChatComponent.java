package btw.mixces.animatium.mixins.screen.components;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public abstract class MixinChatComponent {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/GuiMessage$Line;tag()Lnet/minecraft/client/GuiMessageTag;"))
    private GuiMessageTag animatium$oldChatVisual$removeChatIndicatorBar(GuiMessage.Line instance, Operation<GuiMessageTag> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldChatVisual()) {
            return null;
        } else {
            return original.call(instance);
        }
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIIII)V"))
    private boolean animatium$oldChatVisual$removeScrollbar(GuiGraphics instance, int i, int j, int k, int l, int m, int n) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getOldChatVisual();
    }

    @Inject(method = "clearMessages", at = @At("HEAD"), cancellable = true)
    private void animatium$dontClearChat(boolean bl, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDontClearChat()) {
            ci.cancel();
        }
    }
}
