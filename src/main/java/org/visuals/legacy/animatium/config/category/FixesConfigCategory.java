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
import org.visuals.legacy.animatium.handler.compatibility.ModsKt;
import org.visuals.legacy.animatium.handler.config.bundle.EntryBundle;
import org.visuals.legacy.animatium.handler.config.category.Category;

public final class FixesConfigCategory extends Category {
    public boolean fixSneakingFeetPosition = true;
    public boolean fixMirrorArmSwing = true;
    public boolean fixOffHandUsingPose = true;
    public boolean fixCastLineCheck = true;
    public boolean fixCastLineSwing = true;
    public boolean fixFireballClientsideVisual = true;
    public boolean fixTextStrikethroughStyle = true;
    public boolean fixHighAttackSpeedIndicator = true;
    public boolean fixVerticalBobbingTilt = true;
    public boolean upMinPixelTransparencyLimit = true;
    public boolean fixEquipAnimationOnItemUse = true;
    public boolean fixItemUsageVisualInGUI = true;
    public boolean fixDoubleUsageVisual = true;
    public boolean oldSkyRenderingCheck = true;
    public boolean smoothParticles = true;

    public static ConfigCategory create(final FixesConfigCategory defaults, final FixesConfigCategory config) {
        final ConfigCategory.Builder category = ConfigCategory.createBuilder();
        category.name(Component.translatable("animatium.category.fixes"));
        config.bundle().install(category, defaults, config);
        return category.build();
    }

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "fixes");

        bundle.booleanEntry("fixSneakingFeetPosition");
        bundle.booleanEntry("fixMirrorArmSwing");
        bundle.booleanEntry("fixOffHandUsingPose");
        bundle.booleanEntry("fixCastLineCheck");
        bundle.booleanEntry("fixCastLineSwing");
        bundle.booleanEntry("fixFireballClientsideVisual");
        if (!ModsKt.HAS_VFP) {
            bundle.booleanEntry("fixTextStrikethroughStyle");
        }

        bundle.booleanEntry("fixHighAttackSpeedIndicator");
        bundle.booleanEntry("fixVerticalBobbingTilt");
        bundle.booleanEntry("upMinPixelTransparencyLimit");
        bundle.booleanEntry("fixEquipAnimationOnItemUse");
        bundle.booleanEntry("fixItemUsageVisualInGUI");
        bundle.booleanEntry("fixDoubleUsageVisual");
        bundle.booleanEntry("oldSkyRenderingCheck");
        bundle.booleanEntry("smoothParticles");

        return bundle;
    }
}
