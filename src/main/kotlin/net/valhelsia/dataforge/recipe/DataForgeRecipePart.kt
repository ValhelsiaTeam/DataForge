package net.valhelsia.dataforge.recipe

import net.minecraft.advancements.critereon.ItemPredicate

interface DataForgeRecipePart {
    fun getHasName(): String
    fun createPredicate(): ItemPredicate
}