package net.valhelsia.dataforge.tag

import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.DataProviderContext

abstract class DataForgeItemTagsProvider(context: DataProviderContext.Server) : BlockTagCopyingItemTagProvider(
    context.packOutput,
    context.lookupProvider,
    DataForge.blockTagLookup ?: throw IllegalStateException("Block tag lookup not initialized"),
    DataForge.modId,
)
