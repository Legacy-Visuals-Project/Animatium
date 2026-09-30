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

package org.visuals.legacy.animatium.mixins.v1.general.server_features;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode_StopBlockMining {
    @Shadow
    private boolean isDestroying;

    @WrapOperation(method = "stopDestroyBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;isDestroying:Z", opcode = Opcodes.GETFIELD))
    private boolean animatium$miningItemUsage$allowFullBlock(final MultiPlayerGameMode instance, final Operation<Boolean> original) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MINING_ITEM_USAGE)) {
            return true;
        } else {
            return original.call(instance);
        }
    }

    @WrapWithCondition(method = "stopDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V"))
    private boolean animatium$miningItemUsage$stopPacket(final ClientPacketListener instance, final Packet<?> packet) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MINING_ITEM_USAGE)) {
            return this.isDestroying;
        } else {
            return true;
        }
    }

    @WrapWithCondition(method = "stopDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;resetAttackStrengthTicker()V"))
    private boolean animatium$miningItemUsage$fixAttackProgress(final LocalPlayer instance) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MINING_ITEM_USAGE)) {
            return this.isDestroying;
        } else {
            return true;
        }
    }
}
