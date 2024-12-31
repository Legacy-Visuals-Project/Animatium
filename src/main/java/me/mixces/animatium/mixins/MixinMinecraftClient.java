package me.mixces.animatium.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import me.mixces.animatium.util.PlayerUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    @Final
    public GameOptions options;

    @Shadow
    @Nullable
    public HitResult crosshairTarget;

    @Shadow
    @Final
    public ParticleManager particleManager;

    @Shadow
    @Nullable
    public ClientWorld world;

    @Inject(method = "doAttack", at = @At(value = "RETURN", ordinal = 0))
    private void animatium$missPenaltySwing(CallbackInfoReturnable<Boolean> cir) {
        if (AnimatiumConfig.getInstance().getMissPenaltySwing() && player != null) {
            PlayerUtils.fakeHandSwing(player, Hand.MAIN_HAND);
        }
    }

    @WrapOperation(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V", ordinal = 2))
    private void animatium$disableSwingOnUse(ClientPlayerEntity instance, Hand hand, Operation<Void> original) {
        ItemStack itemStack = instance.getStackInHand(hand);
        if (AnimatiumConfig.getInstance().getDisableSwingOnUse() && ItemUtils.isSwingItemBlacklisted(itemStack)) {
            PlayerUtils.sendSwingPacket(instance, hand);
        } else {
            original.call(instance, hand);
        }
    }

    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"))
    private void animatium$disableSwingOnDrop(ClientPlayerEntity instance, Hand hand, Operation<Void> original) {
        if (AnimatiumConfig.getInstance().getDisableSwingOnDrop()) {
            PlayerUtils.sendSwingPacket(instance, hand);
        } else {
            original.call(instance, hand);
        }
    }

    @WrapOperation(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V", ordinal = 0))
    private void animatium$disableSwingOnEntityInteract(ClientPlayerEntity instance, Hand hand, Operation<Void> original) {
        if (AnimatiumConfig.getInstance().getDisableSwingOnEntityInteract()) {
            PlayerUtils.sendSwingPacket(instance, hand);
        } else {
            original.call(instance, hand);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void animatium$applySwingWhilstMining(CallbackInfo ci) {
        if (AnimatiumConfig.getInstance().getApplyItemSwingUsage()) {
            ClientPlayerEntity player = this.player;
            if (player == null || player.getStackInHand(player.getActiveHand()) == null || !player.isUsingItem() || !this.options.attackKey.isPressed()) {
                return;
            }

            Hand activeHand = player.getActiveHand();
            Hand hand = AnimatiumConfig.getInstance().getAllowOffhandUsageSwinging() ? activeHand : Hand.MAIN_HAND;
            if (AnimatiumConfig.getInstance().getAlwaysAllowUsageSwinging() || (this.crosshairTarget != null && this.crosshairTarget.getType() == HitResult.Type.BLOCK && activeHand.equals(hand))) {
                BlockHitResult blockHitResult = (BlockHitResult) this.crosshairTarget;
                assert blockHitResult != null;
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (AnimatiumConfig.getInstance().getShowUsageSwingingParticles() && !Objects.requireNonNull(this.world).getBlockState(blockPos).isAir()) {
                    Direction direction = blockHitResult.getSide();
                    this.particleManager.addBlockBreakingParticles(blockPos, direction);
                }

                PlayerUtils.fakeHandSwing(player, hand);
            }
        }
    }
}
