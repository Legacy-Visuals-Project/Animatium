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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_LoadOverlayTextures {
    @Unique
    private final ThreadLocal<Runnable> animatium$loadOverlayTextures = ThreadLocal.withInitial(() -> null);

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;registerTextures(Lnet/minecraft/client/renderer/texture/TextureManager;)V"))
    private void animatium$disableVanillaLoading(final TextureManager textureManager, final Operation<Void> original) {
        final Runnable runnable = () -> original.call(textureManager);
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.legacyLoadingScreen) {
            this.animatium$loadOverlayTextures.set(runnable);
        } else {
            runnable.run();
        }
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/ReloadableResourceManager;createReload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/server/packs/resources/ReloadInstance;", shift = At.Shift.AFTER))
    private void animatium$loadOverlayTextures(final GameConfig gameConfig, final CallbackInfo ci) {
        final Runnable runnable = this.animatium$loadOverlayTextures.get();
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.legacyLoadingScreen && runnable != null) {
            runnable.run();
        }
    }
}
