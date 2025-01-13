package btw.mixces.animatium.mixins.renderer;

import btw.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TextureManager.class)
public class MixinTextureManager {
    // TODO/NOTE: Do we need this when we have MixinTextureStateShard
    @ModifyVariable(method = "getTexture", at = @At("HEAD"), argsOnly = true)
    private ResourceLocation animatium$useItemGlint(ResourceLocation original) {
        if (AnimatiumConfig.instance().getForceItemGlintOnEntity() && original == ItemRenderer.ENCHANTED_GLINT_ENTITY) {
            return ItemRenderer.ENCHANTED_GLINT_ITEM;
        } else {
            return original;
        }
    }
}
