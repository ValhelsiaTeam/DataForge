package net.valhelsia.dataforge.tag

import net.neoforged.neoforge.common.data.ItemTagsProvider
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.DataProviderContext

abstract class DataForgeItemTagsProvider(context: DataProviderContext.Server) : ItemTagsProvider(
    context.packOutput,
    context.lookupProvider,
    DataForge.modId,
)
