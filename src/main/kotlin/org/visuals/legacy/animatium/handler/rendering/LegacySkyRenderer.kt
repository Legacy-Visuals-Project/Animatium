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
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.renderpearl.api.commands.RenderPass
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.util.ARGB
import org.joml.Matrix4f
import org.joml.Vector4f
import org.visuals.legacy.animatium.config.AnimatiumConfig
import org.visuals.legacy.animatium.handler.compatibility.IrisPipeline
import org.visuals.legacy.animatium.handler.rendering.pipeline.AnimatiumPipelines
import org.visuals.legacy.animatium.renderer.DynamicTransforms
import org.visuals.legacy.animatium.renderer.buffer.Geometry
import org.visuals.legacy.animatium.renderer.buffer.IndexedGeometry
import org.visuals.legacy.animatium.renderer.impl.WrappedRenderer
import org.visuals.legacy.animatium.renderer.vertex.VertexLayouts
import org.visuals.legacy.animatium.util.getHorizonHeight
import org.visuals.legacy.animatium.util.profile

object LegacySkyRenderer {
    private val GET_VOID_BOX_GEOMETRY = { offset: Float ->
        IndexedGeometry.compile(VertexLayouts.POSITIONED_COLOR_QUAD, 20) { vertexConsumer ->
            val color = ARGB.opaque(0)
            vertexConsumer.apply {
                // Left
                addVertex(-1.0F, offset, 1.0F).setColor(color)
                addVertex(1.0F, offset, 1.0F).setColor(color)
                addVertex(1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(-1.0F, -1.0F, 1.0F).setColor(color)

                // Right
                addVertex(-1.0F, -1.0F, -1.0F).setColor(color)
                addVertex(1.0F, -1.0F, -1.0F).setColor(color)
                addVertex(1.0F, offset, -1.0F).setColor(color)
                addVertex(-1.0F, offset, -1.0F).setColor(color)

                // Back
                addVertex(1.0F, -1.0F, -1.0F).setColor(color)
                addVertex(1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(1.0F, offset, 1.0F).setColor(color)
                addVertex(1.0F, offset, -1.0F).setColor(color)

                // Front
                addVertex(-1.0F, offset, -1.0F).setColor(color)
                addVertex(-1.0F, offset, 1.0F).setColor(color)
                addVertex(-1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(-1.0F, -1.0F, -1.0F).setColor(color)

                // Bottom
                addVertex(-1.0F, -1.0F, -1.0F).setColor(color)
                addVertex(-1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(1.0F, -1.0F, -1.0F).setColor(color)
            }
        }
    }

    lateinit var TOP_GEOMETRY: IndexedGeometry

    lateinit var BOTTOM_GEOMETRY: IndexedGeometry

    @JvmStatic
    fun initialize() {
        TOP_GEOMETRY = IndexedGeometry.compilePersistent(VertexLayouts.POSITIONED_QUAD, 676) { vertexConsumer ->
            buildSkyHalf(
                vertexConsumer,
                16.0F,
                false
            )
        }

        BOTTOM_GEOMETRY = IndexedGeometry.compilePersistent(VertexLayouts.POSITIONED_QUAD, 676) { vertexConsumer ->
            buildSkyHalf(
                vertexConsumer,
                -16.0F,
                true
            )
        }
    }

    @JvmStatic
    fun renderVoidDisc(pass: RenderPass, useDepthAttachment: Boolean) {
        profile("void_disc") {
            pass.pushDebugGroup({ "Void disc" })
            renderSkyDisc(pass, Vector4f(0.0F, 0.0F, 0.0F, 1.0F), useDepthAttachment, BOTTOM_GEOMETRY) {
                it.translate(0.0F, 12.0F, 0.0F)
            }
            pass.popDebugGroup()
        }
    }

    @JvmStatic
    fun renderBlueVoid(pass: RenderPass, skyColor: Int, depth: Double, useDepthAttachment: Boolean) {
        profile("blue_void") {
            pass.pushDebugGroup({ "Blue Void Disc" })
            val color = Vector4f(
                ARGB.redFloat(skyColor) * 0.2F + 0.04F,
                ARGB.greenFloat(skyColor) * 0.2F + 0.04F,
                ARGB.blueFloat(skyColor) * 0.6F + 0.1F,
                1.0F
            )
            renderSkyDisc(pass, color, useDepthAttachment, BOTTOM_GEOMETRY) {
                it.translate(0.0F, if (AnimatiumConfig.instance().extras.dontMoveBlueVoid) 12.0F else -((depth - 16.0).toFloat()), 0.0F)
            }
            pass.popDebugGroup()
        }
    }

    @JvmStatic
    fun renderSkyDisc(pass: RenderPass, color: Vector4f, useDepthAttachment: Boolean, geometry: Geometry, matrixModifier: (matrix: Matrix4f) -> Matrix4f) {
        WrappedRenderer.of(pass).use { renderer ->
            val matrix = matrixModifier(RenderSystem.getModelViewMatrixCopy())

            val skySet = AnimatiumPipelines.getSkySet(AnimatiumConfig.instance().other.planarSkyFog)
            renderer.setPipeline(
                skySet.get(useDepthAttachment),
                IrisPipeline.SKY_BASIC
            )

            renderer.setUniform(
                DynamicTransforms.KEY,
                DynamicTransforms.builder()
                    .withModelViewMatrix(matrix)
                    .withShaderColor(color)
                    .build()
            )

            renderer.draw(geometry)
        }
    }

    @JvmStatic
    fun getHorizonEyeHeight(level: ClientLevel, tickDelta: Float) =
        Minecraft.getInstance().player!!.getEyePosition(tickDelta).y - level.getLevelData().getHorizonHeight(level)

    // TODO/NOTE: Figure out why its rendering differently than in 18w07a (last snapshot to have it)
    @JvmStatic
    fun renderVoidBox(pass: RenderPass, depth: Double, useDepthAttachment: Boolean) {
        profile("player_void_box") {
            WrappedRenderer.of(pass).use { renderer ->
                pass.pushDebugGroup({ "Player Void Box" })
                renderer.setPipeline(AnimatiumPipelines.VOID_BOX.get(useDepthAttachment))
                renderer.draw(GET_VOID_BOX_GEOMETRY(-((depth + 65.0).toFloat())))
                pass.popDebugGroup()
            }
        }
    }

    private fun buildSkyHalf(vertexConsumer: VertexConsumer, y: Float, bottom: Boolean) {
        val width = 64
        for (k in -384..384 step width) {
            for (l in -384..384 step width) {
                var g = k
                var h = k + width
                if (bottom) {
                    // Swap them
                    val b = g
                    g = h
                    h = b
                }

                vertexConsumer.addVertex(g.toFloat(), y, l.toFloat())
                vertexConsumer.addVertex(h.toFloat(), y, l.toFloat())
                vertexConsumer.addVertex(h.toFloat(), y, (l + width).toFloat())
                vertexConsumer.addVertex(g.toFloat(), y, (l + width).toFloat())
            }
        }
    }

    @JvmStatic
    fun close() {
        TOP_GEOMETRY.close()
        BOTTOM_GEOMETRY.close()
    }
}