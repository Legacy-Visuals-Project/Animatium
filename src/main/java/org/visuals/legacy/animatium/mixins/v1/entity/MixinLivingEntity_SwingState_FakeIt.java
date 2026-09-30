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

package org.visuals.legacy.animatium.mixins.v1.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.SwingAnimation;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.visuals.legacy.animatium.util.duck.SwingStateExt;

@Mixin(LivingEntity.SwingState.class)
public abstract class MixinLivingEntity_SwingState_FakeIt implements SwingStateExt {
    @Shadow
    protected abstract void start(final InteractionHand hand, final SwingAnimation animation, final int durationTicks);

    @Shadow
    private LivingEntity.@Nullable SwingDescription currentSwing;

    @Shadow
    private int ticks;

    @Override
    public void animatium$forceSwing(final @NotNull InteractionHand hand, final @NotNull SwingAnimation animation, final int duration) {
        if (this.currentSwing == null || this.ticks > duration / 2 || this.ticks < 0) {
            this.start(hand, animation, duration);
        }
    }
}
