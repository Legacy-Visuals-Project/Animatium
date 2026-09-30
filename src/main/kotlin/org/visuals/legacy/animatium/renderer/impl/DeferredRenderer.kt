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

import com.mojang.blaze3d.ProjectionType
import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.GpuTextureView
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.ProjectionMatrixBuffer
import org.joml.Matrix4f
import org.visuals.legacy.animatium.renderer.DynamicTransforms
import org.visuals.legacy.animatium.renderer.RenderDescriptor
import org.visuals.legacy.animatium.renderer.buffer.Geometry

class DeferredRenderer(private val descriptor: RenderDescriptor) : AbstractRenderer() {
    companion object {
        @JvmStatic
        fun of(descriptor: RenderDescriptor) = DeferredRenderer(descriptor)

        @JvmStatic
        fun of(name: String, colorTextureView: GpuTextureView, depthTextureView: GpuTextureView?, area: RenderDescriptor.Area) =
            of(RenderDescriptor.builder { name }
                .withColorTexture(colorTextureView)
                .withDepthTexture(depthTextureView)
                .withArea(area)
                .build())

        @JvmStatic
        fun of(name: String, colorTextureView: GpuTextureView, depthTextureView: GpuTextureView?) =
            of(name, colorTextureView, depthTextureView, RenderDescriptor.Area(colorTextureView))

        @JvmStatic
        fun of(name: String, renderTarget: RenderTarget) =
            of(RenderDescriptor.builder { name }
                .withRenderTarget(renderTarget, false)
                .withArea(RenderDescriptor.Area(renderTarget))
                .build())

        @JvmStatic
        fun of(name: String) = of(name, Minecraft.getInstance().gameRenderer.mainRenderTarget())
    }

    // Data
    private val name: String = descriptor.name.get()
    private var projectionMatrix: Matrix4f? = null
    private var projectionMatrixBuffer: ProjectionMatrixBuffer? = null

    fun setProjectionMatrix(matrix4f: Matrix4f): AbstractRenderer {
        if (this.projectionMatrixBuffer == null) {
            this.projectionMatrixBuffer = ProjectionMatrixBuffer("Immediate Projection Buffer for " + this.name)
        }

        this.projectionMatrix = matrix4f
        return this
    }

    override fun draw(geometry: Geometry) {
        val hasProjectionModifier = this.projectionMatrixBuffer != null && this.projectionMatrix != null
        if (hasProjectionModifier) {
            val properties = this.projectionMatrix!!.properties()
            val projectionType = if ((properties and Matrix4f.PROPERTY_PERSPECTIVE.toInt()) != 0) {
                ProjectionType.PERSPECTIVE
            } else {
                ProjectionType.ORTHOGRAPHIC
            }

            RenderSystem.backupProjectionMatrix()
            RenderSystem.setProjectionMatrix(this.projectionMatrixBuffer!!.getBuffer(this.projectionMatrix!!), projectionType)
        }

        this.descriptor.createPass().use { pass ->
            val dynamicTransforms = this.uniforms.getOrDefault(DynamicTransforms.KEY, DynamicTransforms.current())
            this.render(pass, geometry, dynamicTransforms)
            if (!geometry.persistent()) {
                geometry.close()
            }
        }

        if (hasProjectionModifier) {
            RenderSystem.restoreProjectionMatrix()
        }
    }

    fun drawGui(geometry: Geometry) {
        val window = Minecraft.getInstance().window
        this.setProjectionMatrix(
            Matrix4f().setOrtho(
                0.0F,
                window.width.toFloat() / window.guiScale.toFloat(),
                window.height.toFloat() / window.guiScale.toFloat(),
                0.0F,
                1000.0F,
                11000.0F,
                RenderSystem.getDevice().deviceInfo.isZZeroToOne
            )
        )
        this.setUniform(
            DynamicTransforms.KEY,
            DynamicTransforms.builder()
                .withModelViewMatrix(Matrix4f().setTranslation(0.0F, 0.0F, -11000.0F))
                .build()
        )
        this.draw(geometry)
    }

    override fun close() {
        super.close()
        if (this.projectionMatrixBuffer != null) {
            this.projectionMatrixBuffer?.close()
            this.projectionMatrixBuffer = null
        }
    }
}