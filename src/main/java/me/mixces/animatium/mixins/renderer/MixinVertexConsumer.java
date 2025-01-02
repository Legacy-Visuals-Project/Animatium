package me.mixces.animatium.mixins.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(VertexConsumer.class)
public interface MixinVertexConsumer {
    // TODO: this is only half of the battle + framed item 2d colors are disabled
    @ModifyArgs(method = "putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;[FFFFF[IIZ)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;transformNormal(FFFLorg/joml/Vector3f;)Lorg/joml/Vector3f;"))
    default void animatium$item2DColors(Args args) {
        ItemStackRenderState state = ItemUtils.getRenderState();
        if (AnimatiumConfig.getInstance().getItem2DColors() && state != null && !state.isGui3d()) {
            ItemDisplayContext displayContext = ItemUtils.getDisplayContext();
            if (displayContext == ItemDisplayContext.GROUND) {
                args.set(1, (float) args.get(2));
                args.set(2, (float) args.get(1));
            }
        }
    }
}
