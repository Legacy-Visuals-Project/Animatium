/**
 * Animatium
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2025 lowercasebtw
 * Copyright (C) 2024-2025 mixces
 * Copyright (C) 2024-2025 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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