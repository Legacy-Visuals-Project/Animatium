package btw.mixces.animatium.config

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import net.minecraft.client.gui.screens.Screen

class ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent: Screen? -> AnimatiumConfig.getConfigScreen(parent) }
    }
}