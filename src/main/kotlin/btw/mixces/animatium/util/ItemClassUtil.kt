package btw.mixces.animatium.util

import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.ShieldItem

// 25w02a+ Removed default classes for DiggerItem/SwordItem, these methods will help replace their uses in our code
object ItemClassUtil {
    // Created for future use case
    @JvmStatic
    fun isSwordItem(stack: ItemStack): Boolean {
        return stack.`is`(ItemTags.SWORDS)
    }

    // Created for future use case
    @JvmStatic
    fun isAxeItem(stack: ItemStack): Boolean {
        return stack.`is`(ItemTags.AXES)
    }

    // Created for future use case
    @JvmStatic
    fun isPickaxeItem(stack: ItemStack): Boolean {
        return stack.`is`(ItemTags.PICKAXES)
    }

    // Created for future use case
    @JvmStatic
    fun isShovelItem(stack: ItemStack): Boolean {
        return stack.`is`(ItemTags.SHOVELS)
    }

    // Created for future use case
    @JvmStatic
    fun isHoeItem(stack: ItemStack): Boolean {
        return stack.`is`(ItemTags.HOES)
    }

    // Created for future use case
    @JvmStatic
    fun isDiggerItem(stack: ItemStack): Boolean {
        return isAxeItem(stack) || isPickaxeItem(stack) || isShovelItem(stack) || isHoeItem(stack)
    }

    // Created for future use case
    @JvmStatic
    fun isShieldItem(item: Item): Boolean {
        return item == Items.SHIELD || item is ShieldItem /* Temporary, could be removed with addition of "blocks_attacks" component */
    }
}