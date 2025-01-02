package me.mixces.animatium.util

import net.minecraft.client.render.entity.state.BipedEntityRenderState
import net.minecraft.client.render.entity.state.EntityRenderState
import net.minecraft.client.render.item.ItemRenderState
import net.minecraft.entity.Entity

object EntityUtils {
    private val BIPED_ENTITY_RENDER_STATE: ThreadLocal<BipedEntityRenderState?> = ThreadLocal.withInitial { null }
    private val STATE_TO_ENTITY = hashMapOf<EntityRenderState, Entity>()

    @JvmStatic
    fun setEntityByState(state: EntityRenderState, entity: Entity) {
        STATE_TO_ENTITY[state] = entity
    }

    @JvmStatic
    fun getEntityByState(state: EntityRenderState): Entity? {
        return STATE_TO_ENTITY.getOrDefault(state, null)
    }

    @JvmStatic
    fun setBipedEntityRenderState(state: BipedEntityRenderState) {
        BIPED_ENTITY_RENDER_STATE.set(state)
    }

    @JvmStatic
    fun removeBipedEntityRenderState() {
        BIPED_ENTITY_RENDER_STATE.remove()
    }

    @JvmStatic
    fun getBipedEntityRenderState(): BipedEntityRenderState? {
        return BIPED_ENTITY_RENDER_STATE.get()
    }
}