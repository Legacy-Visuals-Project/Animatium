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

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.renderpearl.api.textures.FilterMode
import com.mojang.renderpearl.api.textures.GpuTextureView
import org.visuals.legacy.animatium.handler.rendering.pipeline.AnimatiumPipelines
import org.visuals.legacy.animatium.renderer.buffer.BasicGeometry
import org.visuals.legacy.animatium.renderer.impl.DeferredRenderer
import org.visuals.legacy.animatium.util.profile

object ColorBoostRenderer {
    private val GEOMETRY = BasicGeometry(0, 3)

    @JvmStatic
    fun render(colorAttachment: GpuTextureView, depthAttachment: GpuTextureView) {
        profile("color_boost") {
            DeferredRenderer.of("Color Boost Blit", colorAttachment, depthAttachment).use { renderer ->
                renderer.setPipeline(AnimatiumPipelines.COLOR_BOOST_BLIT)
                renderer.setTexture(
                    "Sampler0",
                    colorAttachment,
                    RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST)
                )
                renderer.draw(GEOMETRY)
            }
        }
    }
}