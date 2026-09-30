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

import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.rendertype.LayeringTransform
import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.rendertype.TextureTransform

object AnimatiumRenderTypes {
    // Glint
    @JvmField
    val ARMOR_GLINT = RenderType.create(
        "animatium_armor_glint",
        RenderSetup.builder(AnimatiumPipelines.ARMOR_GLINT)
            .withTexture("Sampler0", ItemRenderer.ENCHANTED_GLINT_ARMOR)
            .setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .useOverlay()
            .createRenderSetup()
    )
}