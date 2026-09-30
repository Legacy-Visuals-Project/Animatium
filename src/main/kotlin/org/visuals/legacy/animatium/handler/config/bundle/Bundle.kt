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
import dev.isxander.yacl3.api.Option
import org.visuals.legacy.animatium.handler.config.bundle.entry.*
import org.visuals.legacy.animatium.handler.config.category.Category
import java.awt.Color
import java.util.function.BiConsumer

abstract class Bundle {
    abstract fun install(builder: ConfigCategory.Builder, defaults: Category, config: Category)

    open fun booleanEntry(name: String, listener: BiConsumer<Option<Boolean>, Boolean>) =
        this.entry(BooleanEntry(name, listener))

    fun booleanEntry(name: String) = this.booleanEntry(name) { opt, value -> }

    open fun intRange(name: String, min: Int, max: Int, step: Int) =
        this.entry(IntRangeEntry(name, null, min, max, step))

    fun intRange(name: String, min: Int, max: Int) = this.intRange(name, min, max, 1)

    open fun floatRange(name: String, min: Float, max: Float, step: Float) =
        this.entry(FloatRangeEntry(name, null, min, max, step))

    fun floatRange(name: String, min: Float, max: Float) = this.floatRange(name, min, max, 0.1F)

    open fun floatEntry(name: String) =
        this.entry(FloatEntry(name, null))

    open fun <S : Enum<S>> enumEntry(name: String, enumClazz: Class<S>, listener: BiConsumer<Option<S>, S>) =
        this.entry(EnumEntry(name, listener, enumClazz))

    fun <S : Enum<S>> enumEntry(name: String, enumClazz: Class<S>) = this.enumEntry(name, enumClazz) { opt, value -> }

    open fun colorEntry(name: String, listener: BiConsumer<Option<Color>, Color>) =
        this.entry(ColorEntry(name, listener))

    fun colorEntry(name: String) = this.colorEntry(name) { opt, value -> }

    abstract fun <T> entry(entry: OptionEntrySupplier<T>): Bundle
}