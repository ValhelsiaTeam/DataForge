package net.valhelsia.dataforge.model

import net.minecraft.core.Holder
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.world.level.block.Block

abstract class BlockModelGenerator(defaultGenerators: BlockModelGenerators) {
    val blockStateOutput = defaultGenerators.blockStateOutput
    val modelOutput = defaultGenerators.modelOutput

    abstract fun generate()

    fun Holder<out Block>.getModelLocation(suffix: String = "") =
        ModelLocationUtils.getModelLocation(this.value(), suffix)
}
