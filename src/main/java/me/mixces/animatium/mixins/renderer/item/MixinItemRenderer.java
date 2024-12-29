package me.mixces.animatium.mixins.renderer.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
    @WrapOperation(method = "renderBakedItemModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModel;getQuads(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/random/Random;)Ljava/util/List;", ordinal = 1))
    private static List<BakedQuad> animatium$itemDrops2D(BakedModel instance, BlockState state, Direction direction, Random random, Operation<List<BakedQuad>> original) {
        List<BakedQuad> quads = original.call(instance, state, direction, random);
        if (animatium$isTransformationModeValid() && !instance.hasDepth()) {
            return quads.stream().filter(baked -> baked.getFace() == Direction.SOUTH).collect(Collectors.toList());
        } else {
            return quads;
        }
    }

    @Unique
    private static boolean animatium$isTransformationModeValid() {
        ModelTransformationMode modelTransformationMode = ItemUtils.getTransformMode();
        boolean itemDrops2D = AnimatiumConfig.getInstance().getItemDrops2D();
        boolean itemFramed2D = AnimatiumConfig.getInstance().getItemFramed2D();

        // TODO: Separate dropped items from thrown items
        return (itemDrops2D && modelTransformationMode == ModelTransformationMode.GROUND) ||
                (itemFramed2D && modelTransformationMode == ModelTransformationMode.FIXED);
    }
}
