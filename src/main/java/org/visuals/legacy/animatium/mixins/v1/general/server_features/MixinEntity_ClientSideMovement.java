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

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures;

@Mixin(Entity.class)
public abstract class MixinEntity_ClientSideMovement {
    @Inject(method = "isLocalInstanceAuthoritative", at = @At("HEAD"), cancellable = true)
    private void animatium$clientSideEntityMovement(final CallbackInfoReturnable<Boolean> cir) {
        if (ServerFeatureManager.isPresent(ServerFeatures.CLIENTSIDE_ENTITIES) && !(((Entity) (Object) this) instanceof Player)) {
            cir.setReturnValue(true);
        }
    }
}
