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

package org.visuals.legacy.animatium.mixins.v1.entity.armor_hurt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.moulberry.mixinconstraints.annotations.IfModAbsent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.UvMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.pipeline.AnimatiumRenderTypes;

@IfModAbsent("ichor")
@Mixin(EquipmentLayerRenderer.class)
public abstract class MixinEquipmentLayerRenderer_DamageTintArmor {
    @Unique
    private static final String RENDER_LAYERS_TARGET = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V";

    @WrapOperation(method = RENDER_LAYERS_TARGET, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;armorCutoutNoCull(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType animatium$renderLayerArmorTint(final Identifier texture, final Operation<RenderType> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.damageTintArmor) {
            return RenderTypes.entityCutoutZOffset(texture);
        } else {
            return original.call(texture);
        }
    }

    @WrapOperation(method = RENDER_LAYERS_TARGET, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;armorTrim(Lnet/minecraft/resources/Identifier;Z)Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType animatium$renderLayerArmorTrimTint(final Identifier texture, final boolean decal, final Operation<RenderType> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.damageTintArmor) {
            return RenderTypes.entityCutoutZOffset(texture);
        } else {
            return original.call(texture, decal);
        }
    }

    @WrapOperation(method = RENDER_LAYERS_TARGET, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;armorCutoutNoCullGlint(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType animatium$disableVanillaGlint(final Identifier texture, final Operation<RenderType> original) {
        if (Animatium.isEnabled() &&
                AnimatiumConfig.instance().other.damageTintArmor &&
                AnimatiumConfig.instance().other.glintAffectsArmorTint &&
                !Minecraft.getInstance().options.improvedTransparency().get()
        ) {
            return RenderTypes.entityCutoutZOffset(texture);
        } else {
            return original.call(texture);
        }
    }

    @WrapOperation(method = RENDER_LAYERS_TARGET, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/UvMapping;I)V", ordinal = 0))
    private <S> void animatium$useOverlayArmorGlint(final OrderedSubmitNodeCollector instance, final Model<? super S> model, final S state, final PoseStack poseStack, final RenderType renderType, final int lightCoords, final int overlayCoords, final int color, final @Nullable UvMapping uvMapping, final int outlineColor, final Operation<Void> original, @Local(argsOnly = true, name = "itemStack") final ItemStack itemStack) {
        original.call(instance, model, state, poseStack, renderType, lightCoords, overlayCoords, color, uvMapping, outlineColor);
        if (Animatium.isEnabled() &&
                AnimatiumConfig.instance().other.damageTintArmor &&
                AnimatiumConfig.instance().other.glintAffectsArmorTint &&
                !Minecraft.getInstance().options.improvedTransparency().get() &&
                itemStack.hasFoil()) {
            original.call(instance, model, state, poseStack, AnimatiumRenderTypes.ARMOR_GLINT, lightCoords, overlayCoords, color, uvMapping, outlineColor);
        }
    }

    @ModifyExpressionValue(method = RENDER_LAYERS_TARGET, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I", opcode = Opcodes.GETSTATIC))
    private <S> int animatium$applyOverlayUV(final int original, @Local(argsOnly = true, name = "state") final S entityRenderState) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.damageTintArmor && entityRenderState instanceof LivingEntityRenderState livingEntityRenderState) {
            return OverlayTexture.pack(0, OverlayTexture.v(livingEntityRenderState.hasRedOverlay));
        } else {
            return original;
        }
    }
}