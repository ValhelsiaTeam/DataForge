package net.valhelsia.dataforge

import com.google.gson.JsonElement
import net.minecraft.data.tags.TagsProvider.TagLookup
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.neoforged.bus.api.IEventBus
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer
import java.util.function.Supplier

object DataForge {

    internal var collector: DataCollector? = null
    internal var blockTagLookup: CompletableFuture<TagLookup<Block>>? = null
    internal var modelOutput: BiConsumer<ResourceLocation, Supplier<JsonElement>>? = null

    internal lateinit var modId: String

    fun setup(collector: DataCollector, eventBus: IEventBus) {
        DataForge.collector = collector

        eventBus.addListener(DataGenerator::gatherData)
    }
}