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

package org.visuals.legacy.animatium.mixins.v1.rendering.states;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.mixins.accessor.CameraAccessor;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    @Shadow
    @Final
    private Camera mainCamera;

    @Inject(method = "extractCamera", at = @At("TAIL"))
    private void animatium$setupCameraState(final CallbackInfo ci, @Local(ordinal = 0) final CameraRenderState cameraState) {
        cameraState.animatium$setPartialTickTime(this.mainCamera.getPartialTickTime());
        cameraState.animatium$setOldEyeHeight(((CameraAccessor) this.mainCamera).animatium$getOldEyeHeight());
        cameraState.animatium$setEyeHeight(((CameraAccessor) this.mainCamera).animatium$getEyeHeight());
        cameraState.animatium$setYRot(this.mainCamera.yRot());
        cameraState.animatium$setXRot(this.mainCamera.xRot());
    }
}
