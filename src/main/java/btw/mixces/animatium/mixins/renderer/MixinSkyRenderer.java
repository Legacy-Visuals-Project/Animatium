/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2025 lowercasebtw
 * Copyright (C) 2024-2025 mixces
 * Copyright (C) 2024-2025 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package btw.mixces.animatium.mixins.renderer;

import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// TODO/NOTE: Just bug fixes here.
@Mixin(SkyRenderer.class)
public abstract class MixinSkyRenderer {
    @WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"))
    private void animatium$pushMatrix(PoseStack instance, Operation<Void> original) {
        if (AnimatiumConfig.instance().getOldVoidSkyFogHeight()) {
            RenderSystem.getModelViewStack().pushMatrix();
            RenderSystem.getModelViewStack().mul(instance.last().pose());
        }
    }

    @WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private void animatium$translate(PoseStack instance, float x, float y, float z, Operation<Void> original) {
        if (AnimatiumConfig.instance().getOldVoidSkyFogHeight()) {
            RenderSystem.getModelViewStack().translate(x, y, z);
        }
    }

    @WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"))
    private void animatium$popMatrix(PoseStack instance, Operation<Void> original) {
        if (AnimatiumConfig.instance().getOldVoidSkyFogHeight()) {
            RenderSystem.getModelViewStack().popMatrix();
        }
    }
}
