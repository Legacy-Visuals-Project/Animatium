package me.mixces.animatium.mixins.renderer.item;

import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.ItemUtils;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.item.ModelTransformationMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(VertexConsumer.class)
public interface MixinVertexConsumer {

    // TODO: this is only half of the battle
    @ModifyArgs(method = "quad(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/model/BakedQuad;[FFFFF[IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack$Entry;transformNormal(FFFLorg/joml/Vector3f;)Lorg/joml/Vector3f;"))
    default void animatium$item2DColors(Args args) {
        if (AnimatiumConfig.getInstance().getItem2DColors()) {
            ModelTransformationMode transformationMode = ItemUtils.getTransformMode();
            if (transformationMode == ModelTransformationMode.GROUND || transformationMode == ModelTransformationMode.FIXED) {
                args.set(1, (float) args.get(2));
                args.set(2, (float) args.get(1));
            }
        }
    }
}
