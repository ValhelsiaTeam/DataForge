package net.valhelsia.dataforge

import net.minecraft.data.tags.TagsProvider.TagLookup
import net.minecraft.world.level.block.Block
import net.neoforged.bus.api.IEventBus
import java.util.concurrent.CompletableFuture

object DataForge {

    internal var collector: DataCollector? = null
    internal var blockTagLookup: CompletableFuture<TagLookup<Block>>? = null

    fun setup(collector: DataCollector, eventBus: IEventBus) {
        DataForge.collector = collector

        eventBus.addListener(DataGenerator::gatherData)
    }
}