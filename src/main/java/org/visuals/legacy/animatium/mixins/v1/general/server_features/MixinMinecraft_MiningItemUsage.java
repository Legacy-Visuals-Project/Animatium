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

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_MiningItemUsage {
    @Definition(id = "gameMode", field = "Lnet/minecraft/client/Minecraft;gameMode:Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;")
    @Definition(id = "isDestroying", method = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;isDestroying()Z")
    @Expression("this.gameMode.isDestroying()")
    @ModifyExpressionValue(method = "startUseItem", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean animatium$leftClickItemUsage$miningItemUsage(final boolean original) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MINING_ITEM_USAGE) || ServerFeatureManager.isPresent(ServerFeatures.LEFT_CLICK_ITEM_USAGE)) {
            return false;
        } else {
            return original;
        }
    }

    @WrapOperation(method = "continueAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
    private boolean animatium$miningItemUsage(final LocalPlayer instance, final Operation<Boolean> original) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MINING_ITEM_USAGE)) {
            return false;
        } else {
            return original.call(instance);
        }
    }
}
