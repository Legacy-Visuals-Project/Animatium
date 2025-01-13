package btw.mixces.animatium.mixins.renderer.item;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.ItemUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
    @WrapOperation(method = "renderModelLists", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/BakedModel;getQuads(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/util/RandomSource;)Ljava/util/List;", ordinal = 1))
    private static List<BakedQuad> animatium$itemDrops2D(BakedModel instance, BlockState state, Direction direction, RandomSource random, Operation<List<BakedQuad>> original) {
        List<BakedQuad> quads = original.call(instance, state, direction, random);
        if (AnimatiumClient.getEnabled() && animatium$isTransformationModeValid() && !instance.isGui3d()) {
            return quads.stream().filter(baked -> baked.getDirection() == Direction.SOUTH).collect(Collectors.toList());
        } else {
            return quads;
        }
    }

    @Unique
    private static boolean animatium$isTransformationModeValid() {
        ItemDisplayContext displayContext = ItemUtils.getDisplayContext();
        boolean itemDrops2D = AnimatiumConfig.instance().getItemDrops2D();
        boolean itemFramed2D = AnimatiumConfig.instance().getItemFramed2D();
        return (itemDrops2D && displayContext == ItemDisplayContext.GROUND) || (itemFramed2D && displayContext == ItemDisplayContext.FIXED);
    }
}
