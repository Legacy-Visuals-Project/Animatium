package btw.mixces.animatium.config.category

import btw.mixces.animatium.config.AnimatiumConfig
import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.OptionGroup
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder
import net.minecraft.network.chat.Component

object OtherConfigCategory {
    fun setup(builder: YetAnotherConfigLib.Builder, defaults: AnimatiumConfig, config: AnimatiumConfig) {
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
                    .name(Component.translatable("animatium.oldVoidSkyFogHeight"))
                    .description(OptionDescription.of(Component.translatable("animatium.oldVoidSkyFogHeight.description")))
                    .binding(
                        defaults.oldVoidSkyFogHeight,
                        { config.oldVoidSkyFogHeight },
                        { newVal -> config.oldVoidSkyFogHeight = newVal })
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
        category.option(
            Option.createBuilder<Boolean>()
                .name(Component.translatable("animatium.forceHighAttackSpeedVisual"))
                .description(OptionDescription.of(Component.translatable("animatium.forceHighAttackSpeedVisual.description")))
                .binding(
                    defaults.forceHighAttackSpeedVisual,
                    { config.forceHighAttackSpeedVisual },
                    { newVal -> config.forceHighAttackSpeedVisual = newVal })
                .controller(TickBoxControllerBuilder::create)
                .build()
        )
        builder.category(category.build())
    }
}