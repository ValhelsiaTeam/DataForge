package net.valhelsia.dataforge.model

import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.ModelProvider
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.DataProviderContext

class DataForgeModelProvider(
    context: DataProviderContext,
    private val blockModelGenerator: ((BlockModelGenerators) -> BlockModelGenerator)?,
    private val itemModelGenerator: ((ItemModelGenerators) -> ItemModelGenerator)?
) : ModelProvider(context.packOutput, DataForge.modId) {

    override fun registerModels(blockModels: BlockModelGenerators, itemModels: ItemModelGenerators) {
        generateBlockModels(blockModels)
        generateItemModels(itemModels)
    }

    private fun generateBlockModels(modelGenerators: BlockModelGenerators) {
        DataForge.modelOutput = modelGenerators.modelOutput

        blockModelGenerator?.invoke(modelGenerators)?.generate()

        DataForge.modelOutput = null
    }

    private fun generateItemModels(modelGenerators: ItemModelGenerators) {
        DataForge.modelOutput = modelGenerators.modelOutput

        itemModelGenerator?.invoke(modelGenerators)?.generate()

        DataForge.modelOutput = null
    }
}
