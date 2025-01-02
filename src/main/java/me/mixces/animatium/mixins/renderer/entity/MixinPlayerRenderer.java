package me.mixces.animatium.mixins.renderer.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer {
    @WrapOperation(method = "getRenderOffset(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;)Lnet/minecraft/world/phys/Vec3;", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;isCrouching:Z"))
    private boolean animatium$fixSneakingFeetPosition(PlayerRenderState instance, Operation<Boolean> original) {
        if (AnimatiumConfig.getInstance().getFixSneakingFeetPosition()) {
            return false;
        } else {
            return original.call(instance);
        }
    }

    // TODO/NOTE: Fix inaccuracies/brokenness
//    @WrapOperation(method = "updateCape", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Mth;lerpAngleDegrees(FFF)F"))
//    private static float undoLerpAngles(float delta, float start, float end, Operation<Float> original) {
//        if (AnimatiumConfig.getInstance().getOldCapeMovement()) {
//            return Mth.lerp(delta, start, end);
//        } else {
//            return original.call(delta, start, end);
//        }
//    }
//
//    @WrapOperation(method = "updateCape", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Mth;clamp(FFF)F", ordinal = 1))
//    private static float undoClampOne(float value, float min, float max, Operation<Float> original) {
//        if (AnimatiumConfig.getInstance().getOldCapeMovement()) {
//            return value;
//        } else {
//            return original.call(value, min, max);
//        }
//    }
//
//    @WrapOperation(method = "updateCape", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Mth;clamp(FFF)F", ordinal = 2))
//    private static float undoClampTwo(float value, float min, float max, Operation<Float> original) {
//        if (AnimatiumConfig.getInstance().getOldCapeMovement()) {
//            return value;
//        } else {
//            return original.call(value, min, max);
//        }
//    }
}
