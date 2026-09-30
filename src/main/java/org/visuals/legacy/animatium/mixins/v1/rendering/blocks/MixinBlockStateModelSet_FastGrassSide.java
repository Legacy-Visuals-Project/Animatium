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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockStateModelSet;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.AnimatiumConstants;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(BlockStateModelSet.class)
public abstract class MixinBlockStateModelSet_FastGrassSide {
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void animatium$fastGrass(final BlockState state, final CallbackInfoReturnable<BlockStateModel> cir) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.fastGrass && (state.is(Blocks.GRASS_BLOCK) && !state.getValue(GrassBlock.SNOWY))) {
            cir.setReturnValue(Minecraft.getInstance().getModelManager().getModel(AnimatiumConstants.FAST_GRASS_MODEL_KEY));
        }
    }
}
