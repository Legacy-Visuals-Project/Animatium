package btw.mixces.animatium.mixins.accessor;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderPipelines.class)
public interface RenderPipelinesAccessor {
    @Accessor("MATRICES_COLOR_SNIPPET")
    static RenderPipeline.Snippet getMatricesColorSnippet() {
        return null;
    }

    @Invoker("register")
    static RenderPipeline registerPipeline(RenderPipeline renderPipeline) {
        return null;
    }
}