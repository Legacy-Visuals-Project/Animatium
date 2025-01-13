package btw.mixces.animatium.mixins.renderer;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(RenderStateShard.TextureStateShard.class)
public class MixinTextureStateShard {
    // TODO/NOTE: Do we need this when we have MixinTextureManager
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/Optional;of(Ljava/lang/Object;)Ljava/util/Optional;"))
    private Optional<Object> animatium$useItemGlint(Object value, Operation<Optional<Object>> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getForceItemGlintOnEntity() && value == ItemRenderer.ENCHANTED_GLINT_ENTITY) {
            return Optional.of(ItemRenderer.ENCHANTED_GLINT_ITEM);
        } else {
            return original.call(value);
        }
    }
}
