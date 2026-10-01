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
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.renderpearl.api.GpuFormat
import com.mojang.renderpearl.api.pipeline.*
import net.minecraft.client.renderer.BindGroupLayouts
import net.minecraft.client.renderer.RenderPipelines
import org.visuals.legacy.animatium.Animatium.location
import org.visuals.legacy.animatium.handler.rendering.clouds.CloudPipelineSet
import java.util.*

object AnimatiumPipelines {
    @JvmField
    val NO_DEPTH_WRITE = DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false)

    // Panorama
    @JvmField
    val PANORAMA_BLEND = BlendFunction(
        BlendFactor.SRC_ALPHA,
        BlendFactor.ONE_MINUS_SRC_ALPHA,
        BlendFactor.ONE,
        BlendFactor.ZERO
    )

    fun panoramaBlendState(colorMask: @ColorTargetState.WriteMask Int) =
        ColorTargetState(Optional.of(PANORAMA_BLEND), GpuFormat.RGBA8_UNORM, colorMask)

    @JvmField
    val TEXTURED_QUAD = RenderPipeline.builder(RenderPipelines.GLOBALS_SNIPPET)
        .withBindGroupLayouts(
            BindGroupLayouts.DYNAMIC_TRANSFORMS,
            BindGroupLayouts.PROJECTION,
            BindGroupLayouts.SAMPLER0
        )
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .buildSnippet()

    @JvmField
    val LEGACY_PANORAMA_SNIPPET = RenderPipeline.builder(TEXTURED_QUAD)
        .withVertexShader(location("core/legacy_panorama"))
        .withFragmentShader(location("core/legacy_panorama"))
        .withCull(false)
        .withVertexFormat(DefaultVertexFormat.POSITION)
        .buildSnippet()

    @JvmField
    val LEGACY_PANORAMA_1 = RenderPipelines.register(
        RenderPipeline.builder(LEGACY_PANORAMA_SNIPPET)
            .withLocation(location("pipeline/legacy_panorama_1"))
            .withColorTargetState(panoramaBlendState(ColorTargetState.WRITE_ALL))
            .build()
    )

    @JvmField
    val LEGACY_PANORAMA_2 = RenderPipelines.register(
        RenderPipeline.builder(LEGACY_PANORAMA_SNIPPET)
            .withLocation(location("pipeline/legacy_panorama_2"))
            .withColorTargetState(panoramaBlendState(ColorTargetState.WRITE_COLOR))
            .build()
    )

    @JvmField
    val LEGACY_PANORAMA_BLUR = RenderPipelines.register(
        RenderPipeline.builder(TEXTURED_QUAD)
            .withLocation(location("pipeline/legacy_panorama_blur"))
            .withVertexShader(location("core/legacy_panorama_blur"))
            .withFragmentShader(location("core/legacy_panorama_blur"))
            .withColorTargetState(panoramaBlendState(ColorTargetState.WRITE_COLOR))
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX)
            .build()
    )

    // Sky
    @JvmField
    val VOID_BOX_SNIPPET =
        RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
            .withVertexShader("core/position_color")
            .withFragmentShader("core/position_color")
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .buildSnippet()

    @JvmField
    val VOID_BOX = DepthPipelineSet.create(
        "void_box",
        NO_DEPTH_WRITE,
        VOID_BOX_SNIPPET
    )

    @JvmField
    val LEGACY_SKY_SNIPPET =
        RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
            .withLocation(location("pipeline/legacy_sky"))
            .withVertexShader(location("core/legacy_sky"))
            .withFragmentShader(location("core/legacy_sky"))
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withVertexFormat(DefaultVertexFormat.POSITION)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .buildSnippet()

    @JvmField
    val LEGACY_SKY = DepthPipelineSet.create(
        "legacy_sky",
        NO_DEPTH_WRITE,
        LEGACY_SKY_SNIPPET
    )

    @JvmField
    val LEGACY_SKY_PLANAR = DepthPipelineSet.create(
        "legacy_sky_planar",
        NO_DEPTH_WRITE,
        RenderPipeline.builder(LEGACY_SKY_SNIPPET)
            .withShaderDefine("PLANAR_FOG")
            .buildSnippet()
    )

    @JvmStatic
    fun getSkySet(planar: Boolean) = if (planar)
        LEGACY_SKY_PLANAR
    else
        LEGACY_SKY

    // Clouds
    @JvmField
    val LEGACY_CLOUDS_SNIPPET = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
        .withVertexShader(location("core/legacy_clouds"))
        .withFragmentShader(location("core/legacy_clouds"))
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .buildSnippet()

    @JvmField
    val LEGACY_CLOUDS = CloudPipelineSet.create(
        "legacy_clouds",
        LEGACY_CLOUDS_SNIPPET
    )

    @JvmField
    val LEGACY_CLOUDS_PLANAR = CloudPipelineSet.create(
        "legacy_clouds_planar",
        RenderPipeline.builder(LEGACY_CLOUDS_SNIPPET)
            .withShaderDefine("PLANAR_FOG")
            .buildSnippet()
    )

    @JvmStatic
    fun getCloudsSet(planar: Boolean) = if (planar)
        LEGACY_CLOUDS_PLANAR
    else
        LEGACY_CLOUDS

    // Color Boost
    @JvmField
    val COLOR_BOOST_BLIT: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder()
            .withLocation(location("pipeline/colorboost"))
            .withVertexShader("core/screentriangle")
            .withFragmentShader(location("core/colorboost"))
            .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
            .build()
    )

    // Lighting
    @JvmField
    val LEGACY_LIGHTMAP_INFO: BindGroupLayout = BindGroupLayout.builder()
        .withUniform("LightmapInfo", UniformType.UNIFORM_BUFFER)
        .build()

    @JvmField
    val LEGACY_LIGHTMAP: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder()
            .withLocation(location("pipeline/legacy_lightmap"))
            .withVertexShader("core/screentriangle")
            .withFragmentShader(location("core/legacy_lightmap"))
            .withBindGroupLayout(LEGACY_LIGHTMAP_INFO)
            .withColorTargetState(ColorTargetState.DEFAULT)
            .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
            .build()
    )

    // Glint
    @JvmField
    val POSITION_TEX_OVERLAY = VertexFormat.builder(0)
        .addAttribute("Position", GpuFormat.RGB32_FLOAT)
        .addAttribute("UV0", GpuFormat.RG32_FLOAT)
        .addAttribute("UV1", GpuFormat.RG16_SINT)
        .build()

    @JvmField
    val ARMOR_GLINT = RenderPipelines.GLINT.builder()
        .withLocation(location("pipeline/armor_glint"))
        .withVertexShader(location("core/armor_glint"))
        .withFragmentShader(location("core/armor_glint"))
        .withBindGroupLayout(BindGroupLayouts.SAMPLER1)
        .withVertexFormat(POSITION_TEX_OVERLAY)
        .build()
}