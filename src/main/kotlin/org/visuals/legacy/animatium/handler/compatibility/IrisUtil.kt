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

package org.visuals.legacy.animatium.handler.compatibility

import com.mojang.renderpearl.api.pipeline.RenderPipeline
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import java.lang.reflect.Method
import java.util.*

object IrisUtil {
    private val pipelineCache = Object2ObjectOpenHashMap<RenderPipeline, IrisPipeline>()
    private var IRIS_INSTANCE: Any? = null
    private var IRIS_ASSIGN_PIPELINE_METHOD: Method? = null

    init {
        try {
            // API
            val irisApiClass = Class.forName("net.irisshaders.iris.api.v0.IrisApi")
            IRIS_INSTANCE = irisApiClass.getMethod("getInstance").invoke(null)

            // Enums
            val irisProgramEnum = Class.forName("net.irisshaders.iris.api.v0.IrisProgram").asSubclass(Enum::class.java)
            Arrays.stream(IrisPipeline.VALUES).forEach { it.initialize(irisProgramEnum) }

            // Methods
            IRIS_ASSIGN_PIPELINE_METHOD = IRIS_INSTANCE!!::class.java.getMethod("assignPipeline", RenderPipeline::class.java, irisProgramEnum)
        } catch (_: Exception) {
        }
    }

    fun assignPipeline(pipeline: RenderPipeline, program: IrisPipeline) {
        try {
            if (pipelineCache.containsKey(pipeline) && pipelineCache[pipeline] == program) {
                return
            }

            pipelineCache[pipeline] = program
            IRIS_ASSIGN_PIPELINE_METHOD?.invoke(IRIS_INSTANCE, pipeline, program.internal())
        } catch (_: Exception) {
        }
    }
}