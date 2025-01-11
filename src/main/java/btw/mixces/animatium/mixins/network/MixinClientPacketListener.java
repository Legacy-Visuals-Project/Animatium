package btw.mixces.animatium.mixins.network;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Objects;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {
    @Shadow
    private ClientLevel level;

    @WrapOperation(method = "handleSetEntityData", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;packedItems()Ljava/util/List;"))
    public List<SynchedEntityData.DataValue<?>> animatium$disableServerPoseAndBlockingVisualUpdates(ClientboundSetEntityDataPacket instance, Operation<List<SynchedEntityData.DataValue<?>>> original) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getDisableServerPoseAndBlockingVisualUpdates()) {
            if (Objects.requireNonNull(Minecraft.getInstance().player).equals(this.level.getEntity(instance.id()))) {
                instance.packedItems().removeIf(entry -> entry.id() == 8 /* Blocking update? */ || entry.serializer().equals(EntityDataSerializers.POSE));
            }
        }

        return original.call(instance);
    }

    @WrapWithCondition(method = "handleContainerClose", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;clientSideCloseContainer()V"))
    private boolean animatium$dontCloseChat$containerClose(LocalPlayer instance) {
        return !AnimatiumClient.getEnabled() || !AnimatiumConfig.instance().getDontCloseChat() || !(Minecraft.getInstance().screen instanceof ChatScreen);
    }
}
