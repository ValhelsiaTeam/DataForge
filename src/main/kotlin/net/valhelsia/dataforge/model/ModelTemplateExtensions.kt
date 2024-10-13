package net.valhelsia.dataforge.model

import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.valhelsia.dataforge.DataForge

private val defaultJsonFactory = { template: ModelTemplate ->
    ModelTemplate.JsonFactory { modelLocation, map ->
        template.createBaseTemplate(modelLocation, map)
    }
}

/**
 * Creates a model using the given model location, texture mapping, and JSON factory.
 *
 * @param modelLocation The location of the model.
 * @param textureMapping The texture mapping for the model.
 * @param jsonFactory The JSON factory to use for creating the model.
 * @throws IllegalStateException if called outside a BlockModelGenerator or ItemModelGenerator context.
 */
fun ModelTemplate.createModel(
    modelLocation: ResourceLocation,
    textureMapping: TextureMapping,
    jsonFactory: ModelTemplate.JsonFactory = defaultJsonFactory(this)
) = this.create(
    modelLocation,
    textureMapping,
    DataForge.modelOutput
        ?: throw IllegalStateException("ModelTemplate#createModel can only be called within a BlockModelGenerator or ItemModelGenerator context."),
    jsonFactory
)

/**
 * Creates a model for the given block using the specified texture mapping, suffix, and JSON factory.
 *
 * @param block The block for which to create the model.
 * @param textureMapping The texture mapping for the model.
 * @param suffix The suffix to append to the model location.
 * @param jsonFactory The JSON factory to use for creating the model.
 */
fun ModelTemplate.createModel(
    block: Block,
    textureMapping: TextureMapping,
    suffix: String = "",
    jsonFactory: ModelTemplate.JsonFactory = defaultJsonFactory(this)
) = createModel(
    ModelLocationUtils.getModelLocation(block, suffix + this.suffix.orElse("")),
    textureMapping,
    jsonFactory
)
