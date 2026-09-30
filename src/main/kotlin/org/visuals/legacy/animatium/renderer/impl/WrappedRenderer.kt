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

import com.mojang.blaze3d.systems.RenderPass
import org.visuals.legacy.animatium.renderer.DynamicTransforms
import org.visuals.legacy.animatium.renderer.buffer.Geometry

class WrappedRenderer(private val pass: RenderPass) : AbstractRenderer() {
    companion object {
        @JvmStatic
        fun of(pass: RenderPass) = WrappedRenderer(pass)
    }

    override fun draw(geometry: Geometry) {
        val dynamicTransforms = this.uniforms.getOrDefault(DynamicTransforms.KEY, DynamicTransforms.current())
        this.render(this.pass, geometry, dynamicTransforms)
        if (!geometry.persistent()) {
            geometry.close()
        }
    }
}