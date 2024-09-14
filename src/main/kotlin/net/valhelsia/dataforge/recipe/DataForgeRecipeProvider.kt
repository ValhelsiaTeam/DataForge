package net.valhelsia.dataforge.recipe

import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.valhelsia.dataforge.DataProviderContext

class DataForgeRecipeProvider(
    private val context: DataProviderContext,
    private vararg val subProviders: RecipeSubProvider
) : RecipeProvider(context.packOutput, context.lookupProvider) {

    override fun buildRecipes(recipeOutput: RecipeOutput) {
        this.context.lookupProvider.thenAccept { runSubProviders(recipeOutput, it) }
    }

    private fun runSubProviders(recipeOutput: RecipeOutput, provider: HolderLookup.Provider) {
        subProviders.forEach { it.registerRecipes(recipeOutput, provider) }
    }
}
