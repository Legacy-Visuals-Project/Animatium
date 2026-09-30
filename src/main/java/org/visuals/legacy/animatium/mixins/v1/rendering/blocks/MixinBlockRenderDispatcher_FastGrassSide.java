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

package org.visuals.legacy.animatium.mixins.v1.rendering.blocks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.AnimatiumConstants;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(BlockRenderDispatcher.class)
public abstract class MixinBlockRenderDispatcher_FastGrassSide {
    @Shadow
    @Final
    private BlockModelShaper blockModelShaper;

    @WrapOperation(method = "getBlockModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockModelShaper;getBlockModel(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/client/renderer/block/model/BlockStateModel;"))
    private BlockStateModel animatium$fastGrass(final BlockModelShaper instance, final BlockState state, final Operation<BlockStateModel> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.fastGrass && (state.is(Blocks.GRASS_BLOCK) && !state.getValue(GrassBlock.SNOWY))) {
            return this.blockModelShaper.getModelManager().getModel(AnimatiumConstants.FAST_GRASS_MODEL_KEY);
        } else {
            return original.call(instance, state);
        }
    }
}
