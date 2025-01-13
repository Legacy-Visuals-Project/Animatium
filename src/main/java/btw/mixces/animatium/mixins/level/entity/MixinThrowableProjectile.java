package btw.mixces.animatium.mixins.level.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrowableProjectile.class)
public abstract class MixinThrowableProjectile {
    @WrapOperation(method = "shouldRenderAtSqrDistance", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/world/entity/projectile/ThrowableProjectile;tickCount:I"))
    private int animatium$disableProjectileAgeCheck(ThrowableProjectile instance, Operation<Integer> original) {
        return original.call(instance) + (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDisableProjectileAgeCheck() ? 2 : 0);
    }
}
