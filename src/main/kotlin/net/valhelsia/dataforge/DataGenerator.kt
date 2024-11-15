package net.valhelsia.dataforge

import net.minecraft.core.HolderLookup
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.data.event.GatherDataEvent
import java.util.concurrent.CompletableFuture

internal object DataGenerator {

    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        println("Gathering data...")

        DataForge.modId = event.modContainer.modId

        val collector = DataForge.collector ?: return

        collector.collectRegistryProviders()

        val dataProvider = DatapackBuiltinEntriesProvider(
            event.generator.packOutput,
            event.lookupProvider,
            collector.registrySetBuilder,
            setOf(DataForge.modId)
        )

        event.generator.addProvider(DataTarget.SERVER.isEnabled(event), dataProvider)

        collector.collectProviders(event.createContext(dataProvider.registryProvider))

        collector.providers
            .filterKeys { it.isEnabled(event) }
            .flatMap { it.value }
            .forEach { event.generator.addProvider(true, it) }
    }

    private fun GatherDataEvent.createContext(lookupProvider: CompletableFuture<HolderLookup.Provider>) = DataProviderContext(
        this.generator.packOutput,
        lookupProvider,
        this.existingFileHelper
    )
}