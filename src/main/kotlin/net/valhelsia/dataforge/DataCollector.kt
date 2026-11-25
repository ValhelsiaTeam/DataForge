package net.valhelsia.dataforge

import net.minecraft.advancements.Advancement
import net.minecraft.core.Registry
import net.minecraft.data.DataProvider
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.crafting.Recipe
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent

abstract class DataCollector {

    internal val clientProviders = mutableListOf<DataProvider>()
    internal val serverProviders = mutableListOf<DataProvider>()
    internal val registryEntryGatherers = mutableListOf<(GatherDataRegistryEntriesEvent) -> Unit>()

    abstract fun collectClientProviders(context: DataProviderContext.Client)
    abstract fun collectServerProviders(context: DataProviderContext.Server)
    abstract fun collectRegistryProviders()

    fun addClientProvider(provider: DataProvider) {
        clientProviders.add(provider)
    }

    fun addServerProvider(provider: DataProvider) {
        serverProviders.add(provider)
    }

    fun <T : Any> addRegistryProvider(
        key: ResourceKey<out Registry<T>>,
        vararg providers: RegistryDataProvider<T>
    ) {
        registryEntryGatherers.add { event ->
            event.add(key) { context -> providers.forEach { it.bootstrap(context) } }
        }
    }

    fun addRecipeProvider(
        factory: (BootstrapContext<Recipe<*>>, BootstrapContext<Advancement>) -> RecipeProvider
    ) {
        registryEntryGatherers.add { event ->
            event.recipe(factory)
        }
    }

    fun addLootTableProvider(
        subProvider: LootTableProvider.SubProviderEntry,
        vararg subProviders: LootTableProvider.SubProviderEntry
    ) {
        registryEntryGatherers.add { event ->
            event.lootTable(subProvider, *subProviders)
        }
    }
}