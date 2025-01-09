package btw.mixces.animatium.mixins.renderer;

import btw.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RenderStateShard.TextureStateShard.class)
public class MixinTextureStateShard {
    @ModifyVariable(method = "<init>", at = @At(value = "LOAD"), argsOnly = true)
    private static ResourceLocation animatium$useItemGlint$init(ResourceLocation original) {
        if (AnimatiumConfig.instance().getForceItemGlintOnEntity() && original == ItemRenderer.ENCHANTED_GLINT_ENTITY) {
            return ItemRenderer.ENCHANTED_GLINT_ITEM;
        } else {
            return original;
        }
    }
}
