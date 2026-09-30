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

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.handler.rendering.clouds.LegacyCloudRenderer;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_LoadLegacyClouds {
    @Shadow
    @Final
    private ReloadableResourceManager resourceManager;

    @Definition(id = "resourceManager", field = "Lnet/minecraft/client/Minecraft;resourceManager:Lnet/minecraft/server/packs/resources/ReloadableResourceManager;")
    @Definition(id = "registerReloadListener", method = "Lnet/minecraft/server/packs/resources/ReloadableResourceManager;registerReloadListener(Lnet/minecraft/server/packs/resources/PreparableReloadListener;)V")
    @Definition(id = "levelRenderer", field = "Lnet/minecraft/client/Minecraft;levelRenderer:Lnet/minecraft/client/renderer/LevelRenderer;")
    @Definition(id = "cloudRenderer", method = "Lnet/minecraft/client/renderer/LevelRenderer;cloudRenderer()Lnet/minecraft/client/renderer/CloudRenderer;")
    @Expression("this.resourceManager.registerReloadListener(this.levelRenderer.cloudRenderer())")
    @Inject(method = "<init>", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private void animatium$loadLegacyClouds(final GameConfig gameConfig, final CallbackInfo ci) {
        this.resourceManager.registerReloadListener(LegacyCloudRenderer.INSTANCE);
    }
}
