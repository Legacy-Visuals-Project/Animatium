package btw.mixces.animatium.mixins.renderer;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import btw.mixces.animatium.util.RenderUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.CompiledShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CompiledShaderProgram.class)
public abstract class MixinCompiledShaderProgram {
    @WrapOperation(method = "setDefaultUniforms", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getShaderLineWidth()F"))
    private float animatium$legacyBlockOutlineRendering$lineWidth(Operation<Float> original) {
        return RenderUtils.getLineWidth(original.call());
    }

    @ModifyArg(method = "setDefaultUniforms", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/shaders/Uniform;set(F)V", ordinal = 0))
    private float animatium$forceMaxGlintStrength(float original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getForceMaxGlintProperties()) {
            // 100% glint strength
            return 1.0F;
        } else {
            return original;
        }
    }
}
