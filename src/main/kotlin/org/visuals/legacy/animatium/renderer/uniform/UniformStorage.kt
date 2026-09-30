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

import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.buffers.Std140SizeCalculator

interface UniformStorage : AutoCloseable {
    fun name(): String

    fun <T> set(key: UniformKey<T>, value: T): UniformStorage

    fun <T> get(key: UniformKey<T>): T?

    fun upload(): GpuBufferSlice

    override fun close()

    abstract class Builder {
        protected val keys = arrayListOf<UniformKey<*>>()
        protected val defaults = hashMapOf<UniformKey<*>, Any?>()
        protected val calculator = Std140SizeCalculator()

        fun <T> with(key: UniformKey<T>, defaultValue: T?): Builder {
            if (this.keys.contains(key)) {
                throw RuntimeException("Cannot add key '${key.name}' to uniform storage builder as it already contains it!")
            } else {
                this.keys.add(key)
                this.defaults[key] = defaultValue
                key.serializer.size(this.calculator)
                return this
            }
        }

        fun <T> with(key: UniformKey<T>) = with(key, null)

        abstract fun build(): UniformStorage
    }
}