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

package org.visuals.legacy.animatium.renderer.buffer

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.renderpearl.api.commands.RenderPass
import org.joml.Matrix3x2f
import org.visuals.legacy.animatium.renderer.vertex.VertexLayouts

interface Geometry : AutoCloseable {
    companion object {
        @JvmStatic
        fun texturedScreenQuad(pose: Matrix3x2f, width: Int, height: Int): IndexedGeometry =
            IndexedGeometry.compile(VertexLayouts.POSITION_TEX_QUAD, 4) { vertexConsumer ->
                vertexConsumer.apply {
                    addVertexWith2DPose(pose, width.toFloat(), height.toFloat()).setUv(0.0F, 1.0F)
                    addVertexWith2DPose(pose, width.toFloat(), 0.0F).setUv(1.0F, 1.0F)
                    addVertexWith2DPose(pose, 0.0F, 0.0F).setUv(1.0F, 0.0F)
                    addVertexWith2DPose(pose, 0.0F, height.toFloat()).setUv(0.0F, 0.0F)
                }
            }
    }

    fun bind(pass: RenderPass, autoStorageIndexBuffer: RenderSystem.AutoStorageIndexBuffer)

    fun draw(pass: RenderPass)

    fun persistent(): Boolean

    fun isClosed(): Boolean

    override fun close()
}