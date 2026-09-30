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
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures;
import org.visuals.legacy.animatium.util.EntityUtilKt;

@Mixin(FishingHookRenderer.class)
public abstract class MixinFishingHookRenderer_HideFirstPersonBobber {
    @ModifyReturnValue(method = "shouldRender(Lnet/minecraft/world/entity/projectile/FishingHook;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z", at = @At("RETURN"))
    private boolean animatium$hideBobberAttachedToSelf(final boolean original, @Local(argsOnly = true, name = "entity") final FishingHook entity) {
        if (ServerFeatureManager.isPresent(ServerFeatures.HIDE_FIRST_PERSON_ROD_BOBBER) && entity.getHookedIn() instanceof Entity hook && EntityUtilKt.isSelf(hook)) {
            return false;
        } else {
            return original;
        }
    }
}
