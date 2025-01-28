package btw.mixces.animatium.util

import net.minecraft.client.renderer.entity.state.EntityRenderState
import net.minecraft.client.renderer.entity.state.HumanoidRenderState
import net.minecraft.world.entity.Entity

object EntityUtils {
    private val HUMAN_RENDER_STATE: ThreadLocal<HumanoidRenderState?> = ThreadLocal.withInitial { null }
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
    fun setHumanRenderState(state: HumanoidRenderState) {
        HUMAN_RENDER_STATE.set(state)
    }

    @JvmStatic
    fun removeHumanRenderState() {
        HUMAN_RENDER_STATE.remove()
    }

    @JvmStatic
    fun getHumanRenderState(): HumanoidRenderState? {
        return HUMAN_RENDER_STATE.get()
    }
}