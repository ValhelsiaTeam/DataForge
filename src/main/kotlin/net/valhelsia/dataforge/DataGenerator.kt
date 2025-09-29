package net.valhelsia.dataforge

import net.minecraft.core.HolderLookup
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.data.event.GatherDataEvent
import java.util.concurrent.CompletableFuture

internal object DataGenerator {

    @SubscribeEvent
    fun gatherClientData(event: GatherDataEvent.Client) {
        DataForge.modId = event.modContainer.modId

        val collector = DataForge.collector ?: return

        collector.collectClientProviders(event.createContext())
        collector.collectRegistryProviders()

        collector.clientProviders.forEach { event.generator.addProvider(true, it) }

        val dataProvider = DatapackBuiltinEntriesProvider(
            event.generator.packOutput,
            event.lookupProvider,
            collector.registrySetBuilder,
            setOf(DataForge.modId)
        )

        collector.collectServerProviders(event.createContext(dataProvider.registryProvider))

        event.generator.addProvider(true, dataProvider)

        collector.serverProviders.forEach { event.generator.addProvider(true, it) }
    }

    private fun GatherDataEvent.Client.createContext() = DataProviderContext.Client(this.generator.packOutput)

    private fun GatherDataEvent.Client.createContext(lookupProvider: CompletableFuture<HolderLookup.Provider>) =
        DataProviderContext.Server(this.generator.packOutput, lookupProvider)
}