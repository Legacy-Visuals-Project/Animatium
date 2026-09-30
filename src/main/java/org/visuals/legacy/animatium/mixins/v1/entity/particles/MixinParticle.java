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

import net.minecraft.client.particle.Particle;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(Particle.class)
public abstract class MixinParticle {
    @Shadow
    public abstract AABB getBoundingBox();

    @Shadow
    public abstract void setBoundingBox(final AABB bb);

    @Shadow
    protected abstract void setLocationFromBoundingbox();

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void animatium$particlePhysics(final double xa, final double ya, final double za, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.disableParticlePhysics) {
            ci.cancel();
            this.setBoundingBox(this.getBoundingBox().move(xa, ya, za));
            this.setLocationFromBoundingbox();
        }
    }
}
