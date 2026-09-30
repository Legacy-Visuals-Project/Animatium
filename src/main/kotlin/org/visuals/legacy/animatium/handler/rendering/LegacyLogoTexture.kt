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

package org.visuals.legacy.animatium.handler.rendering

import com.mojang.blaze3d.platform.NativeImage
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.MipmapStrategy
import net.minecraft.client.renderer.texture.ReloadableTexture
import net.minecraft.client.renderer.texture.TextureContents
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection
import net.minecraft.resources.Identifier
import net.minecraft.server.packs.resources.ResourceManager

class LegacyLogoTexture(id: Identifier) : ReloadableTexture(id) {
    override fun loadContents(resourceManager: ResourceManager): TextureContents {
        Minecraft.getInstance().resourceManager.open(this.resourceId()).use { inputStream ->
            // TODO: Get real metadata file
            return TextureContents(
                NativeImage.read(inputStream),
                TextureMetadataSection(
                    TextureMetadataSection.DEFAULT_BLUR,
                    TextureMetadataSection.DEFAULT_CLAMP,
                    MipmapStrategy.AUTO,
                    TextureMetadataSection.DEFAULT_ALPHA_CUTOFF_BIAS
                )
            )
        }
    }
}