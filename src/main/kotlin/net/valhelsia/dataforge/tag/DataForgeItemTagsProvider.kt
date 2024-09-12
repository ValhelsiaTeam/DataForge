package net.valhelsia.dataforge.tag

import net.minecraft.data.tags.ItemTagsProvider
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.DataProviderContext

abstract class DataForgeItemTagsProvider(context: DataProviderContext) : ItemTagsProvider(
    context.packOutput,
    context.lookupProvider,
    DataForge.blockTagLookup ?: throw IllegalStateException("Block tag lookup not initialized"),
    context.modId,
    context.fileHelper
)
