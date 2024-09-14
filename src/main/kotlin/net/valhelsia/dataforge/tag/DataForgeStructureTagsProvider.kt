package net.valhelsia.dataforge.tag

import net.minecraft.data.tags.StructureTagsProvider
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.DataProviderContext

abstract class DataForgeStructureTagsProvider(context: DataProviderContext) : StructureTagsProvider(
    context.packOutput,
    context.lookupProvider,
    DataForge.modId,
    context.fileHelper
)