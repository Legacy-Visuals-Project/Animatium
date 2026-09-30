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

package org.visuals.legacy.animatium.handler.config.category.option

import org.visuals.legacy.animatium.handler.config.category.Category
import java.lang.reflect.Field

data class Reference<S>(val field: Field?, val defaultValue: S?) {
    companion object {
        fun <T : Category, S> get(name: String, defaults: T, config: T): Reference<S> {
            var field: Field? = null
            var defaultValue: S? = null
            try {
                val defaultField = defaults::class.java.getField(name)
                defaultValue = defaultField.get(defaults) as S?
            } catch (exception: ReflectiveOperationException) {
                exception.printStackTrace()
            }

            try {
                field = config::class.java.getField(name)
            } catch (exception: ReflectiveOperationException) {
                exception.printStackTrace()
            }

            return Reference(field, defaultValue)
        }
    }
}