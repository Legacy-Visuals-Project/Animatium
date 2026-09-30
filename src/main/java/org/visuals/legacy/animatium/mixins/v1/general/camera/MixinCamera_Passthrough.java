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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.world.level.ClipContext;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(Camera.class)
public abstract class MixinCamera_Passthrough {
    @WrapOperation(method = "getMaxZoom", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/ClipContext$Block;VISUAL:Lnet/minecraft/world/level/ClipContext$Block;", opcode = Opcodes.GETSTATIC))
    private ClipContext.Block animatium$cameraTransparentPassthrough(final Operation<ClipContext.Block> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.disableCameraTransparentPassthrough) {
            return ClipContext.Block.OUTLINE;
        } else {
            return original.call();
        }
    }
}
