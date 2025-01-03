package me.mixces.animatium.mixins.network;

import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode {
    @Shadow
    private float destroyProgress;

    @Inject(method = "getDestroyStage", at = @At(value = "RETURN"), cancellable = true)
    private void animatium$oldBlockMiningProgress(CallbackInfoReturnable<Integer> cir) {
        if (AnimatiumConfig.instance().getOldBlockMiningProgress() && destroyProgress > 0.0F) {
            cir.setReturnValue((int) (this.destroyProgress * 10.0f));
        }
    }
}
