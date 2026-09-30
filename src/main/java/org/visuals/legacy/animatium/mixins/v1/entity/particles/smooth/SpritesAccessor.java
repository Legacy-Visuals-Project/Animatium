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

package org.visuals.legacy.animatium.mixins.v1.entity.particles.smooth;

import net.minecraft.client.particle.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({
        AttackSweepParticle.class,
        BaseAshSmokeParticle.class,
        BubblePopParticle.class,
        DragonBreathParticle.class,
        DustParticleBase.class,
        ExplodeParticle.class,
        FallingDustParticle.class,
        GeyserPlumeParticle.class,
        GlowParticle.class,
        GustParticle.class,
        HugeExplosionParticle.class,
        PlayerCloudParticle.class,
        SculkChargeParticle.class,
        SculkChargePopParticle.class,
        SimpleAnimatedParticle.class,
        SnowflakeParticle.class,
        SoulParticle.class,
        SpellParticle.class,
        TrialSpawnerDetectionParticle.class,
        WakeParticle.class
})
public interface SpritesAccessor {
    @Accessor("sprites")
    SpriteSet animatium$sprites();
}
