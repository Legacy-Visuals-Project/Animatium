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

import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.systems.RenderSystem

class BasicGeometry(val firstVertex: Int, val vertexCount: Int) : Geometry {
    override fun bind(
        pass: RenderPass,
        autoStorageIndexBuffer: RenderSystem.AutoStorageIndexBuffer
    ) {
    }

    override fun draw(pass: RenderPass) = pass.draw(vertexCount, 1, firstVertex, 0)

    override fun persistent() = true

    override fun isClosed() = false

    override fun close() {
    }
}