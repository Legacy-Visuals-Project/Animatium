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

import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.controller.ControllerBuilder
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder

data class IntegerRangeOptions(override val min: Int, override val max: Int, override val step: Int = 1) : RangeOptions<Int>() {
    override fun createController(option: Option<Int>): ControllerBuilder<Int> = IntegerSliderControllerBuilder.create(option)
        .range(this.min, this.max)
        .step(this.step)
}