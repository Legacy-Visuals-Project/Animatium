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

package org.visuals.legacy.animatium.mixins.v1.entity.cape.movement;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Avatar;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(AvatarRenderer.class)
public abstract class MixinAvatarRenderer_CapeRotation<AvatarLikeEntity extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<AvatarLikeEntity, AvatarRenderState, PlayerModel> {
    public MixinAvatarRenderer_CapeRotation(final EntityRendererProvider.Context context, final PlayerModel model, final float shadow) {
        super(context, model, shadow);
    }

    @WrapOperation(method = "extractCapeState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(FFF)F"))
    private static float animatium$changeLerpMethod(final float delta, final float start, final float end, final Operation<Float> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.oldCapeMovement) {
            return Mth.lerp(delta, start, end);
        } else {
            return original.call(delta, start, end);
        }
    }

    @ModifyArg(method = "extractCapeState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 1), index = 2)
    private static float animatium$uncapRotation(final float original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.disableCapeLean) {
            return Float.MAX_VALUE;
        } else {
            return original;
        }
    }

    @WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;capeLean:F", ordinal = 1, opcode = Opcodes.PUTFIELD))
    private static boolean animatium$dontAssignLeanField(final AvatarRenderState instance, final float value) {
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().movement.oldCapeMovement;
    }

    @WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;capeLean2:F", ordinal = 1, opcode = Opcodes.PUTFIELD))
    private static boolean animatium$dontAssignLean2Field(final AvatarRenderState instance, final float value) {
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().movement.oldCapeMovement;
    }
}
