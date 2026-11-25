package net.valhelsia.dataforge

import net.minecraft.core.HolderLookup
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.data.event.GatherDataEvent
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent
import java.util.concurrent.CompletableFuture

internal object DataGenerator {

    @SubscribeEvent
    fun gatherRegistryEntries(event: GatherDataRegistryEntriesEvent) {
        DataForge.modId = event.modContainer.modId

        val collector = DataForge.collector ?: return

        collector.collectRegistryProviders()
        collector.registryEntryGatherers.forEach { it(event) }
    }

    @SubscribeEvent
    fun gatherClientData(event: GatherDataEvent.Client) {
        DataForge.modId = event.modContainer.modId

        val collector = DataForge.collector ?: return

        collector.collectClientProviders(event.createContext())
        collector.clientProviders.forEach { event.generator.addProvider(true, it) }

        collector.collectServerProviders(event.createContext(event.reloadableLookupProvider))
        collector.serverProviders.forEach { event.generator.addProvider(true, it) }
    }

    private fun GatherDataEvent.Client.createContext() = DataProviderContext.Client(this.generator.packOutput)

    private fun GatherDataEvent.Client.createContext(lookupProvider: CompletableFuture<HolderLookup.Provider>) =
        DataProviderContext.Server(this.generator.packOutput, lookupProvider)
}