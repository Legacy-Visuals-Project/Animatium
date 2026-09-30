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
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.renderpearl.api.pipeline.BindGroupLayout
import com.mojang.renderpearl.api.pipeline.ShaderType
import java.util.*

fun RenderPipeline.Builder.withVertexFormat(vertexFormat: VertexFormat) = withVertexBinding(0, vertexFormat)

fun RenderPipeline.Builder.withBindGroupLayouts(vararg layouts: BindGroupLayout): RenderPipeline.Builder {
    for (layout in layouts) {
        this.withBindGroupLayout(layout)
    }

    return this
}

fun RenderPipeline.builder() = builderIgnoreDefines()

fun RenderPipeline.builderIgnoreDefines(vararg ignoreDefines: String) = RenderPipeline.builder().apply {
    for ((type, path) in shaders) {
        if (type == ShaderType.VERTEX) {
            this.withVertexShader(path)
        } else if (type == ShaderType.FRAGMENT) {
            this.withFragmentShader(path)
        }
    }

    this.withPolygonMode(polygonMode)
    this.withCull(isCull)

    this.withDepthStencilState(Optional.ofNullable(depthStencilState))
    for ((index, state) in colorTargetStates.withIndex()) {
        if (state != null) {
            this.withColorTargetState(index, state)
        } else {
            this.withUnusedColorTargetState(index)
        }
    }

    this.withPrimitiveTopology(primitiveTopology)
    for ((binding, vertexFormat) in vertexFormatBindings.withIndex()) {
        if (vertexFormat != null) {
            this.withVertexBinding(binding, vertexFormat)
        }
    }

    for (define in shaderDefines.values) {
        if (define.key in ignoreDefines) {
            continue
        }

        this.withShaderDefine(define.key) // TODO: Int/Float value
    }

    for (layout in bindGroupLayouts) {
        this.withBindGroupLayout(layout)
    }
}