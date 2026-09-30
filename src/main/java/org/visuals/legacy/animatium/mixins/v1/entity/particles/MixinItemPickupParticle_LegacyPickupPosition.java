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

package org.visuals.legacy.animatium.mixins.v1.entity.particles;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(ItemPickupParticle.class)
public abstract class MixinItemPickupParticle_LegacyPickupPosition {
    @Shadow
    @Final
    private Entity target;

    @ModifyExpressionValue(method = "updatePosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeY()D"))
    private double animatium$itemPickupPosition(final double original) {
        // TODO: Check this setting, I think the description of it might of been wrong
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.itemPickupPosition) {
            return this.target.position().y;
        } else {
            return original;
        }
    }
}
