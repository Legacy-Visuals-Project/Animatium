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

package org.visuals.legacy.animatium.renderer.vertex

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexFormat.Mode

object VertexLayouts {
    @JvmField
    val POSITIONED_QUAD = quads(DefaultVertexFormat.POSITION)

    @JvmField
    val POSITIONED_COLOR_QUAD = quads(DefaultVertexFormat.POSITION_COLOR)

    @JvmField
    val POSITION_TEX_QUAD = quads(DefaultVertexFormat.POSITION_TEX)

    private fun quads(vertexFormat: VertexFormat) = VertexLayout(vertexFormat, Mode.QUADS)
}