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

fun RenderPipeline.builder() = RenderPipeline.builder().apply {
    this.withVertexShader(vertexShader)
    this.withFragmentShader(fragmentShader)
    this.withPolygonMode(polygonMode)
    this.withColorWrite(isWriteColor, isWriteAlpha)
    this.withDepthWrite(isWriteDepth)
    this.withDepthTestFunction(depthTestFunction)
    this.withDepthBias(depthBiasScaleFactor, depthBiasConstant)
    this.withCull(isCull)
    this.withVertexFormat(vertexFormat, vertexFormatMode)

    if (blendFunction.isPresent) {
        this.withBlend(blendFunction.get())
    } else {
        this.withoutBlend()
    }

    for (define in shaderDefines.values) {
        this.withShaderDefine(define.key) // TODO: Int/Float value
    }

    for (description in uniforms) {
        this.withUniform(description.name, description.type)
    }

    for (sampler in samplers) {
        this.withSampler(sampler)
    }
}