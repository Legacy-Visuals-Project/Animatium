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

package org.visuals.legacy.animatium.util

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.toasts.SystemToast
import net.minecraft.network.chat.Component

object ToastUtil {
    fun send(message: Component) {
        Minecraft.getInstance().gui.toastManager().addToast(
            SystemToast(
                SystemToast.SystemToastId.PERIODIC_NOTIFICATION,
                Component.literal("Animatium"),
                message
            )
        )
    }

    fun send(message: String) = this.send(Component.literal(message))
}