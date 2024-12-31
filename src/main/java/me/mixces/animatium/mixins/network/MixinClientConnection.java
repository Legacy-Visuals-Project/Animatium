package me.mixces.animatium.mixins.network;

import me.mixces.animatium.AnimatiumClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.DisconnectionInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class MixinClientConnection {
    @Inject(method = "disconnect(Lnet/minecraft/network/DisconnectionInfo;)V", at = @At("HEAD"))
    private void animatium$restoreVanillaFunctionality(DisconnectionInfo disconnectionInfo, CallbackInfo ci) {
        AnimatiumClient.setDisableSwingMissPenalty(false);
    }
}
