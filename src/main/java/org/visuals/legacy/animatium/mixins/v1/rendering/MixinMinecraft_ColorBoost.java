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

package org.visuals.legacy.animatium.mixins.v1.rendering;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.ColorBoostRenderer;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_ColorBoost {
    @Shadow
    @Final
    public Gui gui;

    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    public abstract RenderTarget getMainRenderTarget();

    @WrapOperation(method = "runTick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;blitToScreen()V"))
    private void animatium$colorBoost(final RenderTarget instance, final Operation<Void> original) {
        final RenderTarget renderTarget = this.getMainRenderTarget();
        final GpuTextureView colorTextureView = renderTarget.getColorTextureView();
        if (AnimatiumConfig.instance().extras.colorBoost && colorTextureView != null && this.screen == null) {
            ColorBoostRenderer.render(colorTextureView, renderTarget.getDepthTextureView());
        }

        original.call(instance);
    }
}
