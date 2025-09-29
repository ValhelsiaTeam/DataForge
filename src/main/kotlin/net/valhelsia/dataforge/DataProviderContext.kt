package net.valhelsia.dataforge

import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import java.util.concurrent.CompletableFuture

sealed class DataProviderContext(
    val packOutput: PackOutput,
) {
    class Client(
        packOutput: PackOutput
    ) : DataProviderContext(packOutput)

    class Server(
        packOutput: PackOutput,
        val lookupProvider: CompletableFuture<HolderLookup.Provider>
    ) : DataProviderContext(
        packOutput
    )
}
