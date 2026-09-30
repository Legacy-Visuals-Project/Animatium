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

package org.visuals.legacy.animatium.handler.compatibility

import net.fabricmc.loader.api.FabricLoader

@JvmField
val HAS_VFP = FabricLoader.getInstance().isModLoaded("viafabricplus")

@JvmField
val HAS_SODIUM_EXTRAS = FabricLoader.getInstance().isModLoaded("sodium-extra")

@JvmField
val HAS_LUNAR_CLIENT = FabricLoader.getInstance().isModLoaded("ichor")