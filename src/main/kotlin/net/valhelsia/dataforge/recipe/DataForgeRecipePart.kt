package net.valhelsia.dataforge.recipe

import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.HolderGetter
import net.minecraft.world.item.Item

interface DataForgeRecipePart {
    fun getHasName(): String
    fun createPredicate(itemGetter: HolderGetter<Item>): ItemPredicate
}