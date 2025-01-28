package btw.mixces.animatium.mixins.accessor;

import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SkyRenderer.class)
public interface SkyRendererAccessor {
    @Final
    @Accessor("bottomSkyBuffer")
    VertexBuffer getBottomSkyBuffer();
}
