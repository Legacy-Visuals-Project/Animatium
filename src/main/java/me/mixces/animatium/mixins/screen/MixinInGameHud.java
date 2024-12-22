package me.mixces.animatium.mixins.screen;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @WrapOperation(method = "renderChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;render(Lnet/minecraft/client/gui/DrawContext;IIIZ)V"))
    private void animatium$oldChatPosition(ChatHud instance, DrawContext context, int currentTick, int mouseX, int mouseY, boolean focused, Operation<Void> original, @Local Window window) {
        if (AnimatiumConfig.getInstance().getOldChatPosition()) {
            context.getMatrices().translate(0F, 12F, 0F);
        }

        original.call(instance, context, currentTick, mouseX, mouseY, focused);
        if (AnimatiumConfig.getInstance().getOldChatPosition()) {
            context.getMatrices().translate(0F, -12F, 0F);
        }
    }

    @WrapOperation(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
    private boolean animatium$showCrosshairInThirdperson(Perspective instance, Operation<Boolean> original) {
        if (AnimatiumConfig.getInstance().getShowCrosshairInThirdperson()) {
            return true;
        } else {
            return original.call(instance);
        }
    }

    @WrapWithCondition(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 2))
    private boolean animatium$fixHighAttackSpeedIndicator(DrawContext instance, Function<Identifier, RenderLayer> renderLayers, Identifier sprite, int x, int y, int width, int height, @Local float f) {
        if (AnimatiumConfig.getInstance().getFixHighAttackSpeedIndicator()) {
            // NOTE: Couldn't grab it locally, so just copied it. Should be fine.
            int progressWidth = (int) (f * 17.0F);
            return progressWidth != 0;
        } else {
            return true;
        }
    }

    @WrapWithCondition(method = "renderHealthBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIZZZ)V"))
    private boolean animatium$removeHeartFlash(InGameHud instance, DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half) {
        return !AnimatiumConfig.getInstance().getRemoveHeartFlash() || !blinking || type == InGameHud.HeartType.CONTAINER;
    }
}
