package net.valhelsia.dataforge

import net.neoforged.bus.api.IEventBus

object DataForge {

    internal var collector: DataCollector? = null

    fun setup(collector: DataCollector, eventBus: IEventBus) {
        DataForge.collector = collector

        eventBus.addListener(DataGenerator::gatherData)
    }
}