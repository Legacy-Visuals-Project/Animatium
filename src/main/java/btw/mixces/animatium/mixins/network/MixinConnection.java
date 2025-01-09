package btw.mixces.animatium.mixins.network;

import btw.mixces.animatium.AnimatiumClient;
import net.minecraft.network.Connection;
import net.minecraft.network.DisconnectionDetails;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public abstract class MixinConnection {
    @Inject(method = "disconnect(Lnet/minecraft/network/DisconnectionDetails;)V", at = @At("HEAD"))
    private void animatium$restoreVanillaFunctionality(DisconnectionDetails disconnectionDetails, CallbackInfo ci) {
        AnimatiumClient.getEnabledFeatures().clear();
    }
}
