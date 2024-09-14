package net.valhelsia.dataforge.recipe

import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike

fun <T : RecipeBuilder> T.unlockedBy(vararg parts: DataForgeRecipePart) = apply {
    parts.forEach { unlockedBy(it) }
}

fun <T : RecipeBuilder> T.unlockedBy(part: DataForgeRecipePart) = apply {
    this.unlockedBy(part.getHasName(), RecipeSubProvider.inventoryTrigger(part.createPredicate()))
}

fun ShapedRecipeBuilder.pattern(vararg rows: String) = apply {
    rows.forEach { this.pattern(it) }
}

fun ShapedRecipeBuilder.define(symbol: Char, part: DataForgeRecipePart) = apply {
    when (part) {
        is ItemLike -> this.define(symbol, part)
        is TagKey<*> -> this.define(symbol, part as TagKey<Item>)
        is Ingredient -> this.define(symbol, part)
        else -> throw IllegalArgumentException("Invalid type: ${part.javaClass}")
    }
}