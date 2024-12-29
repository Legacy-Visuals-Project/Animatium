package me.mixces.animatium.util

import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes

object MathUtils {
    @JvmStatic
    fun toRadians(angle: Float): Float {
        return angle * Math.PI.toFloat() / 180F
    }

    @JvmStatic
    fun toAngle(radians: Float): Float {
        return radians / Math.PI.toFloat() * 180F
    }

    @JvmStatic
    fun expandVoxelShape(shape: VoxelShape, value: Float): VoxelShape {
        // Code from VoxelShape#simplify
        // TODO: simplify this code? or find alternative?
        val voxelShapes = arrayListOf<VoxelShape>()
        voxelShapes.add(VoxelShapes.empty())
        shape.forEachBox { minX, minY, minZ, maxX, maxY, maxZ ->
            voxelShapes[0] = VoxelShapes.combine(
                voxelShapes[0],
                VoxelShapes.cuboid(
                    minX - value,
                    minY - value,
                    minZ - value,
                    maxX + value,
                    maxY + value,
                    maxZ + value
                ),
                BooleanBiFunction.OR
            )
        }
        return voxelShapes[0]
    }
}