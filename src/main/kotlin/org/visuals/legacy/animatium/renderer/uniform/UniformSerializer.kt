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

package org.visuals.legacy.animatium.renderer.uniform

import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import org.joml.*

abstract class UniformSerializer<T> {
    companion object {
        val INTEGER = object : UniformSerializer<Int>() {
            override fun put(builder: Std140Builder, value: Int) {
                builder.putInt(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putInt()
            }
        }

        val FLOAT = object : UniformSerializer<Float>() {
            override fun put(builder: Std140Builder, value: Float) {
                builder.putFloat(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putFloat()
            }
        }

        val DOUBLE = FLOAT.map(Double::toFloat)

        val BOOLEAN = INTEGER.map<Boolean> { if (it) 1 else 0 }

        val MATRIX4F = object : UniformSerializer<Matrix4f>() {
            override fun put(builder: Std140Builder, value: Matrix4f) {
                builder.putMat4f(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putMat4f()
            }
        }

        val VECTOR2F = object : UniformSerializer<Vector2f>() {
            override fun put(builder: Std140Builder, value: Vector2f) {
                builder.putVec2(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putVec2()
            }
        }

        val VECTOR2I = object : UniformSerializer<Vector2i>() {
            override fun put(builder: Std140Builder, value: Vector2i) {
                builder.putIVec2(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putIVec2()
            }
        }

        val VECTOR3F = object : UniformSerializer<Vector3f>() {
            override fun put(builder: Std140Builder, value: Vector3f) {
                builder.putVec3(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putVec3()
            }
        }

        val VECTOR3I = object : UniformSerializer<Vector3i>() {
            override fun put(builder: Std140Builder, value: Vector3i) {
                builder.putIVec3(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putIVec3()
            }
        }

        val VECTOR4F = object : UniformSerializer<Vector4f>() {
            override fun put(builder: Std140Builder, value: Vector4f) {
                builder.putVec4(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putVec4()
            }
        }

        val VECTOR4I = object : UniformSerializer<Vector4i>() {
            override fun put(builder: Std140Builder, value: Vector4i) {
                builder.putIVec4(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putIVec4()
            }
        }
    }

    abstract fun put(builder: Std140Builder, value: T)

    abstract fun size(calculator: Std140SizeCalculator)

    fun <From> map(conversion: (value: From) -> T): UniformSerializer<From> {
        val outer = this
        return object : UniformSerializer<From>() {
            override fun put(builder: Std140Builder, value: From) {
                outer.put(builder, conversion(value))
            }

            override fun size(calculator: Std140SizeCalculator) {
                outer.size(calculator)
            }
        }
    }
}