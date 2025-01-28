package btw.mixces.animatium.mixins.screen.components;

import btw.mixces.animatium.AnimatiumClient;
import btw.mixces.animatium.config.AnimatiumConfig;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AbstractButton.class)
public abstract class MixinAbstractButton extends AbstractWidget {
    public MixinAbstractButton(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @ModifyConstant(method = "renderWidget", constant = @Constant(intValue = 0xFFFFFF))
    private int renderWidget$old$textColor(int constant) {
        if (AnimatiumClient.getEnabled() && AnimatiumConfig.instance().getOldButtonTextColors()) {
            return !active ? 0xE0E0E0 : (isHoveredOrFocused() ? 0xFFFFA0 : 0xE0E0E0);
        } else {
            return constant;
        }
    }
}
