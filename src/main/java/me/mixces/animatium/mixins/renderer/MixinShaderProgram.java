package me.mixces.animatium.mixins.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.mixces.animatium.util.RenderUtils;
import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShaderProgram.class)
public abstract class MixinShaderProgram {
    @WrapOperation(method = "initializeUniforms", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getShaderLineWidth()F"))
    private float animatium$legacyBlockOutlineRendering$lineWidth(Operation<Float> original) {
        return RenderUtils.getLineWidth(original.call());
    }
}
