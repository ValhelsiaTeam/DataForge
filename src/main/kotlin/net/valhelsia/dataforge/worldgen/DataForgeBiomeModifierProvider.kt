package net.valhelsia.dataforge.worldgen

import net.minecraft.core.HolderGetter
import net.minecraft.core.HolderSet
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BiomeTags
import net.minecraft.util.random.WeightedList
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.neoforged.neoforge.common.world.BiomeModifier
import net.neoforged.neoforge.common.world.BiomeModifiers
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier
import net.neoforged.neoforge.common.world.BiomeModifiers.AddSpawnsBiomeModifier
import net.valhelsia.dataforge.RegistryDataProvider

abstract class DataForgeBiomeModifierProvider : RegistryDataProvider<BiomeModifier> {

    val BootstrapContext<BiomeModifier>.biomeLookup: HolderGetter<Biome>
        get() = this.lookup(Registries.BIOME)
    val BootstrapContext<BiomeModifier>.featureLookup: HolderGetter<PlacedFeature>
        get() = this.lookup(Registries.PLACED_FEATURE)

    val BootstrapContext<BiomeModifier>.overWorldBiomes: HolderSet<Biome>
        get() = this.biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD)
    val BootstrapContext<BiomeModifier>.netherBiomes: HolderSet<Biome>
        get() = this.biomeLookup.getOrThrow(BiomeTags.IS_NETHER)
    val BootstrapContext<BiomeModifier>.endBiomes: HolderSet<Biome>
        get() = this.biomeLookup.getOrThrow(BiomeTags.IS_END)

    fun BootstrapContext<BiomeModifier>.registerFeature(
        key: ResourceKey<BiomeModifier>,
        biomes: HolderSet<Biome>,
        features: HolderSet<PlacedFeature>,
        step: GenerationStep.Decoration
    ) {
        this.register(key, AddFeaturesBiomeModifier(biomes, features, step))
    }

    fun BootstrapContext<BiomeModifier>.removeFeature(
        key: ResourceKey<BiomeModifier>,
        biomes: HolderSet<Biome>,
        features: HolderSet<PlacedFeature>,
        steps: Set<GenerationStep.Decoration>
    ) {
        this.register(key, BiomeModifiers.RemoveFeaturesBiomeModifier(biomes, features, steps))
    }

    fun BootstrapContext<BiomeModifier>.registerSpawn(
        key: ResourceKey<BiomeModifier>,
        biomes: HolderSet<Biome>,
        spawners: WeightedList<SpawnerData>
    ) {
        this.register(key, AddSpawnsBiomeModifier(biomes, spawners))
    }
}