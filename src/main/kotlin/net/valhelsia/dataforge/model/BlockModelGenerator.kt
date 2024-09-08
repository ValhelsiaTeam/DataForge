package net.valhelsia.dataforge.model

import net.minecraft.data.models.BlockModelGenerators

abstract class BlockModelGenerator(defaultGenerators: BlockModelGenerators) {
    val blockStateOutput = defaultGenerators.blockStateOutput
    val modelOutput = defaultGenerators.modelOutput

    abstract fun generate()
}
