package btw.mixces.animatium.mixins.network;

import btw.mixces.animatium.AnimatiumClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.networking.RegistrationPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RegistrationPayload.class)
public abstract class MixinRegistrationPayload {
    @WrapOperation(method = "addId", at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;parse(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private static ResourceLocation animatium$register(String id, Operation<ResourceLocation> original) {
        // TODO/NOTE: This is a hack I think, only way I could figure it out for now.
        if (id.equals("Animatium")) {
            Minecraft.getInstance().execute(() -> ClientPlayNetworking.send(AnimatiumClient.getInfoPayload()));
        }

        return original.call(id);
    }
}