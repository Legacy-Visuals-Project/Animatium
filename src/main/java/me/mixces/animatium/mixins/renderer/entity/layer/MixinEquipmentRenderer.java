package me.mixces.animatium.mixins.renderer.entity.layer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.mixces.animatium.config.AnimatiumConfig;
import me.mixces.animatium.util.EntityUtils;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EquipmentRenderer.class)
public abstract class MixinEquipmentRenderer {
    @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer animatium$renderLayerArmorTint(Identifier texture, Operation<RenderLayer> original) {
        if (AnimatiumConfig.getInstance().getEntityArmorHurtTint()) {
            return RenderLayer.getEntityCutoutNoCullZOffset(texture);
        } else {
            return original.call(texture);
        }
    }

    @WrapOperation(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/Sprite;getTextureSpecificVertexConsumer(Lnet/minecraft/client/render/VertexConsumer;)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer animatium$renderLayerArmorTrimTint(Sprite instance, VertexConsumer consumer, Operation<VertexConsumer> original, @Local(argsOnly = true) VertexConsumerProvider vertexConsumers) {
        if (AnimatiumConfig.getInstance().getEntityArmorHurtTint()) {
            return instance.getTextureSpecificVertexConsumer(vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(instance.getAtlasId())));
        } else {
            return original.call(instance, consumer);
        }
    }

    @ModifyArg(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"), index = 3)
    private int animatium$modifyUVArmorTint(int light) {
        return animatium$getPackUv(light);
    }

    @Unique
    private int animatium$getPackUv(int original) {
        BipedEntityRenderState bipedEntityRenderState = EntityUtils.getBipedEntityRenderState();
        if (AnimatiumConfig.getInstance().getEntityArmorHurtTint() && bipedEntityRenderState != null) {
            return OverlayTexture.packUv(OverlayTexture.getU(0.0F), OverlayTexture.getV(bipedEntityRenderState.hurt));
        } else {
            return original;
        }
    }
}