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

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer_FixSprinting extends AbstractClientPlayer {
    public MixinLocalPlayer_FixSprinting(final ClientLevel clientLevel, final GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @ModifyReturnValue(method = "isSprintingPossible", at = @At("RETURN"))
    private boolean animatium$fixItemUseSprinting(final boolean original) {
        if ((ServerFeatureManager.isPresent(ServerFeatures.FIX_SPRINT_ITEM_USE) && this.isUsingItem())
                || (ServerFeatureManager.isPresent(ServerFeatures.FIX_SPRINT_SNEAKING) && this.isCrouching())) {
            return false;
        } else {
            return original;
        }
    }
}
