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
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.ARGB
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.player.Player

class BirthdaySubCommand : Command<FabricClientCommandSource> {
    companion object {
        @JvmField
        val UNIT = BirthdaySubCommand()
    }

    private val random = RandomSource.createThreadLocalInstance()

    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        val entity = context.getSource().entity
        if (entity is Player) {
            val level = context.getSource().level
            val x = entity.blockX + this.random.nextDouble()
            val y = entity.blockY + this.random.nextDouble()
            val z = entity.blockZ + this.random.nextDouble()
            for (i in 0..<180) {
                val sound = if (this.random.nextIntBetweenInclusive(0, 6) > 3) {
                    SoundEvents.AMETHYST_BLOCK_CHIME
                } else {
                    SoundEvents.FIREWORK_ROCKET_BLAST
                }

                level.playLocalSound(x, y, z, sound, SoundSource.AMBIENT, 20.0F, 0.95F + this.random.nextFloat() * 0.15F, true)
            }

            context.getSource().sendFeedback(colorful("It's the creators birthday today!!! Wish them a happy birthday!"))
        }

        return Command.SINGLE_SUCCESS
    }

    private fun colorful(literal: String): Component {
        val parts = literal.split("")

        val component = Component.empty()
        for (part in parts) {
            component.append(Component.literal(part).withColor(ARGB.opaque((Math.random() * 16777215).toInt())))
        }

        return component
    }
}