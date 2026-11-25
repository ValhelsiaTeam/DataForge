package net.valhelsia.dataforge.recipe

import net.minecraft.advancements.Advancement
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.item.crafting.Recipe

typealias SubProviderFactory = (BootstrapContext<Recipe<*>>, BootstrapContext<Advancement>) -> RecipeSubProvider

class DataForgeRecipeProvider(
    private val recipeOutput: BootstrapContext<Recipe<*>>,
    advancementOutput: BootstrapContext<Advancement>,
    private val subProviders: List<SubProviderFactory>
) : RecipeProvider(recipeOutput, advancementOutput) {
    override fun buildRecipes() {
        subProviders
            .map { it(recipeOutput, advancementOutput) }
            .forEach { it.buildRecipes() }
    }
}
