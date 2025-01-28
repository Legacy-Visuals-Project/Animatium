package btw.mixces.animatium.config.category

import btw.mixces.animatium.AnimatiumClient
import btw.mixces.animatium.config.AnimatiumConfig
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component

object QOLConfigCategory {
    fun setup(builder: YetAnotherConfigLib.Builder, defaults: AnimatiumConfig, config: AnimatiumConfig) {
        val category = ConfigCategory.createBuilder()
        category.name(Component.translatable("animatium.category.qol"))
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.minimalViewBobbing"))
                .description(OptionDescription.of(Component.translatable("animatium.minimalViewBobbing.description")))
                .binding(
                    defaults.minimalViewBobbing,
                    { config.minimalViewBobbing },
                    { newVal -> config.minimalViewBobbing = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.showNametagInThirdperson"))
                .description(OptionDescription.of(Component.translatable("animatium.showNametagInThirdperson.description")))
                .binding(
                    defaults.showNametagInThirdperson,
                    { config.showNametagInThirdperson },
                    { newVal -> config.showNametagInThirdperson = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.hideNameTagBackground"))
                .description(OptionDescription.of(Component.translatable("animatium.hideNameTagBackground.description")))
                .binding(
                    defaults.hideNameTagBackground,
                    { config.hideNameTagBackground },
                    { newVal -> config.hideNameTagBackground = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.applyTextShadowToNametag"))
                .description(OptionDescription.of(Component.translatable("animatium.applyTextShadowToNametag.description")))
                .binding(
                    defaults.applyTextShadowToNametag,
                    { config.applyTextShadowToNametag },
                    { newVal -> config.applyTextShadowToNametag = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.oldDebugHudTextColor"))
                .description(OptionDescription.of(Component.translatable("animatium.oldDebugHudTextColor.description")))
                .binding(
                    defaults.oldDebugHudTextColor,
                    { config.oldDebugHudTextColor },
                    { newVal -> config.oldDebugHudTextColor = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.persistentBlockOutline"))
                .description(OptionDescription.of(Component.translatable("animatium.persistentBlockOutline.description")))
                .binding(
                    defaults.persistentBlockOutline,
                    { config.persistentBlockOutline },
                    { newVal -> config.persistentBlockOutline = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.allowOffhandUsageSwinging"))
                .description(OptionDescription.of(Component.translatable("animatium.allowOffhandUsageSwinging.description")))
                .binding(
                    defaults.allowOffhandUsageSwinging,
                    { config.allowOffhandUsageSwinging },
                    { newVal -> config.allowOffhandUsageSwinging = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.alwaysShowSharpParticles"))
                .description(OptionDescription.of(Component.translatable("animatium.alwaysShowSharpParticles.description")))
                .binding(
                    defaults.alwaysShowSharpParticles,
                    { config.alwaysShowSharpParticles },
                    { newVal -> config.alwaysShowSharpParticles = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.disableRecipeAndTutorialToasts"))
                .description(OptionDescription.of(Component.translatable("animatium.disableRecipeAndTutorialToasts.description")))
                .binding(
                    defaults.disableRecipeAndTutorialToasts,
                    { config.disableRecipeAndTutorialToasts },
                    { newVal -> config.disableRecipeAndTutorialToasts = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.showArmWhileInvisible"))
                .description(OptionDescription.of(Component.translatable("animatium.showArmWhileInvisible.description")))
                .binding(
                    defaults.showArmWhileInvisible,
                    { config.showArmWhileInvisible },
                    { newVal -> config.showArmWhileInvisible = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.fakeMissPenaltySwing"))
                .description(OptionDescription.of(Component.translatable("animatium.fakeMissPenaltySwing.description")))
                .binding(
                    defaults.fakeMissPenaltySwing,
                    { config.fakeMissPenaltySwing },
                    { newVal -> config.fakeMissPenaltySwing = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.showUsageSwingingParticles"))
                .description(OptionDescription.of(Component.translatable("animatium.showUsageSwingingParticles.description")))
                .binding(
                    defaults.showUsageSwingingParticles,
                    { config.showUsageSwingingParticles },
                    { newVal -> config.showUsageSwingingParticles = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.disableEntityDeathTopple"))
                .description(OptionDescription.of(Component.translatable("animatium.disableEntityDeathTopple.description")))
                .binding(
                    defaults.disableEntityDeathTopple,
                    { config.disableEntityDeathTopple },
                    { newVal -> config.disableEntityDeathTopple = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.deepRedHurtTint"))
                .description(OptionDescription.of(Component.translatable("animatium.deepRedHurtTint.description")))
                .binding(
                    defaults.deepRedHurtTint,
                    { config.deepRedHurtTint },
                    { newVal ->
                        config.deepRedHurtTint = newVal
                        AnimatiumClient.reloadOverlayTexture()
                    })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.disableParticlePhysics"))
                .description(OptionDescription.of(Component.translatable("animatium.disableParticlePhysics.description")))
                .binding(
                    defaults.disableParticlePhysics,
                    { config.disableParticlePhysics },
                    { newVal -> config.disableParticlePhysics = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.hideFirstpersonParticles"))
                .description(OptionDescription.of(Component.translatable("animatium.hideFirstpersonParticles.description")))
                .binding(
                    defaults.hideFirstpersonParticles,
                    { config.hideFirstpersonParticles },
                    { newVal -> config.hideFirstpersonParticles = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.dontClearChatOnDisconnect"))
                .description(OptionDescription.of(Component.translatable("animatium.dontClearChatOnDisconnect.description")))
                .binding(
                    defaults.dontClearChatOnDisconnect,
                    { config.dontClearChatOnDisconnect },
                    { newVal -> config.dontClearChatOnDisconnect = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.dontCloseChat"))
                .description(OptionDescription.of(Component.translatable("animatium.dontCloseChat.description")))
                .binding(
                    defaults.dontCloseChat,
                    { config.dontCloseChat },
                    { newVal -> config.dontCloseChat = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )

        // Fixes
        run {
            val qolFixesGroup = OptionGroup.createBuilder()
            qolFixesGroup.name(Component.translatable("animatium.category.qol.group.qol_fixes"))
            qolFixesGroup.option(
                Option.createBuilder<Boolean>()
                    .name(Component.translatable("animatium.fixMirrorArmSwing"))
                    .description(OptionDescription.of(Component.translatable("animatium.fixMirrorArmSwing.description")))
                    .binding(
                        defaults.fixMirrorArmSwing,
                        { config.fixMirrorArmSwing },
                        { newVal -> config.fixMirrorArmSwing = newVal })
                    .controller(TickBoxControllerBuilder::create)
                    .build()
            )
            qolFixesGroup.option(
                Option.createBuilder<Boolean>()
                    .name(Component.translatable("animatium.upMinPixelTransparencyLimit"))
                    .description(OptionDescription.of(Component.translatable("animatium.upMinPixelTransparencyLimit.description")))
                    .binding(
                        defaults.upMinPixelTransparencyLimit,
                        { config.upMinPixelTransparencyLimit },
                        { newVal ->
                            config.upMinPixelTransparencyLimit = newVal
                            Minecraft.getInstance().reloadResourcePacks()
                        })
                    .controller(TickBoxControllerBuilder::create)
                    .build()
            )
            qolFixesGroup.option(
                Option.createBuilder<Boolean>()
                    .name(Component.translatable("animatium.disableOffHandUsePoseNone"))
                    .description(OptionDescription.of(Component.translatable("animatium.disableOffHandUsePoseNone.description")))
                    .binding(
                        defaults.disableOffHandUsePoseNone,
                        { config.disableOffHandUsePoseNone },
                        { newVal -> config.disableOffHandUsePoseNone = newVal })
                    .controller(TickBoxControllerBuilder::create)
                    .build()
            )
            category.group(qolFixesGroup.build())
        }

        builder.category(category.build())
    }
}