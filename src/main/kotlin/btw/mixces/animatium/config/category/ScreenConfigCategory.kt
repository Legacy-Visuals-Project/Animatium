package btw.mixces.animatium.config.category

import btw.mixces.animatium.config.AnimatiumConfig
import btw.mixces.animatium.util.CameraVersion
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.EnumControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import net.minecraft.network.chat.Component

object ScreenConfigCategory {
    fun setup(builder: YetAnotherConfigLib.Builder, defaults: AnimatiumConfig, config: AnimatiumConfig) {
        val category = ConfigCategory.createBuilder()
        category.name(Component.translatable("animatium.category.screen"))
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.showCrosshairInThirdperson"))
                .description(OptionDescription.of(Component.translatable("animatium.showCrosshairInThirdperson.description")))
                .binding(
                    defaults.showCrosshairInThirdperson,
                    { config.showCrosshairInThirdperson },
                    { newVal -> config.showCrosshairInThirdperson = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.fixHighAttackSpeedIndicator"))
                .description(OptionDescription.of(Component.translatable("animatium.fixHighAttackSpeedIndicator.description")))
                .binding(
                    defaults.fixHighAttackSpeedIndicator,
                    { config.fixHighAttackSpeedIndicator },
                    { newVal -> config.fixHighAttackSpeedIndicator = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.removeHeartFlash"))
                .description(OptionDescription.of(Component.translatable("animatium.removeHeartFlash.description")))
                .binding(
                    defaults.removeHeartFlash,
                    { config.removeHeartFlash },
                    { newVal -> config.removeHeartFlash = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.fixTextStrikethroughStyle"))
                .description(OptionDescription.of(Component.translatable("animatium.fixTextStrikethroughStyle.description")))
                .binding(
                    defaults.fixTextStrikethroughStyle,
                    { config.fixTextStrikethroughStyle },
                    { newVal -> config.fixTextStrikethroughStyle = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.centerScrollableListWidgets"))
                .description(OptionDescription.of(Component.translatable("animatium.centerScrollableListWidgets.description")))
                .binding(
                    defaults.centerScrollableListWidgets,
                    { config.centerScrollableListWidgets },
                    { newVal -> config.centerScrollableListWidgets = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.oldListWidgetSelectedBorderColor"))
                .description(OptionDescription.of(Component.translatable("animatium.oldListWidgetSelectedBorderColor.description")))
                .binding(
                    defaults.oldListWidgetSelectedBorderColor,
                    { config.oldListWidgetSelectedBorderColor },
                    { newVal -> config.oldListWidgetSelectedBorderColor = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.oldButtonTextColors"))
                .description(OptionDescription.of(Component.translatable("animatium.oldButtonTextColors.description")))
                .binding(
                    defaults.oldButtonTextColors,
                    { config.oldButtonTextColors },
                    { newVal -> config.oldButtonTextColors = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.removeDebugHudBackground"))
                .description(OptionDescription.of(Component.translatable("animatium.removeDebugHudBackground.description")))
                .binding(
                    defaults.removeDebugHudBackground,
                    { config.removeDebugHudBackground },
                    { newVal -> config.removeDebugHudBackground = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.debugHudTextShadow"))
                .description(OptionDescription.of(Component.translatable("animatium.debugHudTextShadow.description")))
                .binding(
                    defaults.debugHudTextShadow,
                    { config.debugHudTextShadow },
                    { newVal -> config.debugHudTextShadow = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.oldChatPosition"))
                .description(OptionDescription.of(Component.translatable("animatium.oldChatPosition.description")))
                .binding(
                    defaults.oldChatPosition,
                    { config.oldChatPosition },
                    { newVal -> config.oldChatPosition = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.removeChatIndicators"))
                .description(OptionDescription.of(Component.translatable("animatium.removeChatIndicators.description")))
                .binding(
                    defaults.removeChatIndicators,
                    { config.removeChatIndicators },
                    { newVal -> config.removeChatIndicators = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.disableCameraTransparentPassthrough"))
                .description(OptionDescription.of(Component.translatable("animatium.disableCameraTransparentPassthrough.description")))
                .binding(
                    defaults.disableCameraTransparentPassthrough,
                    { config.disableCameraTransparentPassthrough },
                    { newVal -> config.disableCameraTransparentPassthrough = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.oldTooltipStyleRendering"))
                .description(OptionDescription.of(Component.translatable("animatium.oldTooltipStyleRendering.description")))
                .binding(
                    defaults.oldTooltipStyleRendering,
                    { config.oldTooltipStyleRendering },
                    { newVal -> config.oldTooltipStyleRendering = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.oldSlotHoverStyleRendering"))
                .description(OptionDescription.of(Component.translatable("animatium.oldSlotHoverStyleRendering.description")))
                .binding(
                    defaults.oldSlotHoverStyleRendering,
                    { config.oldSlotHoverStyleRendering },
                    { newVal -> config.oldSlotHoverStyleRendering = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(Option.createBuilder<CameraVersion>()
            .name(Component.translatable("animatium.cameraVersion"))
            .description(OptionDescription.of(Component.translatable("animatium.cameraVersion.description")))
            .binding(
                defaults.cameraVersion,
                { config.cameraVersion },
                { newVal -> config.cameraVersion = newVal })
            .controller { opt ->
                EnumControllerBuilder.create(opt).enumClass(CameraVersion::class.java)
                    .formatValue { it -> Component.translatable("animatium.enum.CameraVersion." + it.name) }
            }
            .build())
        builder.category(category.build())
    }
}