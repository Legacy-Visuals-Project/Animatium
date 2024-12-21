package me.mixces.animatium.util

import net.minecraft.client.render.entity.state.EntityRenderState
import net.minecraft.entity.Entity

abstract class EntityUtils {
    companion object {
        private val STATE_TO_ENTITY = hashMapOf<EntityRenderState, Entity>()

        @JvmStatic
        fun setEntityByState(state: EntityRenderState, entity: Entity) {
            STATE_TO_ENTITY[state] = entity
        }

        @JvmStatic
        fun getEntityByState(state: EntityRenderState): Entity? {
            return STATE_TO_ENTITY.getOrDefault(state, null)
        }
    }
}