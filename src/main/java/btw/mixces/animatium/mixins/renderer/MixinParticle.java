package btw.mixces.animatium.mixins.renderer;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Particle.class)
public abstract class MixinParticle {
    @Shadow
    public abstract AABB getBoundingBox();

    @Shadow
    public abstract void setBoundingBox(AABB aABB);

    @Shadow
    protected abstract void setLocationFromBoundingbox();

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void animatium$disableParticlePhysics(double x, double y, double z, CallbackInfo ci) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDisableParticlePhysics()) {
            ci.cancel();
            setBoundingBox(this.getBoundingBox().move(x, y, z));
            this.setLocationFromBoundingbox();
        }
    }
}
