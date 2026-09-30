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
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.visuals.legacy.animatium.handler.compatibility.ModsKt;
import org.visuals.legacy.animatium.handler.config.bundle.EntryBundle;
import org.visuals.legacy.animatium.handler.config.category.Category;
import org.visuals.legacy.animatium.handler.rendering.RenderUtilsKt;
import org.visuals.legacy.animatium.handler.rendering.lighting.LegacyDiffuseLighting;
import org.visuals.legacy.animatium.util.enums.DamageTintSetting;
import org.visuals.legacy.animatium.util.enums.VoidFogSetting;

import java.awt.*;

public final class OtherConfigCategory extends Category {
    // Sky
    public boolean blueVoidSky = false;
    public boolean planarSkyFog = false;
    public boolean cloudHeight = false;
    public boolean playerVoidBox = false;
    public boolean oldCloudRendering = false;
    public VoidFogSetting voidFog = VoidFogSetting.OFF;
    // Damage Tint
    public boolean damageTintArmor = false;
    public boolean glintAffectsArmorTint = false;
    public DamageTintSetting damageTintStyle = DamageTintSetting.VANILLA;
    public Color customTintColor = new Color(1.0F, 0.0F, 0.0F, 0.3F); // Vanilla color as of 26.2
    // Other
    public boolean restoreParticleBlending = false;
    public boolean lockBlockingArmRotation = false;
    public boolean legacyBlockMiningProgress = false;
    public boolean projectileAgeCheck = false;
    public boolean blockOutlineRendering = false;
    public boolean disableModelWhilstSleeping = false;
    public boolean flameDimensions = false;
    public boolean heldItemArmLogic = false;
    public boolean thirdPersonSwordBlockingPosition = false;
    public boolean disableInventoryEntityScissor = false;
    public boolean itemGlintOnEntity = false;
    public boolean maxGlintProperties = false;
    public boolean flameOffset = false;
    public boolean persistentBlockOutline = false;
    public boolean fastGrass = false;
    public boolean oldY0Height = false;
    public boolean oldWaterOverlayOpacity = false;
    public boolean oldWaterColorFog = false;
    public boolean disableRandomBlockRotations = false;
    public boolean legacyDiffuseLighting = false;
    public boolean legacyLightmap = false;
    public boolean legacyFogDarkness = false;
    public boolean legacySplashPosition = false;

    public static ConfigCategory create(final OtherConfigCategory defaults, final OtherConfigCategory config) {
        final ConfigCategory.Builder category = ConfigCategory.createBuilder();
        category.name(Component.translatable("animatium.category.other"));
        config.bundle().install(category, defaults, config);
        return category.build();
    }

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "other");

        bundle.group("sky")
                .booleanEntry("oldCloudRendering")
                .booleanEntry("cloudHeight")
                .booleanEntry("blueVoidSky")
                .booleanEntry("playerVoidBox")
                .enumEntry("voidFog", VoidFogSetting.class)
                .booleanEntry("planarSkyFog");

        if (!ModsKt.HAS_LUNAR_CLIENT) {
            bundle.group("damage_tint")
                    .booleanEntry("damageTintArmor")
                    .booleanEntry("glintAffectsArmorTint")
                    .enumEntry("damageTintStyle", DamageTintSetting.class, (option, value) -> RenderUtilsKt.updateOverlayTint(value))
                    .colorEntry("customTintColor", (option, value) -> RenderUtilsKt.updateOverlayTint(this.damageTintStyle));
        }

        bundle.group("other")
                .booleanEntry("restoreParticleBlending")
                .booleanEntry("lockBlockingArmRotation")
                .booleanEntry("legacyBlockMiningProgress")
                .booleanEntry("projectileAgeCheck")
                .booleanEntry("blockOutlineRendering")
                .booleanEntry("disableModelWhilstSleeping")
                .booleanEntry("flameDimensions")
                .booleanEntry("heldItemArmLogic")
                .booleanEntry("thirdPersonSwordBlockingPosition")
                .booleanEntry("disableInventoryEntityScissor")
                .booleanEntry("itemGlintOnEntity")
                .booleanEntry("maxGlintProperties")
                .booleanEntry("flameOffset")
                .booleanEntry("persistentBlockOutline")
                .booleanEntry("fastGrass", (option, value) -> Minecraft.getInstance().levelExtractor.allChanged())
                .booleanEntry("oldY0Height")
                .booleanEntry("oldWaterOverlayOpacity")
                .booleanEntry("oldWaterColorFog")
                .booleanEntry("disableRandomBlockRotations")
                .booleanEntry("legacyDiffuseLighting", (option, value) -> LegacyDiffuseLighting.refresh())
                .booleanEntry("legacyLightmap")
                .booleanEntry("legacyFogDarkness")
                .booleanEntry("legacySplashPosition");

        return bundle;
    }
}
