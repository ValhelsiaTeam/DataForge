package net.valhelsia.dataforge.recipe

import net.minecraft.advancements.Criterion
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike

data class ValhelsiaShapedRecipeBuilder(val builder: ShapedRecipeBuilder) {
    fun unlockedBy(provider: RecipeSubProvider, itemLike: ItemLike?): ValhelsiaShapedRecipeBuilder {
        return this.unlockedBy(provider, RecipePart.of(itemLike))
    }

    fun unlockedBy(provider: RecipeSubProvider, part: RecipePart<*>): ValhelsiaShapedRecipeBuilder {
        builder.unlockedBy(provider.getHasName(part), provider.has(part))

        return this
    }

    fun unlockedBy(criterionName: String?, criterion: Criterion<*>?): ValhelsiaShapedRecipeBuilder {
        builder.unlockedBy(criterionName, criterion)

        return this
    }

    fun group(groupName: String?): ValhelsiaShapedRecipeBuilder {
        builder.group(groupName)

        return this
    }

    fun pattern(pattern: String): ValhelsiaShapedRecipeBuilder {
        builder.pattern(pattern)

        return this
    }

    fun define(symbol: Char, part: RecipePart<*>): ValhelsiaShapedRecipeBuilder {
        if (part.get() is ItemLike) {
            this.define(symbol, part.get() as ItemLike)

            return this
        }

        if (part.get() is TagKey<*>) {
            this.define(symbol, part.get() as TagKey<Item?>)

            return this
        }

        if (part.get() is Ingredient) {
            this.define(symbol, part.get() as Ingredient)

            return this
        }

        throw IllegalArgumentException("Invalid type: " + part.get()!!.javaClass)
    }

    fun define(symbol: Char, tag: TagKey<Item?>): ValhelsiaShapedRecipeBuilder {
        builder.define(symbol, tag)

        return this
    }

    fun define(symbol: Char, itemLike: ItemLike): ValhelsiaShapedRecipeBuilder {
        builder.define(symbol, itemLike)

        return this
    }

    fun define(symbol: Char, ingredient: Ingredient): ValhelsiaShapedRecipeBuilder {
        builder.define(symbol, ingredient)

        return this
    }

    companion object {
        @JvmStatic
        @JvmOverloads
        fun shaped(category: RecipeCategory, result: ItemLike, count: Int = 1): ValhelsiaShapedRecipeBuilder {
            return ValhelsiaShapedRecipeBuilder(ShapedRecipeBuilder(category, result, count))
        }
    }
}
