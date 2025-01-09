package btw.mixces.animatium.command

import btw.mixces.animatium.AnimatiumClient
import btw.mixces.animatium.util.ColorUtils
import btw.mixces.animatium.util.Feature
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
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
                            Component.literal(
                                "Disabled miss swing penalty? " + AnimatiumClient.enabledFeatures.contains(
                                    Feature.MISS_PENALTY
                                )
                            )
                                .withColor(ColorUtils.randomColor())
                        )
                        context.source.sendFeedback(
                            Component.literal(
                                "Enabled left-click item usage on ground? " + AnimatiumClient.enabledFeatures.contains(
                                    Feature.LEFT_CLICK_ITEM_USAGE
                                )
                            )
                                .withColor(ColorUtils.randomColor())
                        )
                        return@executes Command.SINGLE_SUCCESS
                    }
                )
            }

            run {
                command.then(
                    LiteralArgumentBuilder.literal<FabricClientCommandSource>("on").executes { context ->
                        context.source.sendFeedback(Component.literal("Mod enabled.").withColor(0x00FF00))
                        AnimatiumClient.enabled = true
                        return@executes Command.SINGLE_SUCCESS
                    }
                )
            }

            run {
                command.then(
                    LiteralArgumentBuilder.literal<FabricClientCommandSource>("off").executes { context ->
                        context.source.sendFeedback(Component.literal("Mod disabled.").withColor(0xFF0000))
                        AnimatiumClient.enabled = false
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