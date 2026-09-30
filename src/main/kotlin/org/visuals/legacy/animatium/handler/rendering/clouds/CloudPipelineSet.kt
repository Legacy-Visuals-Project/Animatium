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

package org.visuals.legacy.animatium.handler.rendering.clouds

import com.mojang.blaze3d.pipeline.BlendFunction
import com.mojang.blaze3d.pipeline.ColorTargetState
import com.mojang.blaze3d.pipeline.RenderPipeline
import net.minecraft.client.CloudStatus
import net.minecraft.client.renderer.RenderPipelines
import org.visuals.legacy.animatium.Animatium.location
import java.util.*

data class CloudPipelineSet(
    val defaultPipeline: RenderPipeline,
    val depthOnlyPipeline: RenderPipeline,
    val flatPipeline: RenderPipeline
) {
    companion object {
        fun create(name: String, snippet: RenderPipeline.Snippet): CloudPipelineSet {
            val defaultPipeline = RenderPipelines.register(
                RenderPipeline.builder(snippet)
                    .withLocation(location("pipeline/${name}"))
                    .build()
            )

            val depthOnlyPipeline = RenderPipelines.register(
                RenderPipeline.builder(snippet)
                    .withLocation(location("pipeline/${name}_depth_only"))
                    .withColorTargetState(
                        ColorTargetState(
                            Optional.of(BlendFunction.TRANSLUCENT),
                            ColorTargetState.WRITE_NONE
                        )
                    )
                    .build()
            )

            val flatPipeline = RenderPipelines.register(
                RenderPipeline.builder(snippet)
                    .withLocation(location("pipeline/${name}_flat"))
                    .withCull(false)
                    .build()
            )

            return CloudPipelineSet(defaultPipeline, depthOnlyPipeline, flatPipeline)
        }
    }

    fun get(status: CloudStatus) = if (status == CloudStatus.FANCY)
        defaultPipeline
    else
        flatPipeline
}