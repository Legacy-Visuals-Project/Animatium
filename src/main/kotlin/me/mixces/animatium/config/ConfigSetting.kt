package me.mixces.animatium.config

import net.minecraft.resources.ResourceLocation

// TODO: wtf am I doing, make this better
data class ConfigSetting<T>(val identifier: ResourceLocation, val default: T) {
    private var enabled = if (default is Boolean) default else true
    private var disabled = false // Allows servers/us to force disable a config setting
    var value: T = default

    fun toggle(value: Boolean) {
        enabled = value
    }

    fun enabled(): Boolean {
        return if (default !is Boolean) {
            true
        } else {
            enabled
        }
    }

    fun disabled(): Boolean {
        return disabled
    }

    fun setDisabled(disabled: Boolean) {
        this.disabled = disabled
    }
}