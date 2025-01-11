package btw.mixces.animatium.util

import btw.mixces.animatium.config.AnimatiumConfig
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.state.EntityRenderState
import net.minecraft.client.renderer.item.ItemStackRenderState
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.*
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

    @JvmStatic
    fun areItemsEqual1_8(itemStack: ItemStack, itemStack2: ItemStack): Boolean {
        // We must make sure the actual item class is the same as the class of the item being swapped to (SwordItem, BowItem, etc...)
        val itemsMatch = ItemStack.isSameItem(itemStack, itemStack2)
        // Because the durability changes the itemstack and that results in the item equip update code reading it as a different itemstack,
        // we must ensure the durabilities are different before we skip the equip update
        val durabilitiesMatch = itemStack.damageValue == itemStack2.damageValue
        // Similar to the durability, the stack count changing results in the item equip update code reading the stack as a different stack
        val countMatch = itemStack.count == itemStack2.count
        // This check is not ideal. I need a better to check for inventory slots :( This presents a bug with mending items while a gui is open
        val notInGui = Minecraft.getInstance().screen == null
        // If these conditions are met, the item update code will skip the equip animation as the stack will be immediately updated
        // Some of these checks may or may not be redundant. I will have to rewrite this logic to be simpler someday :)
        return (itemsMatch && notInGui && (!durabilitiesMatch || !countMatch))
    }
}