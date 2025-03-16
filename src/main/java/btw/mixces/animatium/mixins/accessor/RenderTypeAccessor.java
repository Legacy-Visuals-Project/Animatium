package btw.mixces.animatium.mixins.accessor;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderType.class)
public interface RenderTypeAccessor  {
    @Invoker("create")
    static RenderType.CompositeRenderType createRenderType(String string, int size, RenderPipeline renderPipeline, RenderType.CompositeState compositeState) {
        return null;
    }
}
