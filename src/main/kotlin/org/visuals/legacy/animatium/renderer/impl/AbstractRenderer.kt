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

package org.visuals.legacy.animatium.renderer.impl

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.pipeline.BindGroupLayout
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.GpuSampler
import com.mojang.blaze3d.textures.GpuTextureView
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.resources.Identifier
import org.visuals.legacy.animatium.handler.compatibility.IrisPipeline
import org.visuals.legacy.animatium.handler.compatibility.IrisUtil
import org.visuals.legacy.animatium.renderer.DynamicTransforms
import org.visuals.legacy.animatium.renderer.buffer.Geometry
import org.visuals.legacy.animatium.renderer.texture.TextureAndSampler

abstract class AbstractRenderer : AutoCloseable {
    protected var pipeline: RenderPipeline? = null
    protected val textures = Object2ObjectOpenHashMap<String, TextureAndSampler>()
    protected val uniforms = Object2ObjectOpenHashMap<String, GpuBufferSlice>()

    fun setPipeline(pipeline: RenderPipeline, irisPipeline: IrisPipeline): AbstractRenderer {
        this.pipeline = pipeline
        IrisUtil.assignPipeline(pipeline, irisPipeline)
        return this
    }

    fun setPipeline(pipeline: RenderPipeline): AbstractRenderer {
        val samplers = BindGroupLayout.flattenSamplers(pipeline.bindGroupLayouts)
        return this.setPipeline(
            pipeline,
            if (samplers.contains("Sampler0")) {
                IrisPipeline.TEXTURED
            } else {
                IrisPipeline.BASIC
            }
        )
    }

    fun setTexture(name: String, textureAndSampler: TextureAndSampler): AbstractRenderer {
        this.textures[name] = textureAndSampler
        return this
    }

    fun setTexture(name: String, textureView: GpuTextureView, sampler: GpuSampler) = this.setTexture(name, TextureAndSampler(textureView, sampler))

    fun setTexture(name: String, location: Identifier) = this.setTexture(name, TextureAndSampler.get(location))

    fun setUniform(name: String, data: GpuBufferSlice): AbstractRenderer {
        this.uniforms[name] = data
        return this
    }

    fun setUniform(name: String, data: GpuBuffer) = this.setUniform(name, data.slice())

    abstract fun draw(geometry: Geometry)

    protected fun render(pass: RenderPass, geometry: Geometry, dynamicTransforms: GpuBufferSlice) {
        val pipeline = this.pipeline ?: throw RuntimeException("Cannot render, pipeline is null!")
        if (geometry.isClosed()) {
            throw RuntimeException("Cannot render, the provided geometry has already been closed!")
        } else {
            pass.setPipeline(pipeline)

            val bindGroupLayouts = pipeline.bindGroupLayouts
            val descriptions = BindGroupLayout.flattenUniforms(bindGroupLayouts)
                .stream()
                .map(BindGroupLayout.UniformDescription::name)
                .toList()

            val indexBuffer = RenderSystem.getSequentialBuffer(pipeline.primitiveTopology)
            RenderSystem.bindDefaultUniforms(pass)
            pass.setUniform(DynamicTransforms.KEY, dynamicTransforms)
            for (entry in this.uniforms) {
                val name = entry.key
                if (name == DynamicTransforms.KEY) {
                    continue // Special Handling Above
                }

                if (descriptions.contains(name)) {
                    pass.setUniform(entry.key, entry.value)
                }
            }

            val samplers = BindGroupLayout.flattenSamplers(bindGroupLayouts)
            for (entry in this.textures) {
                val name = entry.key
                if (samplers.contains(name)) {
                    pass.bindTexture(name, entry.value.textureView, entry.value.sampler)
                }
            }

            geometry.bind(pass, indexBuffer)
            geometry.draw(pass)
        }
    }

    override fun close() {
        this.textures.clear()
        this.uniforms.clear()
    }
}