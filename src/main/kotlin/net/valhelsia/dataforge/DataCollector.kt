package net.valhelsia.dataforge

import net.minecraft.core.Registry
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.DataProvider
import net.minecraft.resources.ResourceKey

abstract class DataCollector {

    internal val providers = mutableMapOf<DataTarget, MutableList<DataProvider>>()
    internal val registrySetBuilder = RegistrySetBuilder()

    abstract fun collectData(context: DataProviderContext)

    fun addProvider(target: DataTarget, provider: DataProvider) {
        println("Adding provider...")

        providers.getOrPut(target) { mutableListOf() }.add(provider)
    }

    fun <T> addRegistryProvider(
        key: ResourceKey<out Registry<T>>,
        vararg providers: RegistryDataProvider<T>
    ) {
        println("Adding registry provider...")

        registrySetBuilder.add(key) { context -> providers.forEach { it.bootstrap(context) } }
    }
}