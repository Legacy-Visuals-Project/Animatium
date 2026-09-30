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

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.systems.RenderSystem
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap

class DynamicUniformStorage : UniformStorage, AutoCloseable {
    val name: String

    private val keys: List<UniformKey<*>>
    private val values = Object2ObjectOpenHashMap<UniformKey<*>, Any?>()
    private val size: Int

    var isClosed = false
        private set

    private constructor(name: String, keys: List<UniformKey<*>>, defaults: Map<UniformKey<*>, Any?>, size: Int) {
        this.name = name
        this.keys = keys
        this.size = size
        for (key in this.keys) {
            this.values[key] = defaults[key]
        }
    }

    companion object {
        @JvmStatic
        fun builder(name: String) = Builder(name)
    }

    override fun name(): String = this.name

    override fun <T> set(key: UniformKey<T>, value: T) = if (this.isClosed) {
        throw RuntimeException("Cannot set value in Uniform Storage (${this.name}) as it has been closed!")
    } else if (!this.keys.contains(key)) {
        throw RuntimeException("Uniform storage does not contain key '${key.name}'!")
    } else {
        this.values[key] = value
        this
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: UniformKey<T>) = if (!this.keys.contains(key)) null else this.values[key] as T?

    @Suppress("UNCHECKED_CAST")
    override fun upload() = if (this.isClosed) {
        throw RuntimeException("Cannot upload Uniform Storage (${this.name}) as it has been closed!")
    } else {
        val device = RenderSystem.getDevice()
        val transientMemory = device.createCommandEncoder().transientMemory()
        val alignment = device.deviceInfo.limits.minUniformOffsetAlignment
        transientMemory.allocateGpuMapped(
            this.size.toLong(),
            alignment.toLong(),
            GpuBuffer.USAGE_UNIFORM
        ).use { view ->
            val builder = Std140Builder.intoBuffer(view.data)
            for (key in this.keys) {
                val value = this.values[key]
                    ?: throw RuntimeException("Failed to bind \"${key.name}\" in Uniform Storage (${this.name}) as value is not set!")
                (key.serializer as UniformSerializer<Any>).put(builder, value)
            }

            view.slice
        }
    }

    override fun close() {
        if (!this.isClosed) {
            this.isClosed = true
            this.values.clear()
        }
    }

    class Builder(private val name: String) : UniformStorage.Builder() {
        override fun build(): DynamicUniformStorage {
            val size = this.calculator.get()
            if (size == 0) {
                throw RuntimeException("Cannot build Uniform Storage (${this.name}) as it contains no uniforms!")
            } else {
                return DynamicUniformStorage(this.name, this.keys.toList(), HashMap(this.defaults), size)
            }
        }
    }
}