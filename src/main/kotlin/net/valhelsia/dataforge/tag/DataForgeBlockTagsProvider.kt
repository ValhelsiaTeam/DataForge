package net.valhelsia.dataforge.tag

import net.neoforged.neoforge.common.data.BlockTagsProvider
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.DataProviderContext

abstract class DataForgeBlockTagsProvider(context: DataProviderContext) : BlockTagsProvider(
    context.packOutput,
    context.lookupProvider,
    DataForge.modId,
    context.fileHelper
) {
    init {
        DataForge.blockTagLookup = this.contentsGetter()
    }
}
