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

package org.visuals.legacy.animatium.mixins.v1.entity.sneaking;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Pose;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.util.enums.SneakAnimationSetting;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer_SneakAnimationWhileFlying {
    // TODO: Make match isInstantResponse handling
    @WrapOperation(method = "submitNameTag", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;isDiscrete:Z", opcode = Opcodes.GETFIELD))
    private boolean animatium$sneakAnimationWhileFlying(final EntityRenderState instance, final Operation<Boolean> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.sneakAnimation.ordinal() <= SneakAnimationSetting.V1_13.ordinal() && instance instanceof LivingEntityRenderState livingEntityRenderState) {
            return livingEntityRenderState.isDiscrete || livingEntityRenderState.hasPose(Pose.CROUCHING);
        } else {
            return original.call(instance);
        }
    }
}
