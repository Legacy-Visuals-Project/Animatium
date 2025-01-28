package btw.mixces.animatium.mixins.network;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {
    @WrapWithCondition(method = "handleContainerClose", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;clientSideCloseContainer()V"))
    private boolean animatium$dontCloseChat$containerClose(LocalPlayer instance) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getDontCloseChat() || !(Minecraft.getInstance().screen instanceof ChatScreen);
    }
}
