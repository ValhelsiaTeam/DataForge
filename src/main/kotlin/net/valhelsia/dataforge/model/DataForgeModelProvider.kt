package net.valhelsia.dataforge.model

import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.ModelProvider
import net.minecraft.world.level.block.Block
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.DataProviderContext

class DataForgeModelProvider(
    context: DataProviderContext,
    val blocks: Collection<() -> Block>,
    private val blockModelGenerator: ((BlockModelGenerators) -> BlockModelGenerator)?,
    private val itemModelGenerator: ((ItemModelGenerators) -> ItemModelGenerator)?
) : ModelProvider(context.packOutput) {

    fun generateBlockStateModels(modelGenerators: BlockModelGenerators) {
        DataForge.modelOutput = modelGenerators.modelOutput

        blockModelGenerator?.invoke(modelGenerators)?.generate()

        DataForge.modelOutput = null
    }

    fun generateItemModels(modelGenerators: ItemModelGenerators) {
        DataForge.modelOutput = modelGenerators.output

        itemModelGenerator?.invoke(modelGenerators)?.generate()

        DataForge.modelOutput = null
    }
}
