package net.valhelsia.dataforge.tag

import net.minecraft.data.tags.BiomeTagsProvider
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.DataProviderContext

abstract class DataForgeBiomeTagsProvider(context: DataProviderContext) : BiomeTagsProvider(
    context.packOutput,
    context.lookupProvider,
    DataForge.modId,
    context.fileHelper
)