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
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures;
import org.visuals.legacy.animatium.util.SwingUtilKt;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_MissPenalty {
    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Inject(method = "startAttack", at = @At(value = "RETURN", ordinal = 0))
    private void animatium$fakeMissPenaltySwing(final CallbackInfoReturnable<Boolean> cir) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.fakeMissPenaltySwing && this.player != null) {
            SwingUtilKt.fakeHandSwing(this.player, InteractionHand.MAIN_HAND);
        }
    }

    @WrapOperation(method = "startAttack", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;missTime:I", ordinal = 0, opcode = Opcodes.GETFIELD))
    private int animatium$disableSwingMissPenalty(final Minecraft instance, final Operation<Integer> original) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MISS_PENALTY) && (this.hitResult != null && this.hitResult.getType() != HitResult.Type.BLOCK)) {
            return 0;
        } else {
            return original.call(instance);
        }
    }
}
