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
import org.visuals.legacy.animatium.handler.config.category.option.IntegerRangeOptions
import org.visuals.legacy.animatium.handler.config.category.option.OptionBuilder
import java.util.function.BiConsumer

data class IntRangeEntry(
    val name: String,
    val listener: BiConsumer<Option<Int>, Int>?,
    val min: Int,
    val max: Int,
    val step: Int
) : OptionEntrySupplier<Int> {
    override fun create(defaults: Category, config: Category): Option<Int> {
        val option = OptionBuilder(this.name, IntegerRangeOptions(this.min, this.max, this.step))
        this.listener?.let { option.instant().listener(it) }
        return option.build(defaults, config)
    }

    override fun name() = this.name
}