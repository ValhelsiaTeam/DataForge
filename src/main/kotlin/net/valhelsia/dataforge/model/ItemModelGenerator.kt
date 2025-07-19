package net.valhelsia.dataforge.model

import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

abstract class ItemModelGenerator(defaultGenerators: ItemModelGenerators) {
    val output = defaultGenerators.output

    abstract fun generate()

    fun generateBatch(
        vararg items: Holder<out Item>,
        template: ModelTemplate = ModelTemplates.FLAT_ITEM,
        folder: String? = null,
        suffix: String = "",
    ) = items.forEach { generateItem(it, template, folder, suffix) }

    fun generateItem(
        item: Holder<out Item>,
        template: ModelTemplate = ModelTemplates.FLAT_ITEM,
        folder: String? = null,
        suffix: String = "",
    ) = template.createModel(
        item.getModelLocation(suffix),
        TextureMapping.layer0(getItemTexture(item, folder, suffix)),
    )

    fun generateWithOverrides(
        item: Holder<out Item>,
        template: ModelTemplate = ModelTemplates.FLAT_ITEM,
        folder: String? = null,
        suffix: String = "",
        vararg overrides: ModelPredicate,
    ) = template.createModel(
        item.getModelLocation(suffix),
        TextureMapping.layer0(getItemTexture(item, folder, suffix))
    ) { modelLocation, map ->
        val jsonObject = ModelTemplates.TWO_LAYERED_ITEM.createBaseTemplate(modelLocation, map)

        ModelPredicate.CODEC.listOf().encodeStart(JsonOps.INSTANCE, overrides.toList()).ifSuccess {
            jsonObject.add("overrides", it)
        }

        jsonObject
    }

    fun generateFlatBlockItem(
        block: Holder<out Block>,
        folder: String? = null,
        suffix: String = "",
    ) = ModelTemplates.FLAT_ITEM.createModel(
        ModelLocationUtils.getModelLocation(block.value(), suffix),
        TextureMapping.layer0(getBlockItemTexture(block, folder, suffix)),
    )

    fun Holder<out Item>.getModelLocation(suffix: String = "") =
        ModelLocationUtils.getModelLocation(this.value(), suffix)

    private fun getItemTexture(
        item: Holder<out Item>,
        folder: String? = null,
        suffix: String = ""
    ) = BuiltInRegistries.ITEM.getKey(item.value())
        .withPath { "item/${if (folder != null) "$folder/" else ""}$it$suffix" }

    private fun getBlockItemTexture(
        block: Holder<out Block>,
        folder: String? = null,
        suffix: String = ""
    ) = BuiltInRegistries.ITEM.getKey(block.value().asItem())
        .withPath { "item/${if (folder != null) "$folder/" else ""}$it$suffix" }

    data class ModelPredicate(val modelLocation: ResourceLocation, val properties: List<ModelProperty>) {

        constructor(modelLocation: ResourceLocation, vararg properties: ModelProperty) : this(
            modelLocation,
            properties.toList()
        )

        companion object {
            val CODEC: Codec<ModelPredicate> = RecordCodecBuilder.create {
                it.group(
                    ResourceLocation.CODEC.fieldOf("model").forGetter { it.modelLocation },
                    Codec.list(ModelProperty.CODEC).fieldOf("predicate").forGetter { it.properties }
                ).apply(it, ::ModelPredicate)
            }
        }
    }

    data class ModelProperty(val name: ResourceLocation, val value: Float) {
        companion object {
            val CODEC: Codec<ModelProperty> = RecordCodecBuilder.create {
                it.group(
                    ResourceLocation.CODEC.fieldOf("name").forGetter { it.name },
                    Codec.FLOAT.fieldOf("value").forGetter { it.value }
                ).apply(it, ::ModelProperty)
            }
        }
    }
}
