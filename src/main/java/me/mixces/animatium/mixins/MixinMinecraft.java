package me.mixces.animatium.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.AnimatiumClient;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import me.mixces.animatium.util.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Final
    public Options options;

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Final
    public ParticleEngine particleEngine;

    @Shadow
    @Nullable
    public ClientLevel level;

    @Inject(method = "startAttack", at = @At(value = "RETURN", ordinal = 0))
    private void animatium$missPenaltySwing(CallbackInfoReturnable<Boolean> cir) {
        if (AnimatiumConfig.getInstance().getFakeMissPenaltySwing() && player != null) {
            PlayerUtils.fakeHandSwing(player, InteractionHand.MAIN_HAND);
        }
    }

    @WrapOperation(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V", ordinal = 2))
    private void animatium$disableSwingOnUse(LocalPlayer instance, InteractionHand hand, Operation<Void> original) {
        ItemStack itemStack = instance.getItemInHand(hand);
        if (AnimatiumConfig.getInstance().getDisableSwingOnUse() && ItemUtils.isSwingItemBlacklisted(itemStack)) {
            PlayerUtils.sendSwingPacket(instance, hand);
        } else {
            original.call(instance, hand);
        }
    }

    @WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V"))
    private void animatium$disableSwingOnDrop(LocalPlayer instance, InteractionHand hand, Operation<Void> original) {
        if (AnimatiumConfig.getInstance().getDisableSwingOnDrop()) {
            PlayerUtils.sendSwingPacket(instance, hand);
        } else {
            original.call(instance, hand);
        }
    }

    @WrapOperation(method = "startAttack", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;missTime:I", ordinal = 0))
    private int animatium$disableSwingMissPenalty(Minecraft instance, Operation<Integer> original) {
        // TODO/NOTE: This also disables the miss penalty on a block, should that be fixed?
        // TODO/NOTE: For now, this should be fine.
        if (AnimatiumClient.getDisableSwingMissPenalty()) {
            return 0;
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;isDestroying()Z"))
    private boolean animatium$leftClickItemUsage(MultiPlayerGameMode instance, Operation<Boolean> original) {
        if (AnimatiumClient.getLeftClickItemUsage()) {
            return false;
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V", ordinal = 0))
    private void animatium$disableSwingOnEntityInteract(LocalPlayer instance, InteractionHand hand, Operation<Void> original) {
        if (AnimatiumConfig.getInstance().getDisableSwingOnEntityInteract()) {
            PlayerUtils.sendSwingPacket(instance, hand);
        } else {
            original.call(instance, hand);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void animatium$applySwingWhilstMining(CallbackInfo ci) {
        if (AnimatiumConfig.getInstance().getApplyItemSwingUsage()) {
            LocalPlayer player = this.player;
            if (player == null || player.getItemInHand(player.getUsedItemHand()).isEmpty() || !player.isUsingItem() || !this.options.keyAttack.isDown()) {
                return;
            }

            InteractionHand activeHand = player.getUsedItemHand();
            InteractionHand hand = AnimatiumConfig.getInstance().getAllowOffhandUsageSwinging() ? activeHand : InteractionHand.MAIN_HAND;
            if (this.hitResult != null && this.hitResult.getType() == HitResult.Type.BLOCK && activeHand.equals(hand)) {
                BlockHitResult blockHitResult = (BlockHitResult) this.hitResult;

                BlockPos blockPos = blockHitResult.getBlockPos();
                if (AnimatiumConfig.getInstance().getShowUsageSwingingParticles() && this.level != null && !this.level.getBlockState(blockPos).isAir()) {
                    Direction direction = blockHitResult.getDirection();
                    this.particleEngine.crack(blockPos, direction);
                }

                PlayerUtils.fakeHandSwing(player, hand);
            }
        }
    }
}
