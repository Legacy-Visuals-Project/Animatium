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

package org.visuals.legacy.animatium.handler.config.bundle

import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.OptionAddable
import org.visuals.legacy.animatium.handler.config.category.Category

class GroupBundle(category: Category, name: String) : EntryBundle(category, name) {
    override fun install(builder: ConfigCategory.Builder, defaults: Category, config: Category) =
        this.install(builder as OptionAddable, defaults, config)

    fun install(builder: OptionAddable, defaults: Category, config: Category) {
        for (entry in this.entries) {
            builder.option(entry.create(defaults, config))
        }
    }

    override fun group(name: String) = throw UnsupportedOperationException("You cannot create child groups!")
}