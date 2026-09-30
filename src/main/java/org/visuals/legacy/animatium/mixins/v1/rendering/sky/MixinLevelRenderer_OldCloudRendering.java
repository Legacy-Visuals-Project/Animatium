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

package org.visuals.legacy.animatium.mixins.v1.rendering.sky;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.renderer.CloudRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.clouds.LegacyCloudRenderer;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer_OldCloudRendering {
    @Inject(method = "close", at = @At("TAIL"))
    private void animatium$closeLegacyClouds(final CallbackInfo ci) {
        LegacyCloudRenderer.INSTANCE.close();
    }

    @WrapOperation(method = {"allChanged", "needsUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;markForRebuild()V"))
    private void animatium$markLegacyCloudsForRebuild$1(final CloudRenderer instance, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldCloudRendering) {
            LegacyCloudRenderer.INSTANCE.markForRebuild();
        } else {
            original.call(instance);
        }
    }

    @WrapOperation(method = "lambda$addCloudsPass$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;render(ILnet/minecraft/client/CloudStatus;FILnet/minecraft/world/phys/Vec3;JF)V"))
    private void animatium$renderLegacyClouds(final CloudRenderer instance, final int color, final CloudStatus cloudStatus, final float bottomY, final int range, final Vec3 cameraPosition, final long gameTime, final float tickDelta, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldCloudRendering) {
            LegacyCloudRenderer.INSTANCE.render(color, cloudStatus, bottomY, cameraPosition, tickDelta);
        } else {
            original.call(instance, color, cloudStatus, bottomY, range, cameraPosition, gameTime, tickDelta);
        }
    }

    @WrapOperation(method = "endFrame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;endFrame()V"))
    private void animatium$endLegacyCloudsFrame(final CloudRenderer instance, final Operation<Void> original) {
        if (!Animatium.isEnabled() || !AnimatiumConfig.instance().other.oldCloudRendering) {
            original.call(instance);
        }
    }
}
