/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * Licensed under the PolyForm Shield License 1.0.0.
 * You may obtain a copy of the license at
 * https://polyformproject.org/licenses/shield/1.0.0
 */

package org.visuals.legacy.animatium.config.category;

import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.visuals.legacy.animatium.handler.config.bundle.EntryBundle;
import org.visuals.legacy.animatium.handler.config.category.Category;
import org.visuals.legacy.animatium.util.UtilsKt;
import org.visuals.legacy.animatium.util.enums.CameraVersionSetting;

public final class ScreenConfigCategory extends Category {
    public CameraVersionSetting cameraVersion = CameraVersionSetting.VANILLA;
    public boolean crosshairInThirdPerson = false;
    public boolean disableHeartFlash = false;
    public boolean centerScrollableListWidgets = false;
    public boolean listWidgetSelectedBorderColor = false;
    public boolean legacyWidgetHoverTextColor = false;
    public boolean disableDebugHudBackground = false;
    public boolean debugHudTextShadow = false;
    public boolean disableCameraTransparentPassthrough = false;
    public boolean tooltipStyleRendering = false;
    public boolean slotHoverStyleRendering = false;
    public boolean listBackgroundGradient = false;
    public boolean inventoryEffectsPosition = false;
    public boolean fullWidthInventoryEffects = false;
    public boolean panoramaRendering = false;
    public boolean legacyLoadingScreen = false;
    public boolean oldChatPosition = false;
    public boolean oldCrosshairPosition = false;
    public boolean disconnectServerToTitleScreen = false;
    public boolean oldCraftingSlotsPosition = false;

    public static ConfigCategory create(final ScreenConfigCategory defaults, final ScreenConfigCategory config) {
        final ConfigCategory.Builder category = ConfigCategory.createBuilder();
        category.name(Component.translatable("animatium.category.screen"));
        config.bundle().install(category, defaults, config);
        return category.build();
    }

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "screen");

        bundle.enumEntry("cameraVersion", CameraVersionSetting.class);
        bundle.booleanEntry("crosshairInThirdPerson");
        bundle.booleanEntry("disableHeartFlash");
        bundle.booleanEntry("centerScrollableListWidgets");
        bundle.booleanEntry("listWidgetSelectedBorderColor");
        bundle.booleanEntry("legacyWidgetHoverTextColor");
        bundle.booleanEntry("disableDebugHudBackground");
        bundle.booleanEntry("debugHudTextShadow");
        bundle.booleanEntry("disableCameraTransparentPassthrough");
        bundle.booleanEntry("tooltipStyleRendering");
        bundle.booleanEntry("slotHoverStyleRendering");
        bundle.booleanEntry("listBackgroundGradient");
        bundle.booleanEntry("inventoryEffectsPosition");
        bundle.booleanEntry("fullWidthInventoryEffects");
        bundle.booleanEntry("panoramaRendering");
        bundle.booleanEntry("legacyLoadingScreen");
        bundle.booleanEntry("oldChatPosition");
        bundle.booleanEntry("oldCrosshairPosition");
        bundle.booleanEntry("disconnectServerToTitleScreen");
        bundle.booleanEntry("oldCraftingSlotsPosition", (option, event) -> UtilsKt.reinitializeInventorySlots());

        return bundle;
    }
}
