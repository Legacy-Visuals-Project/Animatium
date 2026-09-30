/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * Licensed under the PolyForm Shield License 1.0.0.
 * You may obtain a copy of the license at
 * https://polyformproject.org/licenses/shield/1.0.0
 */

package org.visuals.legacy.animatium.mixins.v1.general.network;

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
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode {
    @Shadow
    private float destroyProgress;

    @Shadow
    private GameType localPlayerMode;

    @Inject(method = "getDestroyStage", at = @At(value = "RETURN"), cancellable = true)
    private void animatium$blockMiningProgress(final CallbackInfoReturnable<Integer> cir) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.legacyBlockMiningProgress) {
            cir.setReturnValue(((int) (this.destroyProgress * 10.0F)) - 1);
        }
    }

    @WrapOperation(method = "performUseItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;"))
    private InteractionResult animatium$fixFireballClientsideVisual(final ItemStack instance, final UseOnContext context, final Operation<InteractionResult> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().fixes.fixFireballClientsideVisual && !instance.isEmpty() && instance.is(Items.FIRE_CHARGE) && context.getLevel().isClientSide() && !this.localPlayerMode.isCreative()) {
            return InteractionResult.SUCCESS;
        } else {
            return original.call(instance, context);
        }
    }
}
