package me.mixces.animatium.config

import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.ColorControllerBuilder
import dev.isxander.yacl3.api.controller.EnumControllerBuilder
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import dev.isxander.yacl3.platform.YACLPlatform
import me.mixces.animatium.AnimatiumClient
import me.mixces.animatium.util.CameraVersion
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.awt.Color

class AnimatiumConfig {
    companion object {
        private val CONFIG = ConfigClassHandler.createBuilder(AnimatiumConfig::class.java)
            .serializer { config ->
                GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("animatium.json"))
                    .build()
            }.build()

        private var YACL_CACHED_CONFIG: YetAnotherConfigLib? = null
        private var CONFIG_SETTINGS = HashMap<ResourceLocation, ConfigSetting<*>>()

        private fun location(path: String): ResourceLocation {
            return ResourceLocation.parse("animatium:$path")
        }

        @JvmStatic
        fun get(identifier: ResourceLocation): ConfigSetting<*>? {
            return CONFIG_SETTINGS[identifier]
        }

        private fun <T> register(setting: ConfigSetting<T>): T {
            // Wtf? Why do I have to do this for it to not crash
            if (CONFIG_SETTINGS == null) {
                CONFIG_SETTINGS = hashMapOf()
            }

            if (!CONFIG_SETTINGS.contains(setting.identifier)) {
                CONFIG_SETTINGS[setting.identifier] = setting
            }

            return setting.default
        }

        @JvmStatic
        fun getEntries(): MutableSet<MutableMap.MutableEntry<ResourceLocation, ConfigSetting<*>>> {
            return CONFIG_SETTINGS.entries
        }

