package net.valhelsia.dataforge

import net.minecraft.data.worldgen.BootstrapContext

interface RegistryDataProvider<T> {
    fun bootstrap(context: BootstrapContext<T>)
}