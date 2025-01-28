package btw.mixces.animatium.mixins.model.item;

import btw.mixces.animatium.util.ItemUtils;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public abstract class MixinItemModelResolver {
    @Inject(method = "appendItemLayers", at = @At("HEAD"))
    private void animatium$setItemUtils(ItemStackRenderState stackRenderState, ItemStack stack, ItemDisplayContext displayContext, Level level, LivingEntity livingEntity, int i, CallbackInfo ci) {
        ItemUtils.set(stackRenderState, stack, displayContext);
    }
}
