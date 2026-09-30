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

package org.visuals.legacy.animatium.handler.command.subcommands

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.network.chat.Component
import org.visuals.legacy.animatium.AnimatiumConstants

class DebugSubCommand : Command<FabricClientCommandSource> {
    companion object {
        @JvmField
        val UNIT = DebugSubCommand()
    }

    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        context.getSource().sendFeedback(Component.literal("Commit: " + AnimatiumConstants.DEVELOPMENT_VERSION))
        context.getSource().sendFeedback(Component.literal("Version: " + AnimatiumConstants.VERSION))
        context.getSource().sendFeedback(Component.literal("Packed Version: " + AnimatiumConstants.VERSION.packedValue))
        context.getSource().sendFeedback(Component.literal("Is Dev Build: " + AnimatiumConstants.IS_DEVELOPMENT))
        return Command.SINGLE_SUCCESS
    }
}