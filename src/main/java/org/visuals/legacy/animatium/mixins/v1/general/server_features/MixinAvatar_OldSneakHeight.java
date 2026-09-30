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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures;

import java.util.Map;

@Mixin(Avatar.class)
public abstract class MixinAvatar_OldSneakHeight {
    @WrapOperation(method = "getDefaultDimensions", at = @At(value = "INVOKE", target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private <V> V animatium$oldSneakHeight(final Map<Pose, EntityDimensions> instance, final Object pose, final V defaultValue, final Operation<EntityDimensions> original) {
        final EntityDimensions entityDimensions = original.call(instance, pose, defaultValue);
        if (ServerFeatureManager.isPresent(ServerFeatures.OLD_SNEAK_HEIGHT) && pose == Pose.CROUCHING) {
            return (V) new EntityDimensions(entityDimensions.width(), 1.65F, 1.54F, entityDimensions.attachments(), entityDimensions.fixed());
        } else {
            return (V) entityDimensions;
        }
    }
}
