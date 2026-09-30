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

package org.visuals.legacy.animatium.mixins.v1.entity.items;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.SwingAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.visuals.legacy.animatium.util.SwingUtilKt;

// TODO/FIX: Should not affect swing code, only visual, currently matches Legacy Animatium tho
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity_ItemSwing {
    @WrapMethod(method = "getModifiedSwingDuration")
    private int animatium$customSwingAnimationSpeed(final SwingAnimation animation, final Operation<Integer> original) {
        return SwingUtilKt.getItemSwingSpeed((LivingEntity) (Object) this, animation, original.call(animation));
    }
}
