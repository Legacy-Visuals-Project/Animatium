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

package org.visuals.legacy.animatium.mixins.v1.entity.glint;

import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(TextureManager.class)
public abstract class MixinTextureManager_ArmorItemGlint {
    @ModifyVariable(method = "getTexture", at = @At("HEAD"), argsOnly = true, name = "location")
    private Identifier animatium$useItemGlint(final Identifier location) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.itemGlintOnEntity && location == ItemFeatureRenderer.ENCHANTED_GLINT_ARMOR) {
            return ItemFeatureRenderer.ENCHANTED_GLINT_ITEM;
        } else {
            return location;
        }
    }
}
