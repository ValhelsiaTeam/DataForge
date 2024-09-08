package net.valhelsia.dataforge.model

import net.minecraft.data.models.ItemModelGenerators

abstract class ItemModelGenerator(defaultGenerators: ItemModelGenerators) {
    val output = defaultGenerators.output

    abstract fun generate()
}
