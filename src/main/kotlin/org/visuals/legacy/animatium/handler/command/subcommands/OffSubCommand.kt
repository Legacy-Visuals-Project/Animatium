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
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import org.visuals.legacy.animatium.Animatium

class OffSubCommand : Command<FabricClientCommandSource> {
    companion object {
        @JvmField
        val UNIT = OffSubCommand()
    }

    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        val source = context.getSource()
        if (!Animatium.isEnabled()) {
            source.sendFeedback(Component.literal("Mod is already disabled!").withStyle(ChatFormatting.YELLOW))
        } else {
            source.sendFeedback(Component.literal("Mod disabled.").withStyle(ChatFormatting.RED))
            Animatium.enabled = false
            Animatium.reload()
        }

        return Command.SINGLE_SUCCESS
    }
}