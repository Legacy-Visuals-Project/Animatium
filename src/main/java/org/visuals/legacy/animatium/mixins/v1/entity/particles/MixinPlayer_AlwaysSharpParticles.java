/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * Licensed under the PolyForm Shield License 1.0.0.
 * You may obtain a copy of the license at
 * https://polyformproject.org/licenses/shield/1.0.0
 */

package org.visuals.legacy.animatium.mixins.v1.entity.particles;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(Player.class)
public abstract class MixinPlayer_AlwaysSharpParticles {
    @Shadow
    public abstract void magicCrit(final Entity entity);

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;cannotAttack(Lnet/minecraft/world/entity/Entity;)Z", shift = At.Shift.AFTER))
    private void animatium$alwaysSharpParticles(final Entity entity, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.alwaysSharpParticles) {
            this.magicCrit(entity);
        }
    }

    @WrapWithCondition(method = "attackVisualEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;magicCrit(Lnet/minecraft/world/entity/Entity;)V"))
    private boolean animatium$disableVanillaCrit(final Player instance, final Entity entity) {
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().extras.alwaysSharpParticles;
    }
}
