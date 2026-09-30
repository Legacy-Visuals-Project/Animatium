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

package org.visuals.legacy.animatium.handler.config.bundle.entry

import dev.isxander.yacl3.api.Option
import org.visuals.legacy.animatium.handler.config.category.Category
import org.visuals.legacy.animatium.handler.config.category.option.FloatRangeOptions
import org.visuals.legacy.animatium.handler.config.category.option.OptionBuilder
import java.util.function.BiConsumer

data class FloatRangeEntry(
    val name: String,
    val listener: BiConsumer<Option<Float>, Float>?,
    val min: Float,
    val max: Float,
    val step: Float
) : OptionEntrySupplier<Float> {
    override fun create(defaults: Category, config: Category): Option<Float> {
        val option = OptionBuilder(this.name, FloatRangeOptions(this.min, this.max, this.step))
        this.listener?.let { option.instant().listener(it) }
        return option.build(defaults, config)
    }

    override fun name() = this.name
}