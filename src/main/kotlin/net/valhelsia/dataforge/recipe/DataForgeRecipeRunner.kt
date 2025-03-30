package net.valhelsia.dataforge.recipe

import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.DataProviderContext

class DataForgeRecipeRunner(
    context: DataProviderContext,
    private vararg val subProviders: SubProviderFactory
) : RecipeProvider.Runner(context.packOutput, context.lookupProvider) {

    override fun getName(): String {
        return "Recipe Provider: ${DataForge.modId}"
    }

    override fun createRecipeProvider(
        lookupProvider: HolderLookup.Provider,
        recipeOutput: RecipeOutput
    ): RecipeProvider {
        return DataForgeRecipeProvider(lookupProvider, recipeOutput, subProviders.toList());
    }
}

typealias SubProviderFactory = (HolderLookup.Provider, RecipeOutput) -> RecipeSubProvider

class DataForgeRecipeProvider(
    private val lookupProvider: HolderLookup.Provider,
    private val recipeOutput: RecipeOutput,
    private val subProviders: List<SubProviderFactory>
) : RecipeProvider(lookupProvider, recipeOutput) {
    override fun buildRecipes() {
        subProviders
            .map { it(lookupProvider, recipeOutput) }
            .forEach { it.buildRecipes() }
    }
}
