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

package org.visuals.legacy.animatium.renderer

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.renderpearl.api.buffers.GpuBufferSlice
import net.minecraft.util.ARGB
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

object DynamicTransforms {
    const val KEY = "DynamicTransforms"

    @JvmStatic
    fun builder() = Builder()

    @JvmStatic
    fun current() = builder().build()

    class Builder {
        private var modelViewMatrix: Matrix4f? = null
        private var textureMatrix: Matrix4f? = null
        private var shaderColor = Vector4f(1.0F)
        private var modelOffset = Vector3f()

        fun withModelViewMatrix(matrix4f: Matrix4f): Builder {
            this.modelViewMatrix = matrix4f
            return this
        }

        fun withTextureMatrix(matrix4f: Matrix4f): Builder {
            this.textureMatrix = matrix4f
            return this
        }

        fun withShaderColor(vector4f: Vector4f): Builder {
            this.shaderColor = vector4f
            return this
        }

        fun withShaderColor(red: Float, green: Float, blue: Float, alpha: Float) =
            this.withShaderColor(Vector4f(red, green, blue, alpha))

        fun withShaderColor(red: Float, green: Float, blue: Float) =
            this.withShaderColor(red, green, blue, 1.0F)

        fun withShaderColor(color: Int) = this.withShaderColor(
            ARGB.redFloat(color),
            ARGB.greenFloat(color),
            ARGB.blueFloat(color),
            ARGB.alphaFloat(color)
        )

        fun withModelOffset(vector3f: Vector3f): Builder {
            this.modelOffset = vector3f
            return this
        }

        fun build() = RenderSystem.getDynamicUniforms().writeTransform(
            this.modelViewMatrix ?: RenderSystem.getModelViewMatrixCopy(),
            this.shaderColor,
            this.modelOffset,
            this.textureMatrix ?: Matrix4f()
        )
    }
}