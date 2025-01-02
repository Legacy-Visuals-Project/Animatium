# Animatium

Join our discord: https://discord.gg/U48eDmst68

## License

This project is licensed under the LGPL-2.1 license.

## Download

Currently, there are no releases. If you want to use a development build, you can get
them [here](https://github.com/Legacy-Visuals-Project/Animatium/actions).

## Dependencies

This mod uses [YACL](https://modrinth.com/mod/yacl) as it's config library of choice. Make sure you install the correct
version to prevent crashing.
It is recommended that you also install [Mod Menu](https://modrinth.com/mod/modmenu/) in order to access the Animatium
config while in-game.

## Server Features

We currently have one payload which servers can use to change game functionality for pvp. Only servers can
enable/disable this, to not cause issues on other servers.
If more are requested/wanted, we will add and update this here.

### Payloads

#### Swing Miss Penalty

Allows the server to enable/disable the swing miss penalty on the client.

| Identifier            | Field Name            | Field Type | Description                                                             |
|-----------------------|-----------------------|------------|-------------------------------------------------------------------------|
| animatium:set_feature | Miss Penalty          | Boolean    | Turn on/off the swing miss penalty                                      |
|                       | Left Click Item Usage | Boolean    | Turn on/off the ability to start using a item whilst holding left click |

# Available Config Categories

<details>
  <summary>Quality of Life</summary>

## 🪶 Quality of Life

- [X] minimalViewBobbing
    - Description: Removes the view bobbing from tilting the world.
    - Type: BOOLEAN
- [X] showNametagInThirdperson
    - Description: Show the player nametag whilst in third-person.
    - Type: BOOLEAN
- [X] hideNameTagBackground
    - Description: Remove the nametag background.
    - Type: BOOLEAN
- [X] applyTextShadowToNametag
    - Description: Make the nametag use text shadow.
    - Type: BOOLEAN
- [X] oldDebugHudTextColor
    - Description: Makes the debug hud text color white again.
    - Type: BOOLEAN
- [X] fixMirrorArmSwing
    - Description: Fix the left-arm swing mirroring.
    - Type: BOOLEAN
- [X] persistentBlockOutline
    - Description: Always show block outline, no matter the gamemode or state.
    - Type: BOOLEAN
- [X] alwaysShowSharpParticles
    - Description: Always show the sharpness particles when damaging/hitting an entity.
    - Type: BOOLEAN
- [ ] forceItemGlintOnEntity
    - Description: Makes the entity glint use the same texture as the item glint like it was in <=1.19.
    - Type: BOOLEAN
- [X] disableRecipeAndTutorialToasts
    - Description: Disable recipe and tutorial toasts.
    - Type: BOOLEAN
- [X] disableServerPoseAndBlockingVisualUpdates
    - Description: Stops the server from updating your pose/animations. (Fixes MC-159163)
    - Type: BOOLEAN
- [X] showArmWhileInvisible
    - Description: Shows the arm as partially visible whilst invisible, like spectator mode or invisibly effect.
    - Type: BOOLEAN
- [X] upMinPixelTransparencyLimit
    - Description: Makes the minimum 0-transparency value less than or equal to 0.1. This fixes textures with invisible
      pixels that cause issues.
    - Type: BOOLEAN
- [X] missPenaltySwing
  - Description: In vanilla Minecraft, if the player has missed their hit, there will be a 10 ms delay on top of the attack cooldown before they can can swing again. Enable this feature to play a fake swing animation during that 10 ms delay to match <=1.7.x.
  - Type: BOOLEAN
- [X] showUsageSwingingParticles
  - Description: Shows fake block-breaking particles during usage swinging to match <=1.7.x.
  - Type: BOOLEAN
- [X] customHitColor
  - Description: Modifies the entity damage tint color.
  - Type: BOOLEAN 
- [X] deepRedHurtTint
  - Description: Modifies the entity damage tint alpha to be less like in Oranges Old Animations mod.
  - Type: BOOLEAN 
  </details>

<details>
  <summary>Movement</summary>

## 🏃 Movement

- [X] rotateBackwardsWalking
    - Description: Rotates the entity body sideways when walking backwards like it was in <=1.11.2.
    - Type: BOOLEAN
- [X] uncapBlockingHeadRotation
    - Description: Reverts the change in 1.20.2, making head rotation when blocking as it used to be.
    - Type: BOOLEAN
- [X] removeHeadRotationInterpolation
    - Description: Removes the head rotation interpolation like in <=1.7.x.
    - Type: BOOLEAN
- [X] fixVerticalBobbingTilt
    - Description: Brings back the camera tilting when falling/flying up like it was in <=1.13.x. (Fixes MC-225335)
    - Type: BOOLEAN
- [X] oldViewBobbing
    - Description: Undoes the 1.21.2+ view bobbing change where when sneaking, your hand still moves normally.
    - Type: BOOLEAN
- [X] oldDeathLimbs
    - Description: Makes entities continue their animation even upon death.
    - Type: BOOLEAN
- [X] fixBowArmMovement
    - Description: Restores old player body movement in third-person when using the bow like in <=1.7?
    - Type: BOOLEAN
- [ ] oldCapeMovement
    - Description: Changes the cape model movement to be how it used to be in <=1.8? Currently broken and doesn't work
      properly.
    - Type: BOOLEAN
- [X] removeSmoothSneaking
    - Description: Removes the smooth sneaking camera animation, making it like it was in 1.8-1.12.2.
    - Type: BOOLEAN
- [X] oldSneakAnimationInterpolation
    - Description: Brings back the <=1.7.x sneaking camera animation interpolation.
    - Type: BOOLEAN
- [X] oldSneakEyeHeight
    - Description: Changes the sneak eye height to be as it was <=1.13.2.
    - Type: BOOLEAN
- [X] fixSneakingFeetPosition
    - Description: Fixes the sneaking model offset to be like <=1.11.x.
    - Type: BOOLEAN
- [X] oldSneakingFeetPosition
    - Description: Fixes the sneaking model offset to be like <1.14?
    - Type: BOOLEAN
- [X] syncPlayerModelWithEyeHeight
    - Description: Synchronizes the player model to the eye height like in <=1.7.x.
    - Type: BOOLEAN
- [X] sneakAnimationWhileFlying
    - Description: Shows the sneaking animation in third-person whilst flying down like in <=1.13.x.
    - Type: BOOLEAN
  </details>

<details>
  <summary>Screen</summary>

## 📷 Screen

- [X] showCrosshairInThirdperson
    - Description: Show crosshair whilst in thirdperson like in <=1.8.x.
    - Type: BOOLEAN
- [X] fixHighAttackSpeedIndicator
    - Description: Hides the attack indicator when you have such a high attack speed. (Fixes MC-268420)
    - Type: BOOLEAN
- [X] removeHeartFlash
    - Description: Remove heart blinking like in <=1.7.x.
    - Type: BOOLEAN
- [X] fixTextStrikethroughStyle
    - Description: Changes the text strikethrough position to make it look like it did in <=1.12.2.
    - Type: BOOLEAN
- [X] centerScrollableListWidgets
    - Description: Center scrollable list widgets like <=1.7.x.
    - Type: BOOLEAN
- [X] oldListWidgetSelectedBorderColor
    - Description: Returns the old list widget selected border color from <=1.15?
    - Type: BOOLEAN
- [X] oldButtonTextColors
    - Description: Bring back the old yellow hover/grayish text colors like in <=1.14.4.
    - Type: BOOLEAN
- [X] removeDebugHudBackground
    - Description: Remove the F3 Debug Hud background.
    - Type: BOOLEAN
- [X] debugHudTextShadow
    - Description: Add text-shadow to F3 Debug Hud.
    - Type: BOOLEAN
- [X] oldChatPosition
    - Description: Moves chat down 12 pixels like in <=1.8.x.
    - Type: BOOLEAN
- [X] disableCameraTransparentPassthrough
    - Description: Stops camera passthrough in thirdperson in glass/etc.
    - Type: BOOLEAN
- [X] cameraVersion
    - Description: Change the camera position to be as it was in said version range.
    - Type: ENUM
        - 1.8 and below (V1_8)
        - 1.9 through to 1.13.2 (V1_9_V1_13_2)
        - 1.14 through to 1.14.3 (V1_14_V1_14_3)
        - LATEST
  </details>

<details>
  <summary>Items</summary>

## 🥍 Items

- [X] tiltItemPositions
    - Description: Tilts the held item position to make held items look like they did in <=1.7.x.
    - Type: BOOLEAN
- [X] tiltItemPositions
    - Description: Tilts the third-person held item position to make held items look like they did in <=1.7.x.
    - Type: BOOLEAN
- [X] applyItemSwingUsage
    - Description: Block hitting (apply swing offset in item usage code).
    - Type: BOOLEAN
- [X] removeEquipAnimationOnItemUse
    - Description: Fixes the blocking animation which plays the equip animation on use, and others.
    - Type: BOOLEAN
- [X] disableItemUsingTextureInGui
    - Description: Disables the item usage texture in the GUI like in <=1.8.x (mainly rod/bow/crossbow).
    - Type: BOOLEAN
- [X] itemDropsFaceCamera
    - Description: Makes item entities face the camera / use camera yaw like <=1.7.x when fast graphics.
    - Type: BOOLEAN
- [X] itemDrops2D
    - Description: Makes item entities render 2D when it's an item (not blocks).
    - Type: BOOLEAN
- [X] oldDurabilityBarColors
    - Description: Restores the old durability damage colors from <1.11.
    - Type: BOOLEAN
- [X] oldItemRarities
    - Description: Restores the old rarities for items visually from <1.21.2. (also old trident rarity from <1.21)
    - Type: BOOLEAN
- [X] removeClientsideBlockingDelay
    - Description: Removes the pesky blocking delay that modern clients have. Shouldn't flag on servers.
    - Type: BOOLEAN
- [X] fixItemUsageCheck
    - Description: Fixes item usage whilst inside a GUI, for example prevents continuous visual blocking, etc.
    - Type: BOOLEAN
- [X] oldFishingRodTextureStackCheck
    - Description: Brings back old fishing rod stack texture check from <=1.8.
    - Type: BOOLEAN
- [X] fishingRodLineInterpolation
    - Description: Correctly interpolates the fishing rod cast line with the eye height from <1.14?
    - Type: BOOLEAN
- [X] noMoveFishingRodLine
    - Description: Does not move the fishing rod cast line while sneaking when viewed in the third person mode from <
      =1.7.
    - Type: BOOLEAN
- [X] oldFishingRodLinePositionThirdPerson
    - Description: Adjusts the position of the fishing rod cast line horizontally like in <=1.7.
    - Type: BOOLEAN
- [X] oldFishingRodLineThickness
    - Description: Restores the old fishing rod line thickness from <1.13?
    - Type: BOOLEAN
- [X] thinFishingRodLineThickness
    - Description: Makes the fishing rod line super thin. Overrides the above setting.
    - Type: BOOLEAN
- [X] fixCastLineCheck
    - Description: Fixes the arm logic for casting the fishing rod.
    - Type: BOOLEAN
- [X] fixCastLineSwing
    - Description: Fixes the swing logic for casting the fishing rod.
    - Type: BOOLEAN
  </details>

<details>
  <summary>Old Settings</summary>

## 🛠️ Old Settings

- [X] oldBlueVoidSky
    - Description: Brings back the forgotten blue void part of the sky. (Fixes MC-257056)
    - Type: BOOLEAN
- [X] oldSkyHorizonHeight
    - Description: Changes the horizon height to how it was in <=1.16.5.
    - Type: BOOLEAN
- [X] oldCloudHeight
    - Description: Changes the cloud height back to 128 like in <=1.16.5.
    - Type: BOOLEAN
- [X] legacyThirdpersonSwordBlockingPosition
    - Description: Brings back the old third-person sword blocking look from <=1.7.
    - Type: BOOLEAN
- [X] lockBlockingArmRotation
    - Description: Locks the third-person blocking arm rotation.
    - Type: BOOLEAN
- [X] disableProjectileAgeCheck
    - Description: Render projectile at all ages <=1.15?
    - Type: BOOLEAN
- [X] oldBlockMiningProgress
    - Description: Bring back the old block mining progress <=1.18?
    - Type: BOOLEAN
- [X] disableInventoryEntityScissor
    - Description: Allows the inventory entity model to render fully.
    - Type: BOOLEAN
- [X] legacyBlockOutlineRendering
    - Description: Restores the legacy block outline rendering from <=1.14.4.
    - Type: BOOLEAN
- [X] removeFOVBasedProjection
    - Description: Removes the inclusion of FOV in the camera projection calculations.
    - Type: BOOLEAN
- [X] hideModelWhilstSleeping
    - Description: Hides the player model whilst sleeping like in <=1.12? Only affects you.
    - Type: BOOLEAN
- [X] entityArmorHurtTint
    - Description: Tints the armor when an entity is damaged like in <=1.7.x.
    - Type: BOOLEAN
  </details>

## Support

Have any issues or need support? Feel free to use
our [issue tracker](https://github.com/Legacy-Visuals-Project/Animatium/issues) to address that. If you are reporting a
crash, make sure you include information about the mods you are using and attach any relevant log files you have. If you
want to suggest features, join our [discord](https://discord.gg/U48eDmst68)!