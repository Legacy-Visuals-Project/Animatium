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

package org.visuals.legacy.animatium.mixins.v1.entity.flame;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.util.CameraUtilKt;
import org.visuals.legacy.animatium.util.enums.SneakAnimationSetting;

@Mixin(FlameFeatureRenderer.class)
public abstract class MixinFlameFeatureRenderer_OldFlameDimensions {
    @ModifyExpressionValue(method = "prepare", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;boundingBoxWidth:F", opcode = Opcodes.GETFIELD))
    private float animatium$flameWidth(final float original, @Local(name = "state") final EntityRenderState state) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.flameDimensions && state instanceof AvatarRenderState) {
            return 0.6F;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "prepare", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;boundingBoxHeight:F", opcode = Opcodes.GETFIELD))
    private float animatium$flameHeight(final float original, @Local(name = "state") final EntityRenderState state) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.flameDimensions && state instanceof AvatarRenderState) {
            return 1.8F;
        } else {
            return original;
        }
    }

    @ModifyArg(method = "prepare", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;translate(FFF)Lorg/joml/Matrix4f;", ordinal = 0), index = 1)
    private float animatium$flameOffset(final float original, @Local(name = "state") final EntityRenderState state) {
        final Camera camera = Minecraft.getInstance().getEntityRenderDispatcher().camera;
        if (Animatium.isEnabled() && state instanceof AvatarRenderState avatarRenderState && camera != null && camera.entity().getId() == avatarRenderState.id) {
            final boolean shouldSyncPlayerModelWithEyeHeight = AnimatiumConfig.instance().movement.sneakAnimation == SneakAnimationSetting.V1_7;

            float value = original;
            if (shouldSyncPlayerModelWithEyeHeight) {
                final float cameraLerpValue = CameraUtilKt.getPositionLerped(camera);
                value = (avatarRenderState.eyeHeight * avatarRenderState.scale) - cameraLerpValue;
            }

            if (AnimatiumConfig.instance().other.flameOffset) {
                value += ((shouldSyncPlayerModelWithEyeHeight && avatarRenderState.isCrouching ? 0.140625F : 0.296875F) * avatarRenderState.scale);
            }

            return value;
        } else {
            return original;
        }
    }
}
