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

package org.visuals.legacy.animatium.mixins.v1.entity.fishing;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.util.enums.FishingRodVersionSetting;

@Mixin(ItemModelResolver.class)
public abstract class MixinItemModelResolver {
    @Inject(method = "appendItemLayers", at = @At("HEAD"))
    private void animatium$storeItemStack(final ItemStackRenderState output, final ItemStack item, final ItemDisplayContext displayContext, final Level level, final ItemOwner owner, final int seed, final CallbackInfo ci) {
        output.animatium$setItemStack(item);
    }

    @WrapOperation(method = "appendItemLayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"))
    private Object animatium$stickModelWhenCastInThirdPerson(
            final ItemStack instance,
            final DataComponentType<?> dataComponentType,
            final Operation<Object> original,
            @Local(argsOnly = true, name = "displayContext") final ItemDisplayContext displayContext,
            @Local(argsOnly = true, name = "owner") final ItemOwner owner,
            @Local(argsOnly = true, name = "item") final ItemStack item
    ) {
        final LivingEntity livingEntity = owner == null ? null : owner.asLivingEntity();
        // TODO/FIX
        if (Animatium.isEnabled() &&
                AnimatiumConfig.instance().items.fishingRodVersion == FishingRodVersionSetting.V1_7 &&
                item.getItem() == Items.FISHING_ROD &&
                (livingEntity instanceof Player player && player.fishing != null) &&
                ((displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND && livingEntity.getOffhandItem() == item) ||
                        (displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND && livingEntity.getMainHandItem() == item))) {
            return Identifier.withDefaultNamespace("stick");
        } else {
            return original.call(instance, dataComponentType);
        }
    }
}
