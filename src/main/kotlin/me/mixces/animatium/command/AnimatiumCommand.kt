package me.mixces.animatium.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text
import kotlin.random.Random

class AnimatiumCommand : Command<FabricClientCommandSource> {
    companion object {
        fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
            // TODO: Arguments
            dispatcher.register(ClientCommandManager.literal("animatium").executes(AnimatiumCommand()))
        }
    }

    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        context.source.sendFeedback(Text.literal("Hello there!").withColor(Random.nextInt(0xFFFFFF)))
        return Command.SINGLE_SUCCESS
    }
}