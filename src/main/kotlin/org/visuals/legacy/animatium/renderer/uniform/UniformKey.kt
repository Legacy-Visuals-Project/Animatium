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

class UniformKey<T> {
    companion object {
        @JvmStatic
        fun <T> of(name: String, serializer: UniformSerializer<T>) = UniformKey(name, serializer)

        @JvmStatic
        fun Integer(name: String) = of(name, UniformSerializer.INTEGER)

        @JvmStatic
        fun Float(name: String) = of(name, UniformSerializer.FLOAT)

        @JvmStatic
        fun Double(name: String) = of(name, UniformSerializer.DOUBLE)

        @JvmStatic
        fun Boolean(name: String) = of(name, UniformSerializer.BOOLEAN)

        @JvmStatic
        fun Matrix4f(name: String) = of(name, UniformSerializer.MATRIX4F)

        @JvmStatic
        fun Vector2f(name: String) = of(name, UniformSerializer.VECTOR2F)

        @JvmStatic
        fun Vector2i(name: String) = of(name, UniformSerializer.VECTOR2I)

        @JvmStatic
        fun Vector3f(name: String) = of(name, UniformSerializer.VECTOR3F)

        @JvmStatic
        fun Vector3i(name: String) = of(name, UniformSerializer.VECTOR3I)

        @JvmStatic
        fun Vector4f(name: String) = of(name, UniformSerializer.VECTOR4F)

        @JvmStatic
        fun Vector4i(name: String) = of(name, UniformSerializer.VECTOR4I)
    }

    val name: String
    val serializer: UniformSerializer<T>

    private constructor(name: String, serializer: UniformSerializer<T>) {
        this.name = name
        this.serializer = serializer
    }
}