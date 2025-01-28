package btw.mixces.animatium.mixins.level.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.PlayerUtils;
import btw.mixces.animatium.util.ViewBobbingStorage;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {
    protected MixinPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyExpressionValue(method = "attack", at = @At(value = "CONSTANT", args = "floatValue=0.0", ordinal = 6))
    private float animatium$alwaysShowSharpParticles(float original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getAlwaysShowSharpParticles()) {
            return -1.0F;
        } else {
            return original;
        }
    }

    @Inject(method = "getMaxHeadRotationRelativeToBody", at = @At(value = "RETURN"), cancellable = true)
    private void animatium$uncapBlockingHeadRotation(CallbackInfoReturnable<Float> cir) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getUncapBlockingHeadRotation()) {
            cir.setReturnValue(50F);
        }
    }

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setSpeed(F)V", shift = At.Shift.AFTER))
    private void animatium$updateBobbingTiltValues(CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getFixVerticalBobbingTilt()) {
            ViewBobbingStorage bobbingAccessor = (ViewBobbingStorage) this;
            float g = this.onGround() || this.getHealth() <= 0.0F ? 0.0F : (float) (Math.atan(-this.getDeltaMovement().y * (double) 0.2F) * 15.0F);
            bobbingAccessor.animatium$setBobbingTilt(Mth.lerp(0.8F, bobbingAccessor.animatium$getBobbingTilt(), g));
        }
    }

    @WrapOperation(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;swing(Lnet/minecraft/world/InteractionHand;)V"))
    private void animatium$disableSwingOnDropInventory(Player player, InteractionHand hand, Operation<Void> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDisableSwingOnDrop()) {
            PlayerUtils.sendSwingPacket((LocalPlayer) player, hand);
        } else {
            original.call(player, hand);
        }
    }

    @WrapWithCondition(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"))
    private boolean animatium$disableModernCombatParticles$damageIndicator(ServerLevel instance, ParticleOptions particleOptions, double d, double e, double f, int i, double g, double h, double j, double k) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getDisableModernCombatParticles();
    }

    @WrapWithCondition(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;sweepAttack()V"))
    private boolean animatium$disableModernCombatParticles$sweep(Player instance) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getDisableModernCombatParticles();
    }
}
