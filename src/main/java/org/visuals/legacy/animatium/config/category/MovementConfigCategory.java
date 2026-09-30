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
import org.visuals.legacy.animatium.util.enums.SneakAnimationSetting;

public final class MovementConfigCategory extends Category {
    // (Movement) Cape
    public boolean oldCapeMovement = false;
    public boolean disableCapeLean = false;
    public boolean disableCapeSwingRotation = false;
    public boolean capeChestplateTranslation = false;
    public boolean capeSneakPosition = false;
    // (Movement) Other
    public SneakAnimationSetting sneakAnimation = SneakAnimationSetting.VANILLA;
    public boolean longUnsneak = false;
    public boolean fakeOldSneakEyeHeight = false;
    public boolean rotateBackwardsWalking = false;
    public boolean uncapBlockingHeadRotation = false;
    public boolean disableHeadRotationInterpolation = false;
    public boolean handViewBobbingMovement = false;
    public boolean deathLimbs = false;
    public boolean bowArmMovement = false;
    public boolean legacyDamageTilt = false;
    public boolean offsetHurtTiltTime = false;

    public static ConfigCategory create(final MovementConfigCategory defaults, final MovementConfigCategory config) {
        final ConfigCategory.Builder category = ConfigCategory.createBuilder();
        category.name(Component.translatable("animatium.category.movement"));
        config.bundle().install(category, defaults, config);
        return category.build();
    }

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "movement");

        bundle.group("cape")
                .booleanEntry("oldCapeMovement")
                .booleanEntry("disableCapeLean")
                .booleanEntry("disableCapeSwingRotation")
                .booleanEntry("capeChestplateTranslation")
                .booleanEntry("capeSneakPosition");

        bundle.group("other")
                .enumEntry("sneakAnimation", SneakAnimationSetting.class)
                .booleanEntry("longUnsneak")
                .booleanEntry("fakeOldSneakEyeHeight")
                .booleanEntry("rotateBackwardsWalking")
                .booleanEntry("uncapBlockingHeadRotation")
                .booleanEntry("disableHeadRotationInterpolation")
                .booleanEntry("handViewBobbingMovement")
                .booleanEntry("deathLimbs")
                .booleanEntry("bowArmMovement")
                .booleanEntry("legacyDamageTilt")
                .booleanEntry("offsetHurtTiltTime");

        return bundle;
    }
}
