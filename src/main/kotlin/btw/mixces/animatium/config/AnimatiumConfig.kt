package btw.mixces.animatium.config

import btw.mixces.animatium.config.category.ItemsConfigCategory
import btw.mixces.animatium.config.category.MovementConfigCategory
import btw.mixces.animatium.config.category.OtherConfigCategory
import btw.mixces.animatium.config.category.QOLConfigCategory
import btw.mixces.animatium.config.category.ScreenConfigCategory
import btw.mixces.animatium.util.CameraVersion
import dev.isxander.yacl3.api.YetAnotherConfigLib
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler
import dev.isxander.yacl3.config.v2.api.SerialEntry
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder
import dev.isxander.yacl3.platform.YACLPlatform
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
                QOLConfigCategory.setup(builder, defaults, config)
                MovementConfigCategory.setup(builder, defaults, config)
                ScreenConfigCategory.setup(builder, defaults, config)
                ItemsConfigCategory.setup(builder, defaults, config)
                OtherConfigCategory.setup(builder, defaults, config)
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

    // TODO/NOTE: Category for just fixes?

    // (QOL)
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
    @SerialEntry var dontClearChatOnDisconnect = false
    @SerialEntry var dontCloseChat = false
    // (QOL) Fixes
    @SerialEntry var fixMirrorArmSwing = true // NOTE: The only QOL setting to default to true, because it's a minecraft bug fix
    @SerialEntry var disableServerPoseAndBlockingVisualUpdates = false
    @SerialEntry var upMinPixelTransparencyLimit = false

    // (Movement)
    // (Movement) Sneaking
    @SerialEntry var removeSmoothSneaking = false
    @SerialEntry var oldSneakAnimationInterpolation = false
    @SerialEntry var fakeOldSneakEyeHeight = false
    @SerialEntry var fixSneakingFeetPosition = true
    @SerialEntry var oldSneakingFeetPosition = false
    @SerialEntry var syncPlayerModelWithEyeHeight = false
    @SerialEntry var sneakAnimationWhileFlying = true
    // (Movement) Other
    @SerialEntry var rotateBackwardsWalking = true
    @SerialEntry var uncapBlockingHeadRotation = true
    @SerialEntry var removeHeadRotationInterpolation = true
    @SerialEntry var fixVerticalBobbingTilt = true
    @SerialEntry var oldViewBobbing = true
    @SerialEntry var oldDeathLimbs = true
    @SerialEntry var fixBowArmMovement = true
    @SerialEntry var oldDamageTilt = true

    // (Screen)
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

    // (Items)
    // (Items) Fishing Rod
    @SerialEntry var oldFishingRodTextureStackCheck = true
    @SerialEntry var fishingRodLineInterpolation = false
    @SerialEntry var noMoveFishingRodLine = false
    @SerialEntry var oldFishingRodLinePositionThirdPerson = true
    @SerialEntry var oldFishingRodLineThickness = false
    @SerialEntry var thinFishingRodLineThickness = false
    @SerialEntry var fixCastLineCheck = true
    @SerialEntry var fixCastLineSwing = true
    // (Items) Fixes
    @SerialEntry var fixItemUseTextureCheck = true
    @SerialEntry var fixEquipAnimationItemCheck = true
    @SerialEntry var removeEquipAnimationOnItemUse = true
    @SerialEntry var removeClientsideBlockingDelay = true
    @SerialEntry var fixItemUsageCheck = true
    @SerialEntry var fixFireballClientsideVisual = true
    // (Items) Enchantment Glint
    @SerialEntry var oldGlintSpeed = true
    @SerialEntry var disableGlintOnItemDrops2D = false
    @SerialEntry var disableGlintOnItemFramed2D = false
    // (Items) Other
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

    // (Other)
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
    @SerialEntry var forceHighAttackSpeedVisual = false
}