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
import org.visuals.legacy.animatium.handler.config.category.option.EnumOptions
import org.visuals.legacy.animatium.handler.config.category.option.OptionBuilder
import java.util.function.BiConsumer

data class EnumEntry<S : Enum<S>>(
    val name: String,
    val listener: BiConsumer<Option<S>, S>?,
    val enumClass: Class<S>
) : OptionEntrySupplier<S> {
    override fun create(defaults: Category, config: Category): Option<S> {
        val option = OptionBuilder(this.name, EnumOptions(this.enumClass))
        this.listener?.let { option.instant().listener(it) }
        return option.build(defaults, config)
    }

    override fun name() = this.name
}