package btw.mixces.animatium.mixins.screen;


import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChatScreen.class)
public abstract class MixinChatScreen {
    @Unique
    private static final float animatium$chatOffset = 12.0F;

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;render(Lnet/minecraft/client/gui/GuiGraphics;IIIZ)V"))
    private void animatium$oldChatPosition(ChatComponent instance, GuiGraphics context, int currentTick, int mouseX, int mouseY, boolean focused, Operation<Void> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldChatPosition()) {
            context.pose().translate(0F, animatium$chatOffset, 0F);
        }

        original.call(instance, context, currentTick, mouseX, mouseY, focused);
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldChatPosition()) {
            context.pose().translate(0F, -animatium$chatOffset, 0F);
        }
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/ChatScreen;getComponentStyleAt(DD)Lnet/minecraft/network/chat/Style;"), index = 1)
    private double animatium$oldChatPosition$fixY(double d) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldChatPosition()) {
            return d - animatium$chatOffset;
        } else {
            return d;
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;getMessageTagAt(DD)Lnet/minecraft/client/GuiMessageTag;"))
    private GuiMessageTag animatium$removeChatIndicators(ChatComponent instance, double mouseX, double mouseY, Operation<GuiMessageTag> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getRemoveChatIndicators()) {
            return null;
        } else {
            return original.call(instance, mouseX, mouseY);
        }
    }

    @ModifyArg(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;handleChatQueueClicked(DD)Z"), index = 1)
    private double animatium$oldChatPosition$clicked$0(double d) {
        return d - ((AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldChatPosition()) ? animatium$chatOffset : 0);
    }

    @ModifyArg(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/ChatScreen;getComponentStyleAt(DD)Lnet/minecraft/network/chat/Style;"), index = 1)
    private double animatium$oldChatPosition$clicked$1(double d) {
        return d - ((AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldChatPosition()) ? animatium$chatOffset : 0);
    }

    @ModifyArg(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;mouseClicked(DDI)Z"), index = 1)
    private double animatium$oldChatPosition$clicked$2(double d) {
        return d - ((AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldChatPosition()) ? animatium$chatOffset : 0);
    }
}
