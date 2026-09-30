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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.mixins.accessor.PlayerAccessor;
import org.visuals.legacy.animatium.util.EntityUtilKt;

@Mixin(HumanoidMobRenderer.class)
public abstract class MixinHumanoidMobRenderer_InstantSneakResponse {
    @WrapOperation(method = "extractHumanoidRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isCrouching()Z"))
    private static boolean animatium$instantSneakResponse(final LivingEntity instance, final Operation<Boolean> original) {
        if (Animatium.isEnabled()
                && AnimatiumConfig.instance().movement.sneakAnimation.isInstantResponse()
                && !instance.isSwimming()
                && EntityUtilKt.isSelf(instance)) {
            return Minecraft.getInstance().options.keyShift.isDown() || (instance instanceof Player player && !((PlayerAccessor) player).animatium$canChangeIntoPose(Pose.STANDING));
        } else {
            return original.call(instance);
        }
    }
}
