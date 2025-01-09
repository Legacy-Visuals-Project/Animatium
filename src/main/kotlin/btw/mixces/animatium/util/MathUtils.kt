package btw.mixces.animatium.util

import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

object MathUtils {
    @JvmStatic
    fun toRadians(angle: Float): Float {
        return angle * Math.PI.toFloat() / 180F
    }

    @JvmStatic
    fun expandVoxelShape(shape: VoxelShape, value: Float): VoxelShape {
        // Code from VoxelShape#simplify
        // TODO: simplify this code? or find alternative?
        val voxelShapes = arrayListOf<VoxelShape>()
        voxelShapes.add(Shapes.empty())
        shape.forAllBoxes { minX, minY, minZ, maxX, maxY, maxZ ->
            voxelShapes[0] = Shapes.joinUnoptimized(
                voxelShapes[0],
                Shapes.box(
                    minX - value,
                    minY - value,
                    minZ - value,
                    maxX + value,
                    maxY + value,
                    maxZ + value
                ),
                BooleanOp.OR
            )
        }
        return voxelShapes[0]
    }
}