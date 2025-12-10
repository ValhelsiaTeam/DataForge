package net.valhelsia.dataforge

import net.minecraft.core.Registry
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.DataProvider
import net.minecraft.resources.ResourceKey

abstract class DataCollector {

    internal val clientProviders = mutableListOf<DataProvider>()
    internal val serverProviders = mutableListOf<DataProvider>()
    internal val registrySetBuilder = RegistrySetBuilder()

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
        println("Adding registry provider...")

        registrySetBuilder.add(key) { context -> providers.forEach { it.bootstrap(context) } }
    }
}