        @JvmStatic
        fun getConfigScreen(parent: Screen?): Screen {
            return (if (YACL_CACHED_CONFIG != null) YACL_CACHED_CONFIG else
                (YetAnotherConfigLib.create(CONFIG) { defaults: AnimatiumConfig, config: AnimatiumConfig, builder: YetAnotherConfigLib.Builder ->
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
                                .name(Component.translatable("animatium.fixMirrorArmSwing"))
                                .description(OptionDescription.of(Component.translatable("animatium.fixMirrorArmSwing.description")))
                                .binding(
                                    defaults.fixMirrorArmSwing,
                                    { config.fixMirrorArmSwing },
                                    { newVal -> config.fixMirrorArmSwing = newVal })
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
                                .name(Component.translatable("animatium.disableServerPoseAndBlockingVisualUpdates"))
                                .description(OptionDescription.of(Component.translatable("animatium.disableServerPoseAndBlockingVisualUpdates.description")))
                                .binding(
                                    defaults.disableServerPoseAndBlockingVisualUpdates,
                                    { config.disableServerPoseAndBlockingVisualUpdates },
                                    { newVal -> config.disableServerPoseAndBlockingVisualUpdates = newVal })
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
                                .name(Component.translatable("animatium.disableCameraTransparentPassthrough"))
                                .description(OptionDescription.of(Component.translatable("animatium.disableCameraTransparentPassthrough.description")))
                                .binding(
                                    defaults.disableCameraTransparentPassthrough,
                                    { config.disableCameraTransparentPassthrough },
                                    { newVal -> config.disableCameraTransparentPassthrough = newVal })
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
                                        { newVal -> config.tiltItemPositions = newVal })
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
                                    .name(Component.translatable("animatium.removeEquipAnimationOnItemUse"))
                                    .description(OptionDescription.of(Component.translatable("animatium.removeEquipAnimationOnItemUse.description")))
                                    .binding(
                                        defaults.removeEquipAnimationOnItemUse,
                                        { config.removeEquipAnimationOnItemUse },
                                        { newVal -> config.removeEquipAnimationOnItemUse = newVal })
                                    .controller(TickBoxControllerBuilder::create)
                                    .build()
                            )
                            otherGroup.option(
                                Option.createBuilder<Boolean>()
                                    .name(Component.translatable("animatium.doNotSkipHandAnimationOnSwap"))
                                    .description(OptionDescription.of(Component.translatable("animatium.doNotSkipHandAnimationOnSwap.description")))
                                    .binding(
                                        defaults.doNotSkipHandAnimationOnSwap,
                                        { config.doNotSkipHandAnimationOnSwap },
                                        { newVal -> config.doNotSkipHandAnimationOnSwap = newVal })
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
                                    .name(Component.translatable("animatium.removeClientsideBlockingDelay"))
                                    .description(OptionDescription.of(Component.translatable("animatium.removeClientsideBlockingDelay.description")))
                                    .binding(
                                        defaults.removeClientsideBlockingDelay,
                                        { config.removeClientsideBlockingDelay },
                                        { newVal -> config.removeClientsideBlockingDelay = newVal })
                                    .controller(TickBoxControllerBuilder::create)
                                    .build()
                            )
                            otherGroup.option(
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
                        builder.category(category.build())
                    }

                    builder
                } as YetAnotherConfigLib))!!.generateScreen(parent)
        }

        @JvmStatic
        fun load() {
            CONFIG.load()
        }

        @JvmStatic
        fun getInstance(): AnimatiumConfig {
            return CONFIG.instance() as AnimatiumConfig
        }
    }

    // QOL
    @SerialEntry
    var minimalViewBobbing = register(ConfigSetting(location("minimal_view_bobbing"), false))

    @SerialEntry
    var showNametagInThirdperson = register(ConfigSetting(location("show_nametag_in_thirdperson"), false))

    @SerialEntry
    var hideNameTagBackground = register(ConfigSetting(location("hide_nametag_background"), false))

    @SerialEntry
    var applyTextShadowToNametag = register(ConfigSetting(location("apply_text_shadow_to_nametag"), false))

    @SerialEntry
    var oldDebugHudTextColor = register(ConfigSetting(location("old_debug_hud_text_color"), false))

    @SerialEntry
    var fixMirrorArmSwing = register(ConfigSetting(location("fix_mirror_arm_swing"), false))

    @SerialEntry
    var persistentBlockOutline = register(ConfigSetting(location("persistent_block_outline"), false))

    @SerialEntry
    var allowOffhandUsageSwinging = register(ConfigSetting(location("allow_offhand_usage_swinging"), false))

    @SerialEntry
    var alwaysShowSharpParticles = register(ConfigSetting(location("always_show_sharp_particles"), false))

    @SerialEntry
    var disableRecipeAndTutorialToasts = register(ConfigSetting(location("disable_recipe_and_tutorial_toasts"), false))

    @SerialEntry
    var disableServerPoseAndBlockingVisualUpdates =
        register(ConfigSetting(location("disable_server_pose_and_blocking_visual_updates"), false))

    @SerialEntry
    var showArmWhileInvisible = register(ConfigSetting(location("show_arm_while_invisible"), false))

    @SerialEntry
    var upMinPixelTransparencyLimit = register(ConfigSetting(location("up_min_pixel_transparency_limit"), false))

    @SerialEntry
    var fakeMissPenaltySwing = register(ConfigSetting(location("fake_miss_penalty_swing"), false))

    @SerialEntry
    var showUsageSwingingParticles = register(ConfigSetting(location("show_usage_swinging_particles"), false))

    @SerialEntry
    var disableEntityDeathTopple = register(ConfigSetting(location("disable_entity_death_topple"), false))

    @SerialEntry
    var customHitColor = register(ConfigSetting(location("custom_hit_color"), Color(255, 0, 0)))

    @SerialEntry
    var deepRedHurtTint = register(ConfigSetting(location("deep_red_hurt_tint"), false))

    // Movement
    @SerialEntry
    var rotateBackwardsWalking = register(ConfigSetting(location("rotate_backwards_walking"), true))

    @SerialEntry
    var uncapBlockingHeadRotation = register(ConfigSetting(location("uncap_blocking_head_rotation"), true))

    @SerialEntry
    var removeHeadRotationInterpolation = register(ConfigSetting(location("remove_head_rotation_interpolation"), true))

    @SerialEntry
    var fixVerticalBobbingTilt = register(ConfigSetting(location("fix_vertical_bobbing_tilt"), true))

    @SerialEntry
    var oldViewBobbing = register(ConfigSetting(location("old_view_bobbing"), true))

    @SerialEntry
    var oldDeathLimbs = register(ConfigSetting(location("old_death_limbs"), true))

    @SerialEntry
    var fixBowArmMovement = register(ConfigSetting(location("fix_bow_arm_movement"), true))

    @SerialEntry
    var oldDamageTilt = register(ConfigSetting(location("old_damage_tilt"), true))

    @SerialEntry
    var removeSmoothSneaking = register(ConfigSetting(location("remove_smooth_sneaking"), false))

    @SerialEntry
    var oldSneakAnimationInterpolation = register(ConfigSetting(location("old_sneak_animation_interpolation"), false))

    @SerialEntry
    var fakeOldSneakEyeHeight = register(ConfigSetting(location("fake_old_sneak_eye_height"), false))

    @SerialEntry
    var fixSneakingFeetPosition = register(ConfigSetting(location("fix_sneaking_feet_position"), true))

    @SerialEntry
    var oldSneakingFeetPosition = register(ConfigSetting(location("old_sneaking_feet_position"), false))

    @SerialEntry
    var syncPlayerModelWithEyeHeight = register(ConfigSetting(location("sync_player_model_with_eye_height"), false))

    @SerialEntry
    var sneakAnimationWhileFlying = register(ConfigSetting(location("sneak_animation_while_flying"), true))

    // Screen
    @SerialEntry
    var showCrosshairInThirdperson = register(ConfigSetting(location("show_crosshair_in_thirdperson"), true))

    @SerialEntry
    var fixHighAttackSpeedIndicator = register(ConfigSetting(location("fix_high_attack_speed_indicator"), true))

    @SerialEntry
    var removeHeartFlash = register(ConfigSetting(location("remove_heart_flash"), true))

    @SerialEntry
    var fixTextStrikethroughStyle = register(ConfigSetting(location("fix_text_strikethrough_style"), true))

    @SerialEntry
    var centerScrollableListWidgets = register(ConfigSetting(location("center_scrollable_list_widgets"), true))

    @SerialEntry
    var oldListWidgetSelectedBorderColor =
        register(ConfigSetting(location("old_list_widget_selected_border_color"), true))

    @SerialEntry
    var oldButtonTextColors = register(ConfigSetting(location("old_button_text_colors"), true))

    @SerialEntry
    var removeDebugHudBackground = register(ConfigSetting(location("remove_debug_hud_background"), true))

    @SerialEntry
    var debugHudTextShadow = register(ConfigSetting(location("debug_hud_text_shadow"), true))

    @SerialEntry
    var oldChatPosition = register(ConfigSetting(location("old_chat_position"), false))

    @SerialEntry
    var disableCameraTransparentPassthrough =
        register(ConfigSetting(location("disable_camera_transparent_passthrough"), true))

    @SerialEntry
    var cameraVersion = register(ConfigSetting(location("camera_version"), CameraVersion.V1_8))

    // Items
    @SerialEntry
    var tiltItemPositions = register(ConfigSetting(location("tilt_item_positions"), true))

    @SerialEntry
    var tiltItemPositionsInThirdperson = register(ConfigSetting(location("tilt_item_positions_in_thirdperson"), true))

    @SerialEntry
    var oldSkullPosition = register(ConfigSetting(location("old_skull_position"), true))

    @SerialEntry
    var applyItemSwingUsage = register(ConfigSetting(location("apply_item_swing_usage"), true))

    @SerialEntry
    var disableSwingOnUse = register(ConfigSetting(location("disable_swing_on_use"), true))

    @SerialEntry
    var disableSwingOnDrop = register(ConfigSetting(location("disable_swing_on_drop"), true))

    @SerialEntry
    var disableSwingOnEntityInteract = register(ConfigSetting(location("disable_swing_on_entity_interact"), true))

    @SerialEntry
    var removeEquipAnimationOnItemUse = register(ConfigSetting(location("remove_equip_animation_on_item_use"), true))

    @SerialEntry
    var doNotSkipHandAnimationOnSwap = register(ConfigSetting(location("do_not_skip_hand_animation_on_swap"), true))

    @SerialEntry
    var disableItemUsingTextureInGui = register(ConfigSetting(location("disable_item_using_texture_in_gui"), true))

    @SerialEntry
    var itemDropsFaceCamera = register(ConfigSetting(location("item_drops_face_camera"), true))

    @SerialEntry
    var itemDrops2D = register(ConfigSetting(location("item_drops_2d"), true))

    @SerialEntry
    var itemFramed2D = register(ConfigSetting(location("item_framed_2d"), true))

    @SerialEntry
    var item2DColors = register(ConfigSetting(location("item_2d_colors"), true))

    @SerialEntry
    var oldDurabilityBarColors = register(ConfigSetting(location("old_durability_bar_colors"), true))

    @SerialEntry
    var oldItemRarities = register(ConfigSetting(location("old_item_rarities"), true))

    @SerialEntry
    var removeClientsideBlockingDelay = register(ConfigSetting(location("remove_clientside_blocking_delay"), true))

    @SerialEntry
    var fixItemUsageCheck = register(ConfigSetting(location("fix_item_usage_check"), true))

    @SerialEntry
    var oldFishingRodTextureStackCheck = register(ConfigSetting(location("old_fishing_rod_texture_stack_check"), true))

    @SerialEntry
    var fishingRodLineInterpolation = register(ConfigSetting(location("fishing_rod_line_interpolation"), false))

    @SerialEntry
    var noMoveFishingRodLine = register(ConfigSetting(location("no_move_fishing_rod_line"), false))

    @SerialEntry
    var oldFishingRodLinePositionThirdPerson =
        register(ConfigSetting(location("old_fishing_rod_line_thirdperson"), true))

    @SerialEntry
    var oldFishingRodLineThickness = register(ConfigSetting(location("old_fishing_rod_line_thickness"), false))

    @SerialEntry
    var thinFishingRodLineThickness = register(ConfigSetting(location("thin_fishing_rod_line_thickness"), false))

    @SerialEntry
    var fixCastLineCheck = register(ConfigSetting(location("fix_cast_line_check"), false))

    @SerialEntry
    var fixCastLineSwing = register(ConfigSetting(location("fix_cast_line_swing"), false))

    // Other
    @SerialEntry
    var oldBlueVoidSky = register(ConfigSetting(location("old_blue_void_sky"), true))

    @SerialEntry
    var oldSkyHorizonHeight = register(ConfigSetting(location("old_sky_horizon_height"), true))

    @SerialEntry
    var oldCloudHeight = register(
        ConfigSetting(location("old_cloud_height"), true)
    )

    @SerialEntry
    var legacyThirdpersonSwordBlockingPosition =
        register(ConfigSetting(location("legacy_thirdperson_sword_blocking_position"), true))

    @SerialEntry
    var lockBlockingArmRotation = register(ConfigSetting(location("lock_blocking_arm_rotation"), true))

    @SerialEntry
    var disableProjectileAgeCheck = register(ConfigSetting(location("disable_projectile_age_check"), true))

    @SerialEntry
    var oldBlockMiningProgress = register(ConfigSetting(location("old_block_mining_progress"), true))

    @SerialEntry
    var disableInventoryEntityScissor = register(ConfigSetting(location("disable_inventory_entity_scissor"), true))

    @SerialEntry
    var legacyBlockOutlineRendering = register(ConfigSetting(location("legacy_block_outline_rendering"), true))

    @SerialEntry
    var hideModelWhilstSleeping = register(ConfigSetting(location("hide_model_whilst_sleeping"), true))

    @SerialEntry
    var entityArmorHurtTint = register(ConfigSetting(location("entity_armor_hurt_tint"), true))
}