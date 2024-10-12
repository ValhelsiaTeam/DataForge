package net.valhelsia.dataforge.model

import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.resources.ResourceLocation
import net.valhelsia.dataforge.DataForge

fun ModelTemplate.createModel(
    modelLocation: ResourceLocation,
    textureMapping: TextureMapping,
    jsonFactory: ModelTemplate.JsonFactory = ModelTemplate.JsonFactory { modelLocation, map ->
        this.createBaseTemplate(modelLocation, map)
    }
) = this.create(
    modelLocation,
    textureMapping,
    DataForge.modelOutput
        ?: throw IllegalStateException("ModelTemplate#createModel can only be called within a BlockModelGenerator or ItemModelGenerator context."),
    jsonFactory
)

