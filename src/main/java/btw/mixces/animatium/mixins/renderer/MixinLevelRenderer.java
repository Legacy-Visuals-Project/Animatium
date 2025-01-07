package btw.mixces.animatium.mixins.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.mixins.accessor.SkyRendererAccessor;
import btw.mixces.animatium.util.MathUtils;
import btw.mixces.animatium.util.RenderUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {
    // TODO: Iris functionality/support?
    // TODO: Make blue void work in FabricSkyBoxes/Nuit

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Nullable
    private ClientLevel level;

    @Shadow
    @Final
    private SkyRenderer skyRenderer;

    @Inject(method = "method_62215", at = @At(value = "TAIL"))
    private void animatium$oldBlueVoidSky(FogParameters fog, DimensionSpecialEffects.SkyType skyType, float tickDelta, DimensionSpecialEffects dimensionSpecialEffects, CallbackInfo ci, @Local PoseStack poseStack) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldBlueVoidSky() && skyType != DimensionSpecialEffects.SkyType.END) {
            assert this.minecraft.player != null;
            assert this.level != null;
            // can't get it via local, so have to re-get it this way
            int skyColor = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), tickDelta);
            this.animatium$renderSkyBlueVoid(poseStack, skyColor, this.minecraft.player.getEyePosition(tickDelta).y - RenderUtils.getLevelHorizonHeight(this.level));
        }
    }

    @WrapOperation(method = "shouldRenderDarkDisc", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel$ClientLevelData;getHorizonHeight(Lnet/minecraft/world/level/LevelHeightAccessor;)D"))
    private double animatium$oldSkyHorizonHeight(ClientLevel.ClientLevelData instance, LevelHeightAccessor levelHeightAccessor, Operation<Double> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldSkyHorizonHeight() && this.level != null) {
            return RenderUtils.getLevelHorizonHeight(this.level);
        } else {
            return original.call(instance, levelHeightAccessor);
        }
    }

    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/DimensionSpecialEffects;getCloudHeight()F"))
    private float animatium$oldCloudHeight(DimensionSpecialEffects instance, Operation<Float> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldCloudHeight()) {
            return instance.skyType() == DimensionSpecialEffects.SkyType.END ? 8.0F : 128.0F;
        } else {
            return original.call(instance);
        }
    }

    @Unique
    private void animatium$renderSkyBlueVoid(PoseStack poseStack, int skyColor, double depth) {
        CompiledShaderProgram shaderProgram = RenderSystem.setShader(CoreShaders.POSITION);

        assert this.level != null;
        Vector3f skyColorVec = ARGB.vector3fFromRGB24(skyColor);
        if (this.level.effects().hasGround()) {
            RenderSystem.setShaderColor(skyColorVec.x * 0.2F + 0.04F, skyColorVec.y * 0.2F + 0.04F, skyColorVec.z * 0.6F + 0.1F, 1.0F);
        } else {
            RenderSystem.setShaderColor(skyColorVec.x, skyColorVec.y, skyColorVec.z, 1.0F);
        }

        poseStack.pushPose();
        poseStack.mulPose(RenderSystem.getModelViewMatrix());
        poseStack.translate(0.0F, -((float) (depth - 16.0)), 0.0F);
        SkyRendererAccessor skyRendererAccessor = (SkyRendererAccessor) this.skyRenderer;
        skyRendererAccessor.getBottomSkyBuffer().bind();
        skyRendererAccessor.getBottomSkyBuffer().drawWithShader(poseStack.last().pose(), RenderSystem.getProjectionMatrix(), shaderProgram);
        VertexBuffer.unbind();
        poseStack.popPose();

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Inject(method = "renderBlockOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)V", shift = At.Shift.BEFORE))
    private void animatium$setBlockOutlineWidth$on(Camera camera, MultiBufferSource.BufferSource bufferSource, PoseStack poseStack, boolean bl, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getLegacyBlockOutlineRendering()) {
            RenderUtils.setLineWidth(2.0F);
        }
    }

    @Inject(method = "renderBlockOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)V", shift = At.Shift.BEFORE))
    private void animatium$setBlockOutlineWidth$off(Camera camera, MultiBufferSource.BufferSource bufferSource, PoseStack poseStack, boolean bl, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getLegacyBlockOutlineRendering()) {
            RenderUtils.setLineWidth(-1.0F);
        }
    }

    @WrapOperation(method = "renderHitOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    private VoxelShape animatium$legacyBlockOutlineRendering(BlockState instance, BlockGetter blockView, BlockPos blockPos, CollisionContext collisionContext, Operation<VoxelShape> original) {
        VoxelShape shape = original.call(instance, blockView, blockPos, collisionContext);
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getLegacyBlockOutlineRendering()) {
            return MathUtils.expandVoxelShape(shape, 0.0020000000949949026F);
        } else {
            return shape;
        }
    }
}