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

package org.visuals.legacy.animatium.handler.rendering.pipeline

import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.renderpearl.api.pipeline.DepthStencilState
import net.minecraft.client.renderer.RenderPipelines
import org.visuals.legacy.animatium.Animatium.location

data class DepthPipelineSet(
    val defaultPipeline: RenderPipeline,
    val depthPipeline: RenderPipeline
) {
    companion object {
        fun create(name: String, depthStencilState: DepthStencilState, snippet: RenderPipeline.Snippet): DepthPipelineSet {
            val defaultPipeline = RenderPipelines.register(
                RenderPipeline.builder(snippet)
                    .withLocation(location("pipeline/${name}"))
                    .build()
            )

            val depthPipeline = RenderPipelines.register(
                RenderPipeline.builder(snippet)
                    .withLocation(location("pipeline/${name}_depth"))
                    .withDepthStencilState(depthStencilState)
                    .build()
            )

            return DepthPipelineSet(defaultPipeline, depthPipeline)
        }
    }

    fun get(useDepthAttachment: Boolean) = if (useDepthAttachment)
        depthPipeline
    else
        defaultPipeline
}