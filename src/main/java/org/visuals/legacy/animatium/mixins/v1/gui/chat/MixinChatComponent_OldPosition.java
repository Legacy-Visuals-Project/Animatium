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

package org.visuals.legacy.animatium.mixins.v1.gui.chat;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(ChatComponent.class)
public abstract class MixinChatComponent_OldPosition {
    @Unique
    private static final int animatium$oldChatY = 28;

    @Expression("40")
    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IIZ)V", at = @At("MIXINEXTRAS:EXPRESSION"))
    private int animatium$oldChatPosition$render(final int original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.oldChatPosition) {
            return animatium$oldChatY;
        } else {
            return original;
        }
    }

    // TODO
    /*@Expression("40.0")
    @ModifyExpressionValue(method = "handleChatQueueClicked", at = @At("MIXINEXTRAS:EXPRESSION"))
    private double animatium$oldChatPosition$handleChatQueueClicked(double original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.oldChatPosition) {
            return animatium$oldChatY;
        } else {
            return original;
        }
    }

    @Expression("40.0")
    @ModifyExpressionValue(method = "screenToChatY", at = @At("MIXINEXTRAS:EXPRESSION"))
    private double animatium$oldChatPosition$screenToChatY(double original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.oldChatPosition) {
            return animatium$oldChatY;
        } else {
            return original;
        }
    }*/
}
