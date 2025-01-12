package btw.mixces.animatium.config

import btw.mixces.animatium.AnimatiumClient
import btw.mixces.animatium.util.CameraVersion
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.ColorControllerBuilder
import dev.isxander.yacl3.api.controller.EnumControllerBuilder
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import dev.isxander.yacl3.platform.YACLPlatform
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.awt.Color

class AnimatiumConfig {
    companion object {
        private val CONFIG = ConfigClassHandler.createBuilder(AnimatiumConfig::class.java)
            .serializer { config ->
                GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("animatium.json"))
                    .build()
            }.build()

        @JvmStatic
        fun getConfigScreen(parent: Screen?): Screen {
            return (YetAnotherConfigLib.create(CONFIG) { defaults: AnimatiumConfig, config: AnimatiumConfig, builder: YetAnotherConfigLib.Builder ->
                builder.title(Component.translatable("animatium.title"))

                run {
                    // Quality of Life Category
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
                        Option.createBuilder<Color>()
                            .name(Component.translatable("animatium.customHitColor"))
                            .description(OptionDescription.of(Component.translatable("animatium.customHitColor.description")))
                            .binding(
                                defaults.customHitColor,
                                { config.customHitColor },
                                { newVal ->
                                    config.customHitColor = newVal
                                    AnimatiumClient.reloadOverlayTexture()
                                })
                            .controller(ColorControllerBuilder::create)
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
                        Option.createBuilder<Int>()
                            .name(Component.translatable("animatium.particleMultiplier"))
                            .description(OptionDescription.of(Component.translatable("animatium.particleMultiplier.description")))
                            .binding(
                                defaults.particleMultiplier,
                                { config.particleMultiplier },
                                { newVal -> config.particleMultiplier = newVal })
                            .controller { opt -> IntegerSliderControllerBuilder.create(opt).range(0, 20).step(1) }
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
                                .name(Component.translatable("animatium.disableServerPoseAndBlockingVisualUpdates"))
                                .description(OptionDescription.of(Component.translatable("animatium.disableServerPoseAndBlockingVisualUpdates.description")))
                                .binding(
                                    defaults.disableServerPoseAndBlockingVisualUpdates,
                                    { config.disableServerPoseAndBlockingVisualUpdates },
                                    { newVal -> config.disableServerPoseAndBlockingVisualUpdates = newVal })
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
                        category.group(qolFixesGroup.build())
                    }

                    builder.category(category.build())
                }

                run {
                    // Movement Category
                    val category = ConfigCategory.createBuilder()
                    category.name(Component.translatable("animatium.category.movement"))

                    // Sneaking
                    run {
                        val sneakingGroup = OptionGroup.createBuilder()
                        sneakingGroup.name(Component.translatable("animatium.category.movement.group.sneaking"))
                        sneakingGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.removeSmoothSneaking"))
                                .description(OptionDescription.of(Component.translatable("animatium.removeSmoothSneaking.description")))
                                .binding(
                                    defaults.removeSmoothSneaking,
                                    { config.removeSmoothSneaking },
                                    { newVal -> config.removeSmoothSneaking = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        sneakingGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldSneakAnimationInterpolation"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldSneakAnimationInterpolation.description")))
                                .binding(
                                    defaults.oldSneakAnimationInterpolation,
                                    { config.oldSneakAnimationInterpolation },
                                    { newVal -> config.oldSneakAnimationInterpolation = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        sneakingGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fakeOldSneakEyeHeight"))
                                .description(OptionDescription.of(Component.translatable("animatium.fakeOldSneakEyeHeight.description")))
                                .binding(
                                    defaults.fakeOldSneakEyeHeight,
                                    { config.fakeOldSneakEyeHeight },
                                    { newVal -> config.fakeOldSneakEyeHeight = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        sneakingGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fixSneakingFeetPosition"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixSneakingFeetPosition.description")))
                                .binding(
                                    defaults.fixSneakingFeetPosition,
                                    { config.fixSneakingFeetPosition },
                                    { newVal -> config.fixSneakingFeetPosition = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        sneakingGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldSneakingFeetPosition"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldSneakingFeetPosition.description")))
                                .binding(
                                    defaults.oldSneakingFeetPosition,
                                    { config.oldSneakingFeetPosition },
                                    { newVal -> config.oldSneakingFeetPosition = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        sneakingGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.syncPlayerModelWithEyeHeight"))
                                .description(OptionDescription.of(Component.translatable("animatium.syncPlayerModelWithEyeHeight.description")))
                                .binding(
                                    defaults.syncPlayerModelWithEyeHeight,
                                    { config.syncPlayerModelWithEyeHeight },
                                    { newVal -> config.syncPlayerModelWithEyeHeight = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        sneakingGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.sneakAnimationWhileFlying"))
                                .description(OptionDescription.of(Component.translatable("animatium.sneakAnimationWhileFlying.description")))
                                .binding(
                                    defaults.sneakAnimationWhileFlying,
                                    { config.sneakAnimationWhileFlying },
                                    { newVal -> config.sneakAnimationWhileFlying = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        category.group(sneakingGroup.build())
                    }


                    // Other
                    run {
                        val otherGroup = OptionGroup.createBuilder()
                        otherGroup.name(Component.translatable("animatium.category.movement.group.other"))
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.rotateBackwardsWalking"))
                                .description(OptionDescription.of(Component.translatable("animatium.rotateBackwardsWalking.description")))
                                .binding(
                                    defaults.rotateBackwardsWalking,
                                    { config.rotateBackwardsWalking },
                                    { newVal -> config.rotateBackwardsWalking = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.uncapBlockingHeadRotation"))
                                .description(OptionDescription.of(Component.translatable("animatium.uncapBlockingHeadRotation.description")))
                                .binding(
                                    defaults.uncapBlockingHeadRotation,
                                    { config.uncapBlockingHeadRotation },
                                    { newVal -> config.uncapBlockingHeadRotation = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.removeHeadRotationInterpolation"))
                                .description(OptionDescription.of(Component.translatable("animatium.removeHeadRotationInterpolation.description")))
                                .binding(
                                    defaults.removeHeadRotationInterpolation,
                                    { config.removeHeadRotationInterpolation },
                                    { newVal -> config.removeHeadRotationInterpolation = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fixVerticalBobbingTilt"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixVerticalBobbingTilt.description")))
                                .binding(
                                    defaults.fixVerticalBobbingTilt,
                                    { config.fixVerticalBobbingTilt },
                                    { newVal -> config.fixVerticalBobbingTilt = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldViewBobbing"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldViewBobbing.description")))
                                .binding(
                                    defaults.oldViewBobbing,
                                    { config.oldViewBobbing },
                                    { newVal -> config.oldViewBobbing = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldDeathLimbs"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldDeathLimbs.description")))
                                .binding(
                                    defaults.oldDeathLimbs,
                                    { config.oldDeathLimbs },
                                    { newVal -> config.oldDeathLimbs = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fixBowArmMovement"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixBowArmMovement.description")))
                                .binding(
                                    defaults.fixBowArmMovement,
                                    { config.fixBowArmMovement },
                                    { newVal -> config.fixBowArmMovement = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldDamageTilt"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldDamageTilt.description")))
                                .binding(
                                    defaults.oldDamageTilt,
                                    { config.oldDamageTilt },
                                    { newVal -> config.oldDamageTilt = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        category.group(otherGroup.build())
                    }

                    builder.category(category.build())
                }

                run {
                    // Screen Category
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

                run {
                    // Items Category
                    val category = ConfigCategory.createBuilder()
                    category.name(Component.translatable("animatium.category.items"))

                    // Fishing Rod
                    run {
                        val fishingRodGroup = OptionGroup.createBuilder()
                        fishingRodGroup.name(Component.translatable("animatium.category.items.group.fishing_rod"))
                        fishingRodGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldFishingRodTextureStackCheck"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldFishingRodTextureStackCheck.description")))
                                .binding(
                                    defaults.oldFishingRodTextureStackCheck,
                                    { config.oldFishingRodTextureStackCheck },
                                    { newVal -> config.oldFishingRodTextureStackCheck = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        fishingRodGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fishingRodLineInterpolation"))
                                .description(OptionDescription.of(Component.translatable("animatium.fishingRodLineInterpolation.description")))
                                .binding(
                                    defaults.fishingRodLineInterpolation,
                                    { config.fishingRodLineInterpolation },
                                    { newVal -> config.fishingRodLineInterpolation = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        fishingRodGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.noMoveFishingRodLine"))
                                .description(OptionDescription.of(Component.translatable("animatium.noMoveFishingRodLine.description")))
                                .binding(
                                    defaults.noMoveFishingRodLine,
                                    { config.noMoveFishingRodLine },
                                    { newVal -> config.noMoveFishingRodLine = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        fishingRodGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldFishingRodLinePositionThirdPerson"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldFishingRodLinePositionThirdPerson.description")))
                                .binding(
                                    defaults.oldFishingRodLinePositionThirdPerson,
                                    { config.oldFishingRodLinePositionThirdPerson },
                                    { newVal -> config.oldFishingRodLinePositionThirdPerson = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        fishingRodGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldFishingRodLineThickness"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldFishingRodLineThickness.description")))
                                .binding(
                                    defaults.oldFishingRodLineThickness,
                                    { config.oldFishingRodLineThickness },
                                    { newVal -> config.oldFishingRodLineThickness = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        fishingRodGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.thinFishingRodLineThickness"))
                                .description(OptionDescription.of(Component.translatable("animatium.thinFishingRodLineThickness.description")))
                                .binding(
                                    defaults.thinFishingRodLineThickness,
                                    { config.thinFishingRodLineThickness },
                                    { newVal -> config.thinFishingRodLineThickness = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        fishingRodGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fixCastLineCheck"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixCastLineCheck.description")))
                                .binding(
                                    defaults.fixCastLineCheck,
                                    { config.fixCastLineCheck },
                                    { newVal -> config.fixCastLineCheck = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        fishingRodGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fixCastLineSwing"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixCastLineSwing.description")))
                                .binding(
                                    defaults.fixCastLineSwing,
                                    { config.fixCastLineSwing },
                                    { newVal -> config.fixCastLineSwing = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        category.group(fishingRodGroup.build())
                    }

                    // Fixes
                    run {
                        val itemFixesGroup = OptionGroup.createBuilder()
                        itemFixesGroup.name(Component.translatable("animatium.category.items.group.item_fixes"))
                        itemFixesGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fixItemUseTextureCheck"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixItemUseTextureCheck.description")))
                                .binding(
                                    defaults.fixItemUseTextureCheck,
                                    { config.fixItemUseTextureCheck },
                                    { newVal -> config.fixItemUseTextureCheck = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        itemFixesGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fixEquipAnimationItemCheck"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixEquipAnimationItemCheck.description")))
                                .binding(
                                    defaults.fixEquipAnimationItemCheck,
                                    { config.fixEquipAnimationItemCheck },
                                    { newVal -> config.fixEquipAnimationItemCheck = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        itemFixesGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.removeEquipAnimationOnItemUse"))
                                .description(OptionDescription.of(Component.translatable("animatium.removeEquipAnimationOnItemUse.description")))
                                .binding(
                                    defaults.removeEquipAnimationOnItemUse,
                                    { config.removeEquipAnimationOnItemUse },
                                    { newVal -> config.removeEquipAnimationOnItemUse = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        itemFixesGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.removeClientsideBlockingDelay"))
                                .description(OptionDescription.of(Component.translatable("animatium.removeClientsideBlockingDelay.description")))
                                .binding(
                                    defaults.removeClientsideBlockingDelay,
                                    { config.removeClientsideBlockingDelay },
                                    { newVal -> config.removeClientsideBlockingDelay = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        itemFixesGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fixItemUsageCheck"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixItemUsageCheck.description")))
                                .binding(
                                    defaults.fixItemUsageCheck,
                                    { config.fixItemUsageCheck },
                                    { newVal -> config.fixItemUsageCheck = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        itemFixesGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.fixFireballClientsideVisual"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixFireballClientsideVisual.description")))
                                .binding(
                                    defaults.fixFireballClientsideVisual,
                                    { config.fixFireballClientsideVisual },
                                    { newVal -> config.fixFireballClientsideVisual = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        category.group(itemFixesGroup.build())
                    }

                    // Glint
                    run {
                        val glintGroup = OptionGroup.createBuilder()
                        glintGroup.name(Component.translatable("animatium.category.items.group.glint"))
                        glintGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldGlintSpeed"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldGlintSpeed.description")))
                                .binding(
                                    defaults.oldGlintSpeed,
                                    { config.oldGlintSpeed },
                                    { newVal -> config.oldGlintSpeed = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        glintGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.disableGlintOnItemDrops2D"))
                                .description(OptionDescription.of(Component.translatable("animatium.disableGlintOnItemDrops2D.description")))
                                .binding(
                                    defaults.disableGlintOnItemDrops2D,
                                    { config.disableGlintOnItemDrops2D },
                                    { newVal -> config.disableGlintOnItemDrops2D = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        glintGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.disableGlintOnItemFramed2D"))
                                .description(OptionDescription.of(Component.translatable("animatium.disableGlintOnItemFramed2D.description")))
                                .binding(
                                    defaults.disableGlintOnItemFramed2D,
                                    { config.disableGlintOnItemFramed2D },
                                    { newVal -> config.disableGlintOnItemFramed2D = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        category.group(glintGroup.build())
                    }

                    // Other
                    run {
                        val otherGroup = OptionGroup.createBuilder()
                        otherGroup.name(Component.translatable("animatium.category.items.group.other"))
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.tiltItemPositions"))
                                .description(OptionDescription.of(Component.translatable("animatium.tiltItemPositions.description")))
                                .binding(
                                    defaults.tiltItemPositions,
                                    { config.tiltItemPositions },
                                    { newVal -> config.tiltItemPositions = newVal } )
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.tiltItemPositionsInThirdperson"))
                                .description(OptionDescription.of(Component.translatable("animatium.tiltItemPositionsInThirdperson.description")))
                                .binding(
                                    defaults.tiltItemPositionsInThirdperson,
                                    { config.tiltItemPositionsInThirdperson },
                                    { newVal -> config.tiltItemPositionsInThirdperson = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldSkullPosition"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldSkullPosition.description")))
                                .binding(
                                    defaults.oldSkullPosition,
                                    { config.oldSkullPosition },
                                    { newVal -> config.oldSkullPosition = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.applyItemSwingUsage"))
                                .description(OptionDescription.of(Component.translatable("animatium.applyItemSwingUsage.description")))
                                .binding(
                                    defaults.applyItemSwingUsage,
                                    { config.applyItemSwingUsage },
                                    { newVal -> config.applyItemSwingUsage = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.disableSwingOnUse"))
                                .description(OptionDescription.of(Component.translatable("animatium.disableSwingOnUse.description")))
                                .binding(
                                    defaults.disableSwingOnUse,
                                    { config.disableSwingOnUse },
                                    { newVal -> config.disableSwingOnUse = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.disableSwingOnDrop"))
                                .description(OptionDescription.of(Component.translatable("animatium.disableSwingOnDrop.description")))
                                .binding(
                                    defaults.disableSwingOnDrop,
                                    { config.disableSwingOnDrop },
                                    { newVal -> config.disableSwingOnDrop = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.disableSwingOnEntityInteract"))
                                .description(OptionDescription.of(Component.translatable("animatium.disableSwingOnEntityInteract.description")))
                                .binding(
                                    defaults.disableSwingOnEntityInteract,
                                    { config.disableSwingOnEntityInteract },
                                    { newVal -> config.disableSwingOnEntityInteract = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.disableItemUsingTextureInGui"))
                                .description(OptionDescription.of(Component.translatable("animatium.disableItemUsingTextureInGui.description")))
                                .binding(
                                    defaults.disableItemUsingTextureInGui,
                                    { config.disableItemUsingTextureInGui },
                                    { newVal -> config.disableItemUsingTextureInGui = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.itemDropsFaceCamera"))
                                .description(OptionDescription.of(Component.translatable("animatium.itemDropsFaceCamera.description")))
                                .binding(
                                    defaults.itemDropsFaceCamera,
                                    { config.itemDropsFaceCamera },
                                    { newVal -> config.itemDropsFaceCamera = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.itemDrops2D"))
                                .description(OptionDescription.of(Component.translatable("animatium.itemDrops2D.description")))
                                .binding(
                                    defaults.itemDrops2D,
                                    { config.itemDrops2D },
                                    { newVal -> config.itemDrops2D = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.itemFramed2D"))
                                .description(OptionDescription.of(Component.translatable("animatium.itemFramed2D.description")))
                                .binding(
                                    defaults.itemFramed2D,
                                    { config.itemFramed2D },
                                    { newVal -> config.itemFramed2D = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.item2DColors"))
                                .description(OptionDescription.of(Component.translatable("animatium.item2DColors.description")))
                                .binding(
                                    defaults.item2DColors,
                                    { config.item2DColors },
                                    { newVal -> config.item2DColors = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldDurabilityBarColors"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldDurabilityBarColors.description")))
                                .binding(
                                    defaults.oldDurabilityBarColors,
                                    { config.oldDurabilityBarColors },
                                    { newVal -> config.oldDurabilityBarColors = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldItemRarities"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldItemRarities.description")))
                                .binding(
                                    defaults.oldItemRarities,
                                    { config.oldItemRarities },
                                    { newVal -> config.oldItemRarities = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        otherGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.showHeldItemInBoat"))
                                .description(OptionDescription.of(Component.translatable("animatium.showHeldItemInBoat.description")))
                                .binding(
                                    defaults.showHeldItemInBoat,
                                    { config.showHeldItemInBoat },
                                    { newVal -> config.showHeldItemInBoat = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        category.group(otherGroup.build())
                    }
                    builder.category(category.build())
                }

                run {
                    // Other Category
                    val category = ConfigCategory.createBuilder()
                    category.name(Component.translatable("animatium.category.other"))

                    // Sky
                    run {
                        val skyGroup = OptionGroup.createBuilder()
                        skyGroup.name(Component.translatable("animatium.category.other.group.sky"))
                        skyGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldBlueVoidSky"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldBlueVoidSky.description")))
                                .binding(
                                    defaults.oldBlueVoidSky,
                                    { config.oldBlueVoidSky },
                                    { newVal -> config.oldBlueVoidSky = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        skyGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldSkyHorizonHeight"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldSkyHorizonHeight.description")))
                                .binding(
                                    defaults.oldSkyHorizonHeight,
                                    { config.oldSkyHorizonHeight },
                                    { newVal -> config.oldSkyHorizonHeight = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        skyGroup.option(
                            Option.createBuilder<Boolean>()
                                .name(Component.translatable("animatium.oldCloudHeight"))
                                .description(OptionDescription.of(Component.translatable("animatium.oldCloudHeight.description")))
                                .binding(
                                    defaults.oldCloudHeight,
                                    { config.oldCloudHeight },
                                    { newVal -> config.oldCloudHeight = newVal })
                                .controller(TickBoxControllerBuilder::create)
                                .build()
                        )
                        category.group(skyGroup.build())
                    }

                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.legacyThirdpersonSwordBlockingPosition"))
                            .description(OptionDescription.of(Component.translatable("animatium.legacyThirdpersonSwordBlockingPosition.description")))
                            .binding(
                                defaults.legacyThirdpersonSwordBlockingPosition,
                                { config.legacyThirdpersonSwordBlockingPosition },
                                { newVal -> config.legacyThirdpersonSwordBlockingPosition = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.lockBlockingArmRotation"))
                            .description(OptionDescription.of(Component.translatable("animatium.lockBlockingArmRotation.description")))
                            .binding(
                                defaults.lockBlockingArmRotation,
                                { config.lockBlockingArmRotation },
                                { newVal -> config.lockBlockingArmRotation = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.disableProjectileAgeCheck"))
                            .description(OptionDescription.of(Component.translatable("animatium.disableProjectileAgeCheck.description")))
                            .binding(
                                defaults.disableProjectileAgeCheck,
                                { config.disableProjectileAgeCheck },
                                { newVal -> config.disableProjectileAgeCheck = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.oldBlockMiningProgress"))
                            .description(OptionDescription.of(Component.translatable("animatium.oldBlockMiningProgress.description")))
                            .binding(
                                defaults.oldBlockMiningProgress,
                                { config.oldBlockMiningProgress },
                                { newVal -> config.oldBlockMiningProgress = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.disableInventoryEntityScissor"))
                            .description(OptionDescription.of(Component.translatable("animatium.disableInventoryEntityScissor.description")))
                            .binding(
                                defaults.disableInventoryEntityScissor,
                                { config.disableInventoryEntityScissor },
                                { newVal -> config.disableInventoryEntityScissor = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.legacyBlockOutlineRendering"))
                            .description(OptionDescription.of(Component.translatable("animatium.legacyBlockOutlineRendering.description")))
                            .binding(
                                defaults.legacyBlockOutlineRendering,
                                { config.legacyBlockOutlineRendering },
                                { newVal -> config.legacyBlockOutlineRendering = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
//                    category.option(
//                        Option.createBuilder<Boolean>()
//                            .name(Component.translatable("animatium.removeFOVBasedProjection"))
//                            .description(OptionDescription.of(Component.translatable("animatium.removeFOVBasedProjection.description")))
//                            .binding(
//                                defaults.removeFOVBasedProjection,
//                                { config.removeFOVBasedProjection },
//                                { newVal -> config.removeFOVBasedProjection = newVal })
//                            .controller(TickBoxControllerBuilder::create)
//                            .build()
//                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.hideModelWhilstSleeping"))
                            .description(OptionDescription.of(Component.translatable("animatium.hideModelWhilstSleeping.description")))
                            .binding(
                                defaults.hideModelWhilstSleeping,
                                { config.hideModelWhilstSleeping },
                                { newVal -> config.hideModelWhilstSleeping = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.entityArmorHurtTint"))
                            .description(OptionDescription.of(Component.translatable("animatium.entityArmorHurtTint.description")))
                            .binding(
                                defaults.entityArmorHurtTint,
                                { config.entityArmorHurtTint },
                                { newVal -> config.entityArmorHurtTint = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.forceItemGlintOnEntity"))
                            .description(OptionDescription.of(Component.translatable("animatium.forceItemGlintOnEntity.description")))
                            .binding(
                                defaults.forceItemGlintOnEntity,
                                { config.forceItemGlintOnEntity },
                                { newVal -> config.forceItemGlintOnEntity = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.forceMaxGlintProperties"))
                            .description(OptionDescription.of(Component.translatable("animatium.forceMaxGlintProperties.description")))
                            .binding(
                                defaults.forceMaxGlintProperties,
                                { config.forceMaxGlintProperties },
                                { newVal -> config.forceMaxGlintProperties = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    category.option(
                        Option.createBuilder<Boolean>()
                            .name(Component.translatable("animatium.oldArmorHurtRendering"))
                            .description(OptionDescription.of(Component.translatable("animatium.oldArmorHurtRendering.description")))
                            .binding(
                                defaults.oldArmorHurtRendering,
                                { config.oldArmorHurtRendering },
                                { newVal -> config.oldArmorHurtRendering = newVal })
                            .controller(TickBoxControllerBuilder::create)
                            .build()
                    )
                    builder.category(category.build())
                }

                builder
            } as YetAnotherConfigLib).generateScreen(parent)
        }

        @JvmStatic
        fun load() {
            CONFIG.load()
        }

        @JvmStatic
        fun instance(): AnimatiumConfig {
            return CONFIG.instance() as AnimatiumConfig
        }
    }

    // QOL
    @SerialEntry var minimalViewBobbing = false
    @SerialEntry var showNametagInThirdperson = false
    @SerialEntry var hideNameTagBackground = false
    @SerialEntry var applyTextShadowToNametag = false
    @SerialEntry var oldDebugHudTextColor = false
    @SerialEntry var persistentBlockOutline = false
    @SerialEntry var allowOffhandUsageSwinging = false
    @SerialEntry var alwaysShowSharpParticles = false
    @SerialEntry var disableRecipeAndTutorialToasts = false
    @SerialEntry var showArmWhileInvisible = false
    @SerialEntry var fakeMissPenaltySwing = false
    @SerialEntry var showUsageSwingingParticles = false
    @SerialEntry var disableEntityDeathTopple = false
    @SerialEntry var customHitColor = Color(255, 0, 0)
    @SerialEntry var deepRedHurtTint = false
    @SerialEntry var disableParticlePhysics = false
    @SerialEntry var particleMultiplier = 1
    @SerialEntry var hideFirstpersonParticles = true
    @SerialEntry var fixMirrorArmSwing = false
    @SerialEntry var disableServerPoseAndBlockingVisualUpdates = false
    @SerialEntry var upMinPixelTransparencyLimit = false
    @SerialEntry var dontClearChatOnDisconnect = false
    @SerialEntry var dontCloseChat = false

    // Movement
    @SerialEntry var rotateBackwardsWalking = true
    @SerialEntry var uncapBlockingHeadRotation = true
    @SerialEntry var removeHeadRotationInterpolation = true
    @SerialEntry var fixVerticalBobbingTilt = true
    @SerialEntry var oldViewBobbing = true
    @SerialEntry var oldDeathLimbs = true
    @SerialEntry var fixBowArmMovement = true
    @SerialEntry var oldDamageTilt = true
    @SerialEntry var removeSmoothSneaking = false
    @SerialEntry var oldSneakAnimationInterpolation = false
    @SerialEntry var fakeOldSneakEyeHeight = false
    @SerialEntry var fixSneakingFeetPosition = true
    @SerialEntry var oldSneakingFeetPosition = false
    @SerialEntry var syncPlayerModelWithEyeHeight = false
    @SerialEntry var sneakAnimationWhileFlying = true

    // Screen
    @SerialEntry var showCrosshairInThirdperson = true
    @SerialEntry var fixHighAttackSpeedIndicator = true
    @SerialEntry var removeHeartFlash = true
    @SerialEntry var fixTextStrikethroughStyle = true
    @SerialEntry var centerScrollableListWidgets = true
    @SerialEntry var oldListWidgetSelectedBorderColor = true
    @SerialEntry var oldButtonTextColors = true
    @SerialEntry var removeDebugHudBackground = true
    @SerialEntry var debugHudTextShadow = true
    @SerialEntry var oldChatPosition = false
    @SerialEntry var removeChatIndicators = false
    @SerialEntry var disableCameraTransparentPassthrough = true
    @SerialEntry var oldTooltipStyleRendering = true
    @SerialEntry var oldSlotHoverStyleRendering = true
    @SerialEntry var cameraVersion = CameraVersion.V1_8

    // Items
    @SerialEntry var tiltItemPositions = true
    @SerialEntry var tiltItemPositionsInThirdperson = true
    @SerialEntry var oldSkullPosition = true
    @SerialEntry var applyItemSwingUsage = true
    @SerialEntry var disableSwingOnUse = true
    @SerialEntry var disableSwingOnDrop = true
    @SerialEntry var disableSwingOnEntityInteract = true
    @SerialEntry var disableItemUsingTextureInGui = true
    @SerialEntry var itemDropsFaceCamera = true
    @SerialEntry var itemDrops2D = true
    @SerialEntry var itemFramed2D = true
    @SerialEntry var item2DColors = true
    @SerialEntry var oldDurabilityBarColors = true
    @SerialEntry var oldItemRarities = true
    @SerialEntry var showHeldItemInBoat = true
    @SerialEntry var oldGlintSpeed = true
    @SerialEntry var disableGlintOnItemDrops2D = false
    @SerialEntry var disableGlintOnItemFramed2D = false
    @SerialEntry var oldFishingRodTextureStackCheck = true
    @SerialEntry var fishingRodLineInterpolation = false
    @SerialEntry var noMoveFishingRodLine = false
    @SerialEntry var oldFishingRodLinePositionThirdPerson = true
    @SerialEntry var oldFishingRodLineThickness = false
    @SerialEntry var thinFishingRodLineThickness = false
    @SerialEntry var fixCastLineCheck = false
    @SerialEntry var fixCastLineSwing = false
    @SerialEntry var fixItemUseTextureCheck = true
    @SerialEntry var fixEquipAnimationItemCheck = true
    @SerialEntry var removeEquipAnimationOnItemUse = true
    @SerialEntry var removeClientsideBlockingDelay = true
    @SerialEntry var fixItemUsageCheck = true
    @SerialEntry var fixFireballClientsideVisual = true

    // Other
    @SerialEntry var oldBlueVoidSky = true
    @SerialEntry var oldSkyHorizonHeight = true
    @SerialEntry var oldCloudHeight = true
    @SerialEntry var legacyThirdpersonSwordBlockingPosition = true
    @SerialEntry var lockBlockingArmRotation = true
    @SerialEntry var disableProjectileAgeCheck = true
    @SerialEntry var oldBlockMiningProgress = true
    @SerialEntry var disableInventoryEntityScissor = true
    @SerialEntry var legacyBlockOutlineRendering = true
    @SerialEntry var hideModelWhilstSleeping = true
    @SerialEntry var entityArmorHurtTint = true
    @SerialEntry var forceItemGlintOnEntity = true
    @SerialEntry var forceMaxGlintProperties = true
    @SerialEntry var oldArmorHurtRendering = true
}