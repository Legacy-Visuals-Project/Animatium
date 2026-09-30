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

package org.visuals.legacy.animatium.handler.networking

import net.fabricmc.fabric.api.client.networking.v1.*
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import org.visuals.legacy.animatium.AnimatiumConstants
import org.visuals.legacy.animatium.handler.networking.payloads.InfoPayload
import org.visuals.legacy.animatium.handler.networking.payloads.SetServerFeaturesPayload
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager

object AnimatiumNetworking {
    fun bootstrap() {
        ClientLoginConnectionEvents.DISCONNECT.register { _, _ -> ServerFeatureManager.ENABLED_SERVER_FEATURES.clear() }
        ClientConfigurationConnectionEvents.DISCONNECT.register { _, _ -> ServerFeatureManager.ENABLED_SERVER_FEATURES.clear() }
        ClientPlayConnectionEvents.DISCONNECT.register { _, _ -> ServerFeatureManager.ENABLED_SERVER_FEATURES.clear() }

        PayloadTypeRegistry.serverboundPlay()
            .register(InfoPayload.TYPE, InfoPayload.STREAM_CODEC)
        ServerPlayNetworking.registerGlobalReceiver(InfoPayload.TYPE) { _, _ -> /* NO-OP */ }
        ClientPlayConnectionEvents.JOIN.register { _, sender, _ ->
            if (ClientPlayNetworking.canSend(InfoPayload.TYPE)) {
                sender.sendPacket(AnimatiumConstants.INFO_PAYLOAD)
            }
        }

        PayloadTypeRegistry.clientboundConfiguration()
            .register(SetServerFeaturesPayload.TYPE, SetServerFeaturesPayload.STREAM_CODEC)
        ClientConfigurationNetworking.registerGlobalReceiver(SetServerFeaturesPayload.TYPE) { payload, context ->
            context.client().schedule {
                ServerFeatureManager.ENABLED_SERVER_FEATURES.clear()
                ServerFeatureManager.ENABLED_SERVER_FEATURES.addAll(payload.features)
            }
        }

        PayloadTypeRegistry.clientboundPlay()
            .register(SetServerFeaturesPayload.TYPE, SetServerFeaturesPayload.STREAM_CODEC)
        ClientPlayNetworking.registerGlobalReceiver(SetServerFeaturesPayload.TYPE) { payload, context ->
            context.client().schedule {
                ServerFeatureManager.ENABLED_SERVER_FEATURES.clear()
                ServerFeatureManager.ENABLED_SERVER_FEATURES.addAll(payload.features)
            }
        }
    }
}