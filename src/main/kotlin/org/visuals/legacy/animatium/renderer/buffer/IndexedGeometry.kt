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

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.ByteBufferBuilder
import com.mojang.blaze3d.vertex.VertexConsumer
import org.visuals.legacy.animatium.renderer.vertex.VertexLayout
import java.util.function.Consumer

data class IndexedGeometry(
    val vertexLayout: VertexLayout,
    val vertexBuffer: GpuBuffer,
    val indexCount: Int,
    val persistent: Boolean
) : Geometry {
    companion object {
        private fun compile(
            vertexLayout: VertexLayout,
            vertexCount: Int,
            vertexConsumer: Consumer<VertexConsumer>,
            persistent: Boolean
        ) = ByteBufferBuilder.exactlySized(vertexLayout.vertexFormat.vertexSize * vertexCount)
            .use { byteBufferBuilder ->
                val builder = vertexLayout.buffer(byteBufferBuilder)
                vertexConsumer.accept(builder)

                builder.buildOrThrow().use { meshData ->
                    val device = RenderSystem.getDevice()
                    val vertexBuffer = device.createBuffer(
                        { "Vertex buffer for " + vertexLayout.vertexFormat },
                        GpuBuffer.USAGE_VERTEX,
                        meshData.vertexBuffer()
                    )

                    IndexedGeometry(vertexLayout, vertexBuffer, meshData.drawState().indexCount, persistent)
                }
            }

        fun compile(
            vertexLayout: VertexLayout,
            vertexCount: Int,
            vertexConsumer: Consumer<VertexConsumer>
        ) = compile(vertexLayout, vertexCount, vertexConsumer, false)

        fun compilePersistent(
            vertexLayout: VertexLayout,
            vertexCount: Int,
            vertexConsumer: Consumer<VertexConsumer>
        ) = compile(vertexLayout, vertexCount, vertexConsumer, true)
    }

    override fun bind(pass: RenderPass, autoStorageIndexBuffer: RenderSystem.AutoStorageIndexBuffer) {
        pass.setVertexBuffer(0, vertexBuffer)
        pass.setIndexBuffer(autoStorageIndexBuffer.getBuffer(indexCount), autoStorageIndexBuffer.type())
    }

    override fun draw(pass: RenderPass) = pass.drawIndexed(0, 0, indexCount, 1)

    override fun persistent() = persistent

    override fun isClosed() = vertexBuffer.isClosed

    override fun close() = vertexBuffer.close()
}