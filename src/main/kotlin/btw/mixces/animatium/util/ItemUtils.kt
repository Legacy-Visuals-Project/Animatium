package btw.mixces.animatium.util

import btw.mixces.animatium.config.AnimatiumConfig
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.entity.state.EntityRenderState
import net.minecraft.client.renderer.item.ItemStackRenderState
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.CrossbowItem
import net.minecraft.world.item.DiggerItem
import net.minecraft.world.item.EnderpearlItem
import net.minecraft.world.item.FishingRodItem
import net.minecraft.world.item.FoodOnAStickItem
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.MaceItem
import net.minecraft.world.item.ProjectileItem
import net.minecraft.world.item.ProjectileWeaponItem
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.ShearsItem
import net.minecraft.world.item.ShieldItem
import net.minecraft.world.item.SwordItem
import net.minecraft.world.item.TridentItem
import net.minecraft.world.level.block.BannerBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RodBlock
import net.minecraft.world.level.block.SkullBlock
import kotlin.math.roundToInt

object ItemUtils {
    private val RENDER_STATE: ThreadLocal<ItemStackRenderState?> = ThreadLocal.withInitial { null }
    private val STACK: ThreadLocal<ItemStack?> = ThreadLocal.withInitial { null }
    private val DISPLAY_CONTEXT: ThreadLocal<ItemDisplayContext?> = ThreadLocal.withInitial { null }

    @JvmStatic
    fun set(renderState: ItemStackRenderState, stack: ItemStack, displayContext: ItemDisplayContext) {
        RENDER_STATE.remove()
        STACK.remove()
        DISPLAY_CONTEXT.remove()
        RENDER_STATE.set(renderState)
        STACK.set(stack)
        DISPLAY_CONTEXT.set(displayContext)
    }

    @JvmStatic
    fun getRenderState(): ItemStackRenderState? {
        return RENDER_STATE.get()
    }

    @JvmStatic
    fun getStack(): ItemStack? {
        return STACK.get()
    }

    @JvmStatic
    fun getDisplayContext(): ItemDisplayContext? {
        return DISPLAY_CONTEXT.get()
    }

    @JvmStatic
    fun isFishingRodItem(stack: ItemStack): Boolean {
        return if (!stack.isEmpty) {
            stack.item is FishingRodItem || stack.item is FoodOnAStickItem<*>
        } else {
            false
        }
    }

    @JvmStatic
    fun isRangedWeaponItem(stack: ItemStack): Boolean {
        return if (!stack.isEmpty) {
            stack.item is ProjectileWeaponItem
        } else {
            false
        }
    }

    @JvmStatic
    fun isHandheldItem(stack: ItemStack): Boolean {
        return if (!stack.isEmpty) {
            val item = stack.item
            // TODO: is this the best way? probably not
            item is DiggerItem || item is SwordItem
                    || item is MaceItem || item is TridentItem
                    || isFishingRodItem(stack)
                    || setOf(Items.STICK, Items.BREEZE_ROD, Items.BLAZE_ROD).contains(item)
        } else {
            false
        }
    }

    @JvmStatic
    fun isSkullBlock(stack: ItemStack): Boolean {
        return if (!stack.isEmpty) {
            Block.byItem(stack.item) is SkullBlock
        } else {
            false
        }
    }

    @JvmStatic
    fun isBlockItemBlacklisted(stack: ItemStack): Boolean {
        return if (!stack.isEmpty) {
            val item = Block.byItem(stack.item)
            item is BannerBlock || item is RodBlock || isSkullBlock(stack)
        } else {
            false
        }
    }

    @JvmStatic
    fun isItemBlacklisted(stack: ItemStack): Boolean {
        return if (!stack.isEmpty) {
            val item = stack.item
            item is ShieldItem || item is CrossbowItem || isBlockItemBlacklisted(stack)
        } else {
            false
        }
    }

    @JvmStatic
    fun isSwingItemBlacklisted(stack: ItemStack): Boolean {
        return if (!stack.isEmpty) {
            val item = stack.item
            item is ProjectileItem || item is BucketItem || item is ShearsItem || item is EnderpearlItem
        } else {
            false
        }
    }

    @JvmStatic
    fun isBlock3d(stack: ItemStack, itemStackRenderState: ItemStackRenderState): Boolean {
        return if (!stack.isEmpty) {
            stack.item is BlockItem && itemStackRenderState.isGui3d
        } else {
            false
        }
    }

    @JvmStatic
    fun applyLegacyFirstpersonTransforms(poseStack: PoseStack, direction: Int, runnable: Runnable) {
        poseStack.mulPose(Axis.YP.rotationDegrees(direction * 45.0F))
        poseStack.scale(0.4F, 0.4F, 0.4F)
        runnable.run()
        poseStack.scale(1 / 0.4F, 1 / 0.4F, 1 / 0.4F)
        poseStack.mulPose(Axis.YP.rotationDegrees(direction * -45.0F))
    }

    @JvmStatic
    fun applyLegacyThirdpersonTransforms(poseStack: PoseStack, direction: Int, runnable: Runnable) {
        // TODO
        runnable.run()
        // TODO
    }

    @JvmStatic
    // TODO: jesus this name
    fun shouldTiltItemPositionsInThirdperson(entityState: EntityRenderState): Boolean {
        return if (AnimatiumConfig.instance().tiltItemPositionsInThirdperson) {
            true
        } else {
            val entity = EntityUtils.getEntityByState(entityState) ?: return false
            if (entity is LivingEntity) {
                AnimatiumConfig.instance().legacyThirdpersonSwordBlockingPosition && entity.isBlocking
            } else {
                false
            }
        }
    }

    @JvmStatic
    fun getLegacyDurabilityColorValue(stack: ItemStack): Int {
        return (255.0 - stack.damageValue.toDouble() * 255.0 / stack.maxDamage.toDouble()).roundToInt()
    }

    @JvmStatic
    fun getOldItemRarity(stack: ItemStack): Rarity {
        return if (listOf(Items.GOLDEN_APPLE, Items.END_CRYSTAL).contains(stack.item)) {
            Rarity.RARE
        } else if (listOf(Items.NETHER_STAR, Items.ELYTRA, Items.DRAGON_HEAD).contains(stack.item)) {
            Rarity.UNCOMMON
        } else if (stack.item == Items.ENCHANTED_GOLDEN_APPLE) {
            Rarity.EPIC
        } else if (stack.item == Items.TRIDENT) {
            Rarity.COMMON
        } else {
            // TODO?: Trims? eh, if someone requests it ig
            stack.rarity
        }
    }
}