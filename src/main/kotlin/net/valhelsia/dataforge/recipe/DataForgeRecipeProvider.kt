package net.valhelsia.dataforge.recipe

import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.valhelsia.dataforge.DataProviderContext

class DataForgeRecipeProvider(
    private val context: DataProviderContext,
    vararg subProviders: (DataForgeRecipeProvider) -> RecipeSubProvider
) : RecipeProvider(context.packOutput, context.lookupProvider) {
    private val subProviders: List<RecipeSubProvider> = subProviders.map { it.invoke(this) }

    lateinit var recipeOutput: RecipeOutput

    override fun buildRecipes(recipeOutput: RecipeOutput) {
        this.recipeOutput = recipeOutput

        this.context.lookupProvider.thenAccept { provider ->
            subProviders.forEach { it.registerRecipes(provider) }
        }
    }

    fun getModId() = this.context.modId
}
