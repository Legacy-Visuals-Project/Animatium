package me.mixces.animatium.util

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import me.mixces.animatium.config.AnimatiumConfig
import net.minecraft.client.renderer.entity.state.EntityRenderState
import net.minecraft.client.renderer.item.ItemStackRenderState
import net.minecraft.core.component.DataComponents
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

    @JvmStatic
    fun areEquals1_8(left: ItemStack, right: ItemStack): Boolean {
        // TODO: manually match the object stuff as doing equals checks on them break it
        // TODO/NOTE: For example, components, tags, etc
        return (left.count == right.count) &&
                (left.itemName == right.itemName) &&
                (left.customName == right.customName) &&
                (left.rarity == right.rarity) &&
                (left.maxStackSize == right.maxStackSize) &&
                (left.maxDamage == right.maxDamage) &&
                (left.isDamaged == right.isDamaged) &&
                (left.damageValue == right.damageValue) &&
                (left.useAnimation == right.useAnimation) &&
                (left.isEnchanted == right.isEnchanted) &&
                (left.components.size() == right.components.size()) &&
                (left.components.get(DataComponents.CUSTOM_DATA) == right.components.get(DataComponents.CUSTOM_DATA)) &&
                (left.components.get(DataComponents.UNBREAKABLE) == right.components.get(DataComponents.UNBREAKABLE)) &&
                (left.components.get(DataComponents.LORE) == right.components.get(DataComponents.LORE)) &&
                (left.components.get(DataComponents.ENCHANTMENTS) == right.components.get(DataComponents.ENCHANTMENTS)) &&
                (left.components.get(DataComponents.ATTRIBUTE_MODIFIERS) == right.components.get(DataComponents.ATTRIBUTE_MODIFIERS)) &&
                (left.components.get(DataComponents.CUSTOM_MODEL_DATA) == right.components.get(DataComponents.CUSTOM_MODEL_DATA)) &&
                (left.components.get(DataComponents.REPAIR_COST) == right.components.get(DataComponents.REPAIR_COST)) &&
                (left.components.get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE) == right.components.get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE)) &&
                (left.components.get(DataComponents.INTANGIBLE_PROJECTILE) == right.components.get(DataComponents.INTANGIBLE_PROJECTILE)) &&
                (left.components.get(DataComponents.FOOD) == right.components.get(DataComponents.FOOD)) &&
                (left.components.get(DataComponents.CONSUMABLE) == right.components.get(DataComponents.CONSUMABLE)) &&
                (left.components.get(DataComponents.USE_REMAINDER) == right.components.get(DataComponents.USE_REMAINDER)) &&
                (left.components.get(DataComponents.USE_COOLDOWN) == right.components.get(DataComponents.USE_COOLDOWN)) &&
                (left.components.get(DataComponents.DAMAGE_RESISTANT) == right.components.get(DataComponents.DAMAGE_RESISTANT)) &&
                (left.components.get(DataComponents.TOOL) == right.components.get(DataComponents.TOOL)) &&
                (left.components.get(DataComponents.ENCHANTABLE) == right.components.get(DataComponents.ENCHANTABLE)) &&
                (left.components.get(DataComponents.REPAIRABLE) == right.components.get(DataComponents.REPAIRABLE)) &&
                (left.components.get(DataComponents.GLIDER) == right.components.get(DataComponents.GLIDER)) &&
                (left.components.get(DataComponents.TOOLTIP_STYLE) == right.components.get(DataComponents.TOOLTIP_STYLE)) &&
                (left.components.get(DataComponents.DEATH_PROTECTION) == right.components.get(DataComponents.DEATH_PROTECTION)) &&
                (left.components.get(DataComponents.STORED_ENCHANTMENTS) == right.components.get(DataComponents.STORED_ENCHANTMENTS)) &&
                (left.components.get(DataComponents.DYED_COLOR) == right.components.get(DataComponents.DYED_COLOR)) &&
                (left.components.get(DataComponents.MAP_COLOR) == right.components.get(DataComponents.MAP_COLOR)) &&
                (left.components.get(DataComponents.MAP_ID) == right.components.get(DataComponents.MAP_ID)) &&
                (left.components.get(DataComponents.CHARGED_PROJECTILES) == right.components.get(DataComponents.CHARGED_PROJECTILES)) &&
                (left.components.get(DataComponents.POTION_CONTENTS) == right.components.get(DataComponents.POTION_CONTENTS)) &&
                (left.components.get(DataComponents.SUSPICIOUS_STEW_EFFECTS) == right.components.get(DataComponents.SUSPICIOUS_STEW_EFFECTS)) &&
                (left.components.get(DataComponents.WRITABLE_BOOK_CONTENT) == right.components.get(DataComponents.WRITABLE_BOOK_CONTENT)) &&
                (left.components.get(DataComponents.WRITTEN_BOOK_CONTENT) == right.components.get(DataComponents.WRITTEN_BOOK_CONTENT)) &&
                (left.components.get(DataComponents.TRIM) == right.components.get(DataComponents.TRIM)) &&
                (left.components.get(DataComponents.ENTITY_DATA) == right.components.get(DataComponents.ENTITY_DATA)) &&
                (left.components.get(DataComponents.BUCKET_ENTITY_DATA) == right.components.get(DataComponents.BUCKET_ENTITY_DATA)) &&
                (left.components.get(DataComponents.BLOCK_ENTITY_DATA) == right.components.get(DataComponents.BLOCK_ENTITY_DATA)) &&
                (left.components.get(DataComponents.INSTRUMENT) == right.components.get(DataComponents.INSTRUMENT)) &&
                (left.components.get(DataComponents.OMINOUS_BOTTLE_AMPLIFIER) == right.components.get(DataComponents.OMINOUS_BOTTLE_AMPLIFIER)) &&
                (left.components.get(DataComponents.JUKEBOX_PLAYABLE) == right.components.get(DataComponents.JUKEBOX_PLAYABLE)) &&
                (left.components.get(DataComponents.LODESTONE_TRACKER) == right.components.get(DataComponents.LODESTONE_TRACKER)) &&
                (left.components.get(DataComponents.FIREWORK_EXPLOSION) == right.components.get(DataComponents.FIREWORK_EXPLOSION)) &&
                (left.components.get(DataComponents.FIREWORKS) == right.components.get(DataComponents.FIREWORKS)) &&
                (left.components.get(DataComponents.PROFILE) == right.components.get(DataComponents.PROFILE)) &&
                (left.components.get(DataComponents.NOTE_BLOCK_SOUND) == right.components.get(DataComponents.NOTE_BLOCK_SOUND)) &&
                (left.components.get(DataComponents.BANNER_PATTERNS) == right.components.get(DataComponents.BANNER_PATTERNS)) &&
                (left.components.get(DataComponents.BASE_COLOR) == right.components.get(DataComponents.BASE_COLOR)) &&
                (left.components.get(DataComponents.POT_DECORATIONS) == right.components.get(DataComponents.POT_DECORATIONS)) &&
                (left.components.get(DataComponents.BLOCK_STATE) == right.components.get(DataComponents.BLOCK_STATE)) &&
                (left.components.get(DataComponents.LOCK) == right.components.get(DataComponents.LOCK)) &&
                (left.components.get(DataComponents.EQUIPPABLE) == right.components.get(DataComponents.EQUIPPABLE))
    }
}