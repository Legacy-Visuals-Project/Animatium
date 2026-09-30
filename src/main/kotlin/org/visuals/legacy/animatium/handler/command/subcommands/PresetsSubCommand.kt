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
import org.visuals.legacy.animatium.handler.screen.PresetsScreen

class PresetsSubCommand : Command<FabricClientCommandSource> {
    companion object {
        @JvmField
        val UNIT = PresetsSubCommand()
    }

    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        val minecraft = context.getSource().client
        minecraft.schedule({ minecraft.setScreen(PresetsScreen(minecraft.screen)) })
        return Command.SINGLE_SUCCESS
    }
}