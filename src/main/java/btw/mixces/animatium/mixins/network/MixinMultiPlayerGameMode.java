package btw.mixces.animatium.mixins.network;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode {
    @Shadow
    private float destroyProgress;

    @Shadow
    private GameType localPlayerMode;

    @Inject(method = "getDestroyStage", at = @At(value = "RETURN"), cancellable = true)
    private void animatium$oldBlockMiningProgress(CallbackInfoReturnable<Integer> cir) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldBlockMiningProgress() && destroyProgress > 0.0F) {
            cir.setReturnValue((int) (this.destroyProgress * 10.0F));
        }
    }

    @WrapOperation(method = "performUseItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;"))
    private InteractionResult animatium$fixFireballClientsideVisual(ItemStack stack, UseOnContext useOnContext, Operation<InteractionResult> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixFireballClientsideVisual() && !stack.isEmpty() && stack.is(Items.FIRE_CHARGE) && useOnContext.getLevel().isClientSide() && !this.localPlayerMode.isCreative()) {
            return InteractionResult.SUCCESS;
        } else {
            return original.call(stack, useOnContext);
        }
    }
}
