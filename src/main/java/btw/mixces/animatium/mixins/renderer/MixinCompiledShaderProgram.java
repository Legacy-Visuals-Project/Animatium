package btw.mixces.animatium.mixins.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import btw.mixces.animatium.util.RenderUtils;
import net.minecraft.client.renderer.CompiledShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CompiledShaderProgram.class)
public abstract class MixinCompiledShaderProgram {
    @WrapOperation(method = "setDefaultUniforms", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getShaderLineWidth()F"))
    private float animatium$legacyBlockOutlineRendering$lineWidth(Operation<Float> original) {
        return RenderUtils.getLineWidth(original.call());
    }
}
