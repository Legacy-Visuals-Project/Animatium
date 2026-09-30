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

package org.visuals.legacy.animatium.mixins.v1.general.camera;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.util.enums.CameraVersionSetting;

@Mixin(Camera.class)
public abstract class MixinCamera_Positioned {
    @Shadow
    private Entity entity;

    @Shadow
    private boolean detached;

    @Shadow
    protected abstract void move(final float forwards, final float up, final float right);

    @Inject(method = "setup", at = @At(value = "TAIL"))
    private void animatium$cameraVersion(final Level level, final Entity entity, final boolean detached, final boolean mirror, final float tickDelta, final CallbackInfo ci) {
        // TODO: Fix bed/sleeping position
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.cameraVersion != CameraVersionSetting.VANILLA && !this.detached && !(this.entity instanceof LivingEntity && ((LivingEntity) this.entity).isSleeping())) {
            final int ordinal = AnimatiumConfig.instance().screen.cameraVersion.ordinal();
            if (ordinal <= CameraVersionSetting.V1_14_TO_V1_14_3.ordinal()) {
                // <= 1.14.3
                this.move(-0.05000000074505806F, 0.0F, 0.0F);
                // <= 1.13.2
                if (ordinal <= CameraVersionSetting.V1_9_TO_V1_13_2.ordinal()) {
                    this.move(0.1F, 0.0F, 0.0F);
                    // <= 1.8
                    if (ordinal == CameraVersionSetting.V1_8.ordinal()) {
                        this.move(-0.15F, 0, 0); // unfixing parallax
                    }
                }
            }
        }
    }
}
