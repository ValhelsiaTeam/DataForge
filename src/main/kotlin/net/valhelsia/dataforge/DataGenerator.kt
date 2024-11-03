package net.valhelsia.dataforge

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.data.event.GatherDataEvent

internal object DataGenerator {

    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        println("Gathering data...")

        DataForge.modId = event.modContainer.modId

        val collector = DataForge.collector ?: return
        val context = event.createContext()
        collector.collectData(context)

        collector.providers
            .filterKeys { it.isEnabled(event) }
            .flatMap { it.value }
            .forEach { event.generator.addProvider(true, it) }

        event.generator.addProvider(
            DataTarget.SERVER.isEnabled(event),
            DatapackBuiltinEntriesProvider(
                context.packOutput,
                context.lookupProvider,
                collector.registrySetBuilder,
                setOf(DataForge.modId)
            )
        )
    }

    private fun GatherDataEvent.createContext() = DataProviderContext(
        this.generator.packOutput,
        this.lookupProvider,
        this.existingFileHelper
    )
}