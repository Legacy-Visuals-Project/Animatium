package btw.mixces.animatium.util

import com.google.common.base.MoreObjects
import net.minecraft.client.Minecraft
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState
import net.minecraft.network.protocol.game.ClientboundAnimatePacket
import net.minecraft.network.protocol.game.ServerboundSwingPacket
import net.minecraft.server.level.ServerChunkCache
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.effect.MobEffectUtil
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import java.util.*

object PlayerUtils {
    @JvmStatic
    fun getHandMultiplier(player: Player): Int {
        val hand = MoreObjects.firstNonNull(player.swingingArm, InteractionHand.MAIN_HAND)
        val direction = getHandMultiplier(player, hand)
        val client = Minecraft.getInstance()
        return (if (client.options.cameraType.isFirstPerson) 1 else -1) * direction
    }

    @JvmStatic
    fun getHandMultiplier(player: Player, hand: InteractionHand): Int {
        val arm = if (hand == InteractionHand.MAIN_HAND) player.mainArm else player.mainArm.opposite
        return getArmMultiplier(arm)
    }

    @JvmStatic
    fun getArmMultiplier(arm: HumanoidArm): Int {
        return if (arm == HumanoidArm.RIGHT) 1 else -1
    }

    @JvmStatic
    fun getPosWithEyeHeight(entity: Player, tickDelta: Float, eyeHeight: Double): Vec3 {
        return entity.getPosition(tickDelta).add(0.0, eyeHeight, 0.0)
    }

    @JvmStatic
    fun isBlockingArm(arm: HumanoidArm, armedEntityState: ArmedEntityRenderState): Boolean {
        return if (arm == HumanoidArm.LEFT && armedEntityState.leftArmPose == HumanoidModel.ArmPose.BLOCK) {
            true
        } else if (arm == HumanoidArm.RIGHT && armedEntityState.rightArmPose == HumanoidModel.ArmPose.BLOCK) {
            true
        } else {
            false
        }
    }

    @JvmStatic
    fun fakeHandSwing(player: Player, hand: InteractionHand) {
        // NOTE: Clientside fake swinging, doesn't send a packet
        if (!player.swinging || player.swingTime >= getHandSwingDuration(player) / 2 || player.swingTime < 0) {
            player.swingTime = -1
            player.swinging = true
            player.swingingArm = hand
        }
    }

    // Sends necessary swing packets, without playing the player hand swing animation
    @JvmStatic
    fun sendSwingPacket(player: LocalPlayer, hand: InteractionHand) {
        if (!player.swinging || player.swingTime >= getHandSwingDuration(player) / 2 || player.swingTime < 0) {
            if (player.level() is ServerLevel) {
                (player.level().chunkSource as ServerChunkCache).broadcast(
                    player,
                    ClientboundAnimatePacket(
                        player,
                        if (hand == InteractionHand.MAIN_HAND)
                            ClientboundAnimatePacket.SWING_MAIN_HAND
                        else
                            ClientboundAnimatePacket.SWING_OFF_HAND
                    )
                )
            }
        }

        player.connection?.send(ServerboundSwingPacket(hand))
    }

    // Fixes crash & doesn't require accesswidener
    @JvmStatic
    fun getHandSwingDuration(entity: LivingEntity): Int {
        return if (MobEffectUtil.hasDigSpeed(entity)) {
            6 - (1 + MobEffectUtil.getDigSpeedAmplification(entity))
        } else {
            if (entity.hasEffect(MobEffects.MINING_FATIGUE)) {
                6 + (1 + Objects.requireNonNull(entity.getEffect(MobEffects.MINING_FATIGUE))!!.amplifier) * 2
            } else {
                6
            }
        }
    }
}