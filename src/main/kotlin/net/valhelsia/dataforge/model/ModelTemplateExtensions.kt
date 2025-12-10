package net.valhelsia.dataforge.model

import net.minecraft.client.data.models.model.ModelLocationUtils
import net.minecraft.client.data.models.model.ModelTemplate
import net.minecraft.client.data.models.model.TextureMapping
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.Block
import net.valhelsia.dataforge.DataForge
import net.valhelsia.dataforge.mixin.ModelTemplateAccessor

/**
 * Creates a model using the given model location, texture mapping, and JSON factory.
 *
 * @param modelLocation The location of the model.
 * @param textureMapping The texture mapping for the model.
 * @throws IllegalStateException if called outside a BlockModelGenerator or ItemModelGenerator context.
 */
fun ModelTemplate.createModel(
    modelLocation: Identifier,
    textureMapping: TextureMapping,
): Identifier = this.create(
    modelLocation,
    textureMapping,
    DataForge.modelOutput
        ?: throw IllegalStateException("ModelTemplate#createModel can only be called within a BlockModelGenerator or ItemModelGenerator context."),
)

/**
 * Creates a model for the given block using the specified texture mapping, suffix, and JSON factory.
 *
 * @param block The block for which to create the model.
 * @param textureMapping The texture mapping for the model.
 * @param suffix The suffix to append to the model location.
 */
fun ModelTemplate.createModel(
    block: Block,
    textureMapping: TextureMapping,
    suffix: String = "",
): Identifier = createModel(
    ModelLocationUtils.getModelLocation(block, suffix + (this as ModelTemplateAccessor).suffix.orElse("")),
    textureMapping,
)
