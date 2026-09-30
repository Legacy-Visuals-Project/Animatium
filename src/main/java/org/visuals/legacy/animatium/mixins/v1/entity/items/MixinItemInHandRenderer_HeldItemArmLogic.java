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

package org.visuals.legacy.animatium.mixins.v1.entity.items;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer_HeldItemArmLogic {
    @Inject(method = "renderPlayerArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/ClientAsset$Texture;texturePath()Lnet/minecraft/resources/Identifier;", shift = At.Shift.AFTER))
    private void animatium$extractArmState(final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final float inverseArmHeight, final float attackValue, final HumanoidArm arm, final CallbackInfo ci, @Local(name = "avatarRenderer") final AvatarRenderer<AbstractClientPlayer> avatarRenderer) {
        final Minecraft minecraft = Minecraft.getInstance();
        if (Animatium.isEnabled() && (AnimatiumConfig.instance().other.heldItemArmLogic || AnimatiumConfig.instance().extras.showArmWhileInvisible || AnimatiumConfig.instance().extras.damageTintItems) && minecraft.player != null) {
            avatarRenderer.extractRenderState(minecraft.player, avatarRenderer.createRenderState(), minecraft.getDeltaTracker().getGameTimeDeltaTicks());
        }
    }
}
