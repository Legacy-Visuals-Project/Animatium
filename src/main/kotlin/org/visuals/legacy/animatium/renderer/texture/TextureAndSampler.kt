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

package org.visuals.legacy.animatium.renderer.texture

import com.mojang.blaze3d.textures.GpuSampler
import com.mojang.blaze3d.textures.GpuTextureView
import net.minecraft.client.Minecraft
import net.minecraft.resources.Identifier

data class TextureAndSampler(val textureView: GpuTextureView?, val sampler: GpuSampler?) {
    companion object {
        @JvmStatic
        fun get(location: Identifier): TextureAndSampler {
            val texture = Minecraft.getInstance().textureManager.getTexture(location)
            return TextureAndSampler(texture.getTextureView(), texture.getSampler())
        }
    }
}