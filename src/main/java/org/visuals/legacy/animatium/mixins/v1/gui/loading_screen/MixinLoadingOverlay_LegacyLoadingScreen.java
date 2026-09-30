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

package org.visuals.legacy.animatium.mixins.v1.gui.loading_screen;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.LegacyLogoTexture;

import java.util.function.IntSupplier;

@Mixin(LoadingOverlay.class)
public abstract class MixinLoadingOverlay_LegacyLoadingScreen {
    @Shadow
    @Final
    private ReloadInstance reload;

    @Unique
    private static final Identifier animatium$MOJANG_LOGO = Identifier.withDefaultNamespace("textures/gui/title/mojang.png");

    @Inject(method = "registerTextures", at = @At("TAIL"))
    private static void animatium$loadTextures(final TextureManager textureManager, final CallbackInfo ci) {
        textureManager.registerAndLoad(animatium$MOJANG_LOGO, new LegacyLogoTexture(animatium$MOJANG_LOGO));
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/function/IntSupplier;getAsInt()I"))
    private int animatium$replaceBackgroundColor(final IntSupplier instance, final Operation<Integer> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.legacyLoadingScreen) {
            return ARGB.white(1.0F);
        } else {
            return original.call(instance);
        }
    }

    // TODO/NOTE: It still doesn't feel instant, as the debug hud renders before the title screen does meaning theres still some time inbetween
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;isReadyToFadeOut()Z"))
    private boolean animatium$instantFadeOut(final LoadingOverlay instance, final Operation<Boolean> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.legacyLoadingScreen && this.reload.isDone()) {
            return true;
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;replaceAlpha(II)I"))
    private int animatium$disableFade(final int color, final int alpha, final Operation<Integer> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.legacyLoadingScreen) {
            return color;
        } else {
            return original.call(color, alpha);
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 0))
    private void animatium$changeLogo(final GuiGraphics instance, final RenderPipeline renderPipeline, final Identifier texture, final int x, final int y, final float u, final float v, final int width, final int height, final int srcWidth, final int srcHeight, final int textureWidth, final int textureHeight, final int color, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.legacyLoadingScreen) {
            final int size = 256;
            instance.blit(RenderPipelines.GUI_TEXTURED, animatium$MOJANG_LOGO, (instance.guiWidth() - size) / 2, (instance.guiHeight() - size) / 2, 0, 0, size, size, size, size, size, size);
        } else {
            original.call(instance, renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight, color);
        }
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 1))
    private boolean animatium$disableSecondLogoDraw(final GuiGraphics instance, final RenderPipeline renderPipeline, final Identifier texture, final int x, final int y, final float u, final float v, final int width, final int height, final int srcWidth, final int srcHeight, final int textureWidth, final int textureHeight, final int color) {
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().screen.legacyLoadingScreen;
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;drawProgressBar(Lnet/minecraft/client/gui/GuiGraphics;IIIIF)V"))
    private boolean animatium$disableProgressBar(final LoadingOverlay instance, final GuiGraphics graphics, final int x0, final int y0, final int x1, final int y1, final float fade) {
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().screen.legacyLoadingScreen || AnimatiumConfig.instance().extras.legacyLoadingScreenProgressBar;
    }

    @WrapOperation(method = "drawProgressBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;color(IIII)I"))
    private int animatium$blackProgressBar(final int alpha, final int red, final int green, final int blue, final Operation<Integer> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.legacyLoadingScreen && AnimatiumConfig.instance().extras.legacyLoadingScreenProgressBar) {
            return ARGB.color(alpha, 0, 0, 0);
        } else {
            return original.call(alpha, red, green, blue);
        }
    }
}
