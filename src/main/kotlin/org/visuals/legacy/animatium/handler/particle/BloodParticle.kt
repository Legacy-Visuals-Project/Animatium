/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * Licensed under the PolyForm Shield License 1.0.0.
 * You may obtain a copy of the license at
 * https://polyformproject.org/licenses/shield/1.0.0
 */

package org.visuals.legacy.animatium.handler.particle

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.TerrainParticle
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.GameType
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RedStoneWireBlock
import net.minecraft.world.phys.EntityHitResult
import org.visuals.legacy.animatium.config.AnimatiumConfig

// Credit to Orange Marshalls 1.8 Mod "Vanilla Enhancements"
class BloodParticle(
    level: ClientLevel,
    x: Double, y: Double, z: Double,
    velocityX: Double, velocityY: Double, velocityZ: Double,
    random: RandomSource
) : TerrainParticle(
    level,
    x, y, z,
    velocityX, velocityY, velocityZ,
    Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.POWER, 15)
) {
    init {
        this.rCol = random.nextFloat() * 0.25F + 0.3F
        this.gCol = 0.0F
        this.bCol = 0.0F
        this.quadSize *= 0.8F
    }

    companion object {
        val RANDOM = RandomSource.createNewThreadLocalInstance()

        @JvmStatic
        fun canSpawn(): Boolean {
            val minecraft = Minecraft.getInstance()
            val hitResult = minecraft.hitResult
            if (hitResult !is EntityHitResult) {
                return false
            } else if (!hitResult.entity.isAlive) {
                return false
            } else {
                val netPlayerHandler = minecraft.gameMode ?: return false
                return (netPlayerHandler.playerMode == GameType.SURVIVAL) || (netPlayerHandler.playerMode == GameType.CREATIVE)
            }
        }

        @JvmStatic
        fun spawn(target: Entity) {
            val level = target.level()
            if (level is ClientLevel) {
                val eyePos = BlockPos.containing(target.x, target.y + 0.5, target.z)
                val count = 5 * AnimatiumConfig.instance().extras.bloodParticleMultiplier
                for (i in 0..<count) {
                    val x = eyePos.x.toDouble() + Math.random()
                    val y = eyePos.y.toDouble() + 0.3 + Math.random() * 1.3
                    val z = eyePos.z.toDouble() + Math.random()
                    val velocityX = Math.random() * 2.0 - 1.3
                    val velocityY = Math.random() * 0.8
                    val velocityZ = Math.random() * 2.0 - 1.3
                    Minecraft.getInstance().particleEngine.add(BloodParticle(level, x, y, z, velocityX, velocityY, velocityZ, RANDOM))
                }
            }
        }
    }
}