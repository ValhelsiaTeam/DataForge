package net.valhelsia.dataforge

import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.concurrent.CompletableFuture

data class DataProviderContext(
    val packOutput: PackOutput,
    val lookupProvider: CompletableFuture<HolderLookup.Provider>,
    val fileHelper: ExistingFileHelper
)
