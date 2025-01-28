package btw.mixces.animatium.config.category

import btw.mixces.animatium.config.AnimatiumConfig
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import net.minecraft.network.chat.Component

object MovementConfigCategory {
    fun setup(builder: YetAnotherConfigLib.Builder, defaults: AnimatiumConfig, config: AnimatiumConfig) {
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

        run {
            val capeGroup = OptionGroup.createBuilder()
            capeGroup.name(Component.translatable("animatium.category.movement.group.cape"))
            capeGroup.option(
                Option.createBuilder<Boolean>()
                    .name(Component.translatable("animatium.oldCapeMovement"))
                    .description(OptionDescription.of(Component.translatable("animatium.oldCapeMovement.description")))
                    .binding(
                        defaults.oldCapeMovement,
                        { config.oldCapeMovement },
                        { newVal -> config.oldCapeMovement = newVal })
                    .controller(TickBoxControllerBuilder::create)
                    .build()
            )
            capeGroup.option(
                Option.createBuilder<Boolean>()
                    .name(Component.translatable("animatium.dontClampCapeLean"))
                    .description(OptionDescription.of(Component.translatable("animatium.dontClampCapeLean.description")))
                    .binding(
                        defaults.dontClampCapeLean,
                        { config.dontClampCapeLean },
                        { newVal -> config.dontClampCapeLean = newVal })
                    .controller(TickBoxControllerBuilder::create)
                    .build()
            )
            capeGroup.option(
                Option.createBuilder<Boolean>()
                    .name(Component.translatable("animatium.capeSwingRotation"))
                    .description(OptionDescription.of(Component.translatable("animatium.capeSwingRotation.description")))
                    .binding(
                        defaults.capeSwingRotation,
                        { config.capeSwingRotation },
                        { newVal -> config.capeSwingRotation = newVal })
                    .controller(TickBoxControllerBuilder::create)
                    .build()
            )
            capeGroup.option(
                Option.createBuilder<Boolean>()
                    .name(Component.translatable("animatium.capeChestplateTranslation"))
                    .description(OptionDescription.of(Component.translatable("animatium.capeChestplateTranslation.description")))
                    .binding(
                        defaults.capeChestplateTranslation,
                        { config.capeChestplateTranslation },
                        { newVal -> config.capeChestplateTranslation = newVal })
                    .controller(TickBoxControllerBuilder::create)
                    .build()
            )
            capeGroup.option(
                Option.createBuilder<Boolean>()
                    .name(Component.translatable("animatium.oldCapePosition"))
                    .description(OptionDescription.of(Component.translatable("animatium.oldCapePosition.description")))
                    .binding(
                        defaults.oldCapePosition,
                        { config.oldCapePosition },
                        { newVal -> config.oldCapePosition = newVal })
                    .controller(TickBoxControllerBuilder::create)
                    .build()
            )
            category.group(capeGroup.build())
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
}