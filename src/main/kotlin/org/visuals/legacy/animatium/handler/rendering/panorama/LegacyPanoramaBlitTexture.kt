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

package org.visuals.legacy.animatium.handler.rendering.panorama

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.textures.GpuTextureView
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.client.gui.render.TextureSetup
import net.minecraft.client.gui.render.state.GuiElementRenderState
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.util.ARGB
import org.joml.Matrix3x2f

class LegacyPanoramaBlitTexture(
    val pose: Matrix3x2f,
    val textureView: GpuTextureView,
    val width: Int,
    val height: Int
) : GuiElementRenderState {
    override fun buildVertices(vertexConsumer: VertexConsumer) {
        val color = ARGB.white(1.0F)
        val aspect = if (this.width > this.height) 120.0F / this.width else 120.0F / this.height
        val sw = this.width * aspect / 256.0F
        val sh = this.height * aspect / 256.0F
        vertexConsumer
            .addVertexWith2DPose(this.pose, 0.0F, this.height.toFloat())
            .setUv(0.5F - sh, 0.5F + sw)
            .setColor(color)
        vertexConsumer
            .addVertexWith2DPose(this.pose, this.width.toFloat(), this.height.toFloat())
            .setUv(0.5F - sh, 0.5F - sw)
            .setColor(color)
        vertexConsumer
            .addVertexWith2DPose(this.pose, this.width.toFloat(), 0.0F)
            .setUv(0.5F + sh, 0.5F - sw)
            .setColor(color)
        vertexConsumer
            .addVertexWith2DPose(this.pose, 0.0F, 0.0F)
            .setUv(0.5F + sh, 0.5F + sw)
            .setColor(color)
    }

    override fun pipeline() = RenderPipelines.GUI_TEXTURED

    override fun textureSetup() =
        TextureSetup.singleTexture(this.textureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))

    override fun scissorArea() = null

    override fun bounds() = ScreenRectangle(0, 0, this.width, this.height)
}