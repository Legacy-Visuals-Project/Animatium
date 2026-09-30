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

interface OptionEntrySupplier<T> {
    fun create(defaults: Category, config: Category): Option<T>

    fun name(): String

    fun value(): T? = throw UnsupportedOperationException("The supplier used has not been bootstrapped yet!")

    companion object {
        fun <T> bootstrap(clazz: Class<out Category>, category: Category, supplier: OptionEntrySupplier<T>): OptionEntrySupplier<T> {
            return object : OptionEntrySupplier<T> {
                override fun create(defaults: Category, config: Category) = supplier.create(defaults, config)

                override fun name() = supplier.name()

                override fun value(): T? {
                    return try {
                        clazz.getField(this.name()).get(category) as T
                    } catch (_: Exception) {
                        null
                    }
                }
            }
        }
    }
}