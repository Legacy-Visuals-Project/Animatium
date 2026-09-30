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

package org.visuals.legacy.animatium.handler.rendering.lighting.lightmap

import com.mojang.renderpearl.api.textures.GpuTextureView
import org.visuals.legacy.animatium.handler.rendering.pipeline.AnimatiumPipelines
import org.visuals.legacy.animatium.renderer.buffer.BasicGeometry
import org.visuals.legacy.animatium.renderer.impl.DeferredRenderer
import org.visuals.legacy.animatium.renderer.uniform.DynamicUniformStorage
import org.visuals.legacy.animatium.renderer.uniform.UniformKey
import org.visuals.legacy.animatium.util.profile

class LegacyLightmapRenderer : AutoCloseable {
    companion object {
        private val SkyDarken = UniformKey.Float("SkyDarken")
        private val SkyDarkness = UniformKey.Float("SkyDarkness")
        private val BlockLightRed = UniformKey.Float("BlockLightRed")
        private val NightVisionScale = UniformKey.Float("NightVisionScale")
        private val Gamma = UniformKey.Float("Gamma")
        private val UseBrightLightmap = UniformKey.Boolean("UseBrightLightmap")

        private val BASE_GEOMETRY = BasicGeometry(0, 3)
    }

    private val lightmapInfoUniform = DynamicUniformStorage.builder("Legacy Lightmap UBO")
        .with(SkyDarken)
        .with(SkyDarkness)
        .with(BlockLightRed)
        .with(NightVisionScale)
        .with(Gamma)
        .with(UseBrightLightmap)
        .build()

    fun render(state: LegacyLightmapState, textureView: GpuTextureView) {
        if (state.needsUpdate) {
            profile("lightmap") {
                DeferredRenderer.of("Legacy Lightmap Update", textureView, null).use { renderer ->
                    renderer.setPipeline(AnimatiumPipelines.LEGACY_LIGHTMAP)
                    renderer.setUniform(
                        "LightmapInfo",
                        this.lightmapInfoUniform
                            .set(SkyDarken, state.skyDarken)
                            .set(SkyDarkness, state.skyDarkness)
                            .set(BlockLightRed, state.blockLightRed)
                            .set(NightVisionScale, state.nightVisionScale)
                            .set(Gamma, state.gamma)
                            .set(UseBrightLightmap, state.useBrightLightmap)
                            .upload()
                    )
                    renderer.draw(BASE_GEOMETRY)
                }
            }
        }
    }

    override fun close() = this.lightmapInfoUniform.close()
}