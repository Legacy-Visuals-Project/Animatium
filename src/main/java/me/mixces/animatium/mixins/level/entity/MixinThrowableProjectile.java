package me.mixces.animatium.mixins.level.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrowableProjectile.class)
public abstract class MixinThrowableProjectile {
    @ModifyExpressionValue(method = "shouldRenderAtSqrDistance", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/world/entity/projectile/ThrowableProjectile;tickCount:I"))
    private int animatium$disableProjectileAgeCheck(int original) {
        return original + (AnimatiumConfig.instance().getDisableProjectileAgeCheck() ? 2 : 0);
    }
}
