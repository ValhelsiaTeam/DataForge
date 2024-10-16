package net.valhelsia.dataforge

import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.data.event.GatherDataEvent

internal object DataGenerator {

    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        println("Gathering data...")

        DataForge.modId = event.modContainer.modId

        val collector = DataForge.collector ?: return
        collector.collectData(event.createContext())

        collector.providers
            .filterKeys { it.isEnabled(event) }
            .flatMap { it.value }
            .forEach { event.generator.addProvider(true, it) }
    }

    private fun GatherDataEvent.createContext() = DataProviderContext(
        this.generator.packOutput,
        this.lookupProvider,
        this.existingFileHelper
    )
}