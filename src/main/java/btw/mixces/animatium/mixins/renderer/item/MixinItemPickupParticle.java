package btw.mixces.animatium.mixins.renderer.item;

import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemPickupParticle.class)
public class MixinItemPickupParticle {
    @Shadow
    @Final
    private Entity target;

    @ModifyExpressionValue(method = "updatePosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeY()D"))
    private double animatium$oldItemPickupPosition(double original) {
        if (AnimatiumConfig.instance().getOldItemPickupPosition()) {
            return this.target.position().y;
        } else {
            return original;
        }
    }
}
