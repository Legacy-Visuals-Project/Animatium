package btw.mixces.animatium.mixins.renderer.item;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemModelResolver.class)
public abstract class MixinItemModelResolver {
    @WrapOperation(method = "appendItemLayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;get(Lnet/minecraft/core/component/DataComponentType;)Ljava/lang/Object;"))
    private Object animatium$useStickModelWhenCastInThirdperson(ItemStack instance, DataComponentType<ResourceLocation> dataComponentType, Operation<ResourceLocation> original, @Local(argsOnly = true) ItemDisplayContext displayContext, @Local(argsOnly = true) LivingEntity livingEntity, @Local(argsOnly = true) ItemStack itemStack) {
        if (AnimatiumClient.getEnabled() &&
                AnimatiumConfig.instance().getUseStickModelWhenCastInThirdperson() &&
                itemStack.getItem() == Items.FISHING_ROD &&
                (livingEntity instanceof Player player && player.fishing != null) &&
                ((displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND && livingEntity.getOffhandItem() == itemStack) ||
                        (displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND && livingEntity.getMainHandItem() == itemStack))) {
            return ResourceLocation.withDefaultNamespace("stick");
        } else {
            return original.call(instance, dataComponentType);
        }
    }
}
