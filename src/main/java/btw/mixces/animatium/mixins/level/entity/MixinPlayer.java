package btw.mixces.animatium.mixins.level.entity;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.PlayerUtils;
import btw.mixces.animatium.util.ViewBobbingStorage;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {
    @Shadow
    public abstract void magicCrit(Entity target);

    protected MixinPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void animatium$alwaysShowSharpParticles(Entity target, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getAlwaysShowSharpParticles() &&
                target.isAttackable() &&
                !target.skipAttackInteraction(this) &&
                !Minecraft.getInstance().isSingleplayer()) {
            // TODO: Fix always sharp particles on Lunar client
            for (int i = 0; i < AnimatiumConfig.instance().getParticleMultiplier(); ++i) {
                this.magicCrit(target);
            }
        }
    }

    @WrapWithCondition(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;magicCrit(Lnet/minecraft/world/entity/Entity;)V"))
    private boolean animatium$disableDefaultSharpParticles(Player instance, Entity target) {
        return !AnimatiumClient.getEnabled() || !(AnimatiumConfig.instance().getAlwaysShowSharpParticles() && !Minecraft.getInstance().isSingleplayer());
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
    private void animatium$disableSwingOnDropInventory(Player instance, InteractionHand hand, Operation<Void> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDisableSwingOnDrop()) {
            PlayerUtils.sendSwingPacket((LocalPlayer) instance, hand);
        } else {
            original.call(instance, hand);
        }
    }
}
