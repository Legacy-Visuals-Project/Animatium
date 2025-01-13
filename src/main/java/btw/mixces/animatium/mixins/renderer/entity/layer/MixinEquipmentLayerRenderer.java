package btw.mixces.animatium.mixins.renderer.entity.layer;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.EntityUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EquipmentLayerRenderer.class)
public abstract class MixinEquipmentLayerRenderer {
    // TODO: Fix lunar client

    @WrapOperation(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorCutoutNoCull(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private RenderType animatium$renderLayerArmorTint(ResourceLocation resourceLocation, Operation<RenderType> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getEntityArmorHurtTint()) {
            return RenderType.entityCutoutNoCullZOffset(resourceLocation);
        } else {
            return original.call(resourceLocation);
        }
    }

    @WrapOperation(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;wrap(Lcom/mojang/blaze3d/vertex/VertexConsumer;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer animatium$renderLayerArmorTrimTint(TextureAtlasSprite instance, VertexConsumer consumer, Operation<VertexConsumer> original, @Local(argsOnly = true) MultiBufferSource multiBufferSource) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getEntityArmorHurtTint()) {
            return instance.wrap(multiBufferSource.getBuffer(RenderType.entityCutoutNoCullZOffset(instance.atlasLocation())));
        } else {
            return original.call(instance, consumer);
        }
    }

    @ModifyArg(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"), index = 3)
    private int animatium$modifyUVArmorTint(int light) {
        return animatium$getPackUv(light);
    }

    @ModifyArg(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"), index = 3)
    private int animatium$modifyUVTrimTint(int light) {
        return animatium$getPackUv(light);
    }

    @WrapOperation(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/ResourceLocation;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void animatium$oldArmorHurtRendering(Model instance, PoseStack poseStack, VertexConsumer vertexConsumer, int light, int overlay, int color, Operation<Void> original) {
        original.call(instance, poseStack, vertexConsumer, light, overlay, color);
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getEntityArmorHurtTint() && AnimatiumConfig.instance().getOldArmorHurtRendering()) {
            HumanoidRenderState humanRenderState = EntityUtils.getHumanRenderState();
            Level level = Minecraft.getInstance().level;
            if (humanRenderState == null || level == null) {
                return;
            }

            // TODO: Too strong? & glint needs to be tinted hurt color
            boolean isHurt = humanRenderState.hasRedOverlay;
            if (isHurt) {
                // Code sourced from 1.7/1.8
                Minecraft.getInstance().gameRenderer.lightTexture().turnOffLightLayer();
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(770, 771);
                RenderSystem.depthFunc(514);
                original.call(instance, poseStack, vertexConsumer, light, overlay, color);
                RenderSystem.depthFunc(515);
                RenderSystem.disableBlend();
                Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
            }
        }
    }

    @Unique
    private int animatium$getPackUv(int original) {
        HumanoidRenderState humanRenderState = EntityUtils.getHumanRenderState();
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getEntityArmorHurtTint() && humanRenderState != null) {
            return OverlayTexture.pack(OverlayTexture.u(0.0F), OverlayTexture.v(humanRenderState.hasRedOverlay));
        } else {
            return original;
        }
    }
}