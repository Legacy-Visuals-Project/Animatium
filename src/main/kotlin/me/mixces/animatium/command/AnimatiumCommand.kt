package me.mixces.animatium.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import me.mixces.animatium.AnimatiumClient
import me.mixces.animatium.util.ColorUtils
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.network.chat.Component

class AnimatiumCommand : Command<FabricClientCommandSource> {
    companion object {
        fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
            val command = ClientCommandManager.literal("animatium").executes(AnimatiumCommand())

            run {
                command.then(
                    LiteralArgumentBuilder.literal<FabricClientCommandSource>("debug").executes { context ->
                        context.source.sendFeedback(
                            Component.literal("Disabled miss swing penalty? " + AnimatiumClient.disableSwingMissPenalty)
                                .withColor(ColorUtils.randomColor())
                        )
                        context.source.sendFeedback(
                            Component.literal("Enabled left-click item usage on ground? " + AnimatiumClient.leftClickItemUsage)
                                .withColor(ColorUtils.randomColor())
                        )
                        return@executes Command.SINGLE_SUCCESS
                    }
                )
            }

            dispatcher.register(command)
        }
    }

    override fun run(context: CommandContext<FabricClientCommandSource>): Int {
        context.source.sendFeedback(Component.literal("Hello there!").withColor(ColorUtils.randomColor()))
        return Command.SINGLE_SUCCESS
    }
}