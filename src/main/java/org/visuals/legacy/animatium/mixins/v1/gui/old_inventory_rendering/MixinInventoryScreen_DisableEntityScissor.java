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

package org.visuals.legacy.animatium.mixins.v1.gui.old_inventory_rendering;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.pip.GuiEntityRenderState;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(InventoryScreen.class)
public abstract class MixinInventoryScreen_DisableEntityScissor {
    @WrapOperation(method = "renderEntityInInventoryFollowsMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;submitEntityRenderState(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FLorg/joml/Vector3f;Lorg/joml/Quaternionf;Lorg/joml/Quaternionf;IIII)V"))
    private static void animatium$disableEntityScissor(final GuiGraphics instance, final EntityRenderState renderState, final float scale, final Vector3f translation, final Quaternionf rotation, final Quaternionf overrideCameraAngle, final int x0, final int y0, final int x1, final int y1, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.disableInventoryEntityScissor) {
            final ScreenRectangle bounds = new ScreenRectangle(0, 0, instance.guiWidth(), instance.guiHeight());
            final int expansion = 40;
            instance.guiRenderState.submitPicturesInPictureState(new GuiEntityRenderState(renderState, translation, rotation, overrideCameraAngle, x0 - expansion, y0 - expansion, x1 + expansion, y1 + expansion, scale, null, bounds));
        } else {
            original.call(instance, renderState, scale, translation, rotation, overrideCameraAngle, x0, y0, x1, y1);
        }
    }
}
