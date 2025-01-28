package btw.mixces.animatium.mixins.renderer.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.util.Mth;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer {
    @WrapOperation(method = "extractCapeState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(FFF)F"))
    private static float animatium$changeLerpMethod(float delta, float start, float end, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldCapeMovement()) {
            return Mth.lerp(delta, start, end);
        } else {
            return original.call(delta, start, end);
        }
    }

    @ModifyArg(method = "extractCapeState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 1), index = 2)
    private static float animatium$uncapRotation(float original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDontClampCapeLean()) {
            return Float.MAX_VALUE;
        } else {
            return original;
        }
    }

    @WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;capeLean:F", ordinal = 1))
    private static boolean animatium$dontAssignLeanField(PlayerRenderState instance, float value) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getOldCapeMovement();
    }

    @WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;capeLean2:F", ordinal = 1))
    private static boolean animatium$dontAssignLean2Field(PlayerRenderState instance, float value) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getOldCapeMovement();
    }

    @WrapOperation(method = "getRenderOffset(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;)Lnet/minecraft/world/phys/Vec3;", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;isCrouching:Z"))
    private boolean animatium$fixSneakingFeetPosition(PlayerRenderState instance, Operation<Boolean> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixSneakingFeetPosition()) {
            return false;
        } else {
            return original.call(instance);
        }
    }
}
