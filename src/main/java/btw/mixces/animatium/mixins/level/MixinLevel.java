package btw.mixces.animatium.mixins.level;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Level.class)
public abstract class MixinLevel {
    // TODO/NOTE: Find a better way
    @Unique
    private static final List<SoundEvent> animatium$ignoreSounds = List.of(
            SoundEvents.PLAYER_ATTACK_KNOCKBACK,
            SoundEvents.PLAYER_ATTACK_SWEEP,
            SoundEvents.PLAYER_ATTACK_CRIT,
            SoundEvents.PLAYER_ATTACK_STRONG,
            SoundEvents.PLAYER_ATTACK_WEAK,
            SoundEvents.PLAYER_ATTACK_NODAMAGE
    );

    @WrapWithCondition(method = "playSeededSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFJ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSeededSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V"))
    private boolean animatium$disableModernCombatSounds(Level instance, @Nullable Entity player, double x, double y, double z, Holder<SoundEvent> soundEventHolder, SoundSource soundSource, float volume, float pitch, long unknown) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDisableModernCombatSounds()) {
            return !animatium$ignoreSounds.contains(soundEventHolder.value());
        } else {
            return true;
        }
    }
}
