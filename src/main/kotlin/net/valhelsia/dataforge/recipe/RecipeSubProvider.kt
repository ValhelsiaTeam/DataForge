package net.valhelsia.dataforge.recipe

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.HolderGetter
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.*
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.Tags
import net.valhelsia.dataforge.DataForge
import java.util.*

abstract class RecipeSubProvider(
    val registries: HolderLookup.Provider,
    val recipeOutput: RecipeOutput,
) {
    val items: HolderGetter<Item> = registries.lookupOrThrow(Registries.ITEM)

    abstract fun buildRecipes()

    fun add(builder: RecipeBuilder, path: String? = null) {
        val id = when {
            path == null -> RecipeBuilder.getDefaultRecipeId(builder.result)
            else -> ResourceLocation.fromNamespaceAndPath(DataForge.modId, path)
        }

        builder.save(recipeOutput, ResourceKey.create(Registries.RECIPE, id))
    }

    fun storageRecipe(item: ItemLike, block: ItemLike, groupItem: String? = null, groupBlock: String? = null) {
        this.shaped(RecipeCategory.MISC, block, path = "${block.getName()}_from_${item.getName()}") {
            it.group(groupBlock).pattern("###", "###", "###").define('#', item).unlockedBy(item)
        }

        this.shapeless(RecipeCategory.BUILDING_BLOCKS, item, 9, "${item.getName()}_from_${block.getName()}") {
            it.group(groupItem).requires(block).unlockedBy(block)
        }
    }

    fun simple2x2(category: RecipeCategory, result: ItemLike, item: DataForgeRecipePart) =
        this.shaped(category, result) {
            it.pattern("##", "##").define('#' to item).unlockedBy(item)
        }

    fun singleRow(category: RecipeCategory, result: ItemLike, item: DataForgeRecipePart) =
        this.shaped(category, result) {
            it.pattern("###").define('#' to item).unlockedBy(item)
        }

    fun surroundingItem(
        category: RecipeCategory,
        result: ItemLike,
        middle: DataForgeRecipePart,
        outside: DataForgeRecipePart,
        amount: Int
    ) = this.shaped(category, result, amount) {
        it.pattern("###", "#X#", "###").define('#' to outside, 'X' to middle).unlockedBy(middle, outside)
    }

    fun wood(result: ItemLike, log: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3) {
            it.group("bark").pattern("##", "##").define('#' to log).unlockedBy(log)
        }

    fun planks(result: ItemLike, log: TagKey<Item>, count: Int = 4) =
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, result, count) {
            it.requires(log).unlockedBy(log)
        }

    fun slab(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6) {
            it.pattern("###").define('#' to planks).unlockedBy(planks)
        }

    fun woodenSlab(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6) {
            it.group("wooden_slab").pattern("###").define('#' to planks).unlockedBy(planks)
        }

    fun stairs(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4) {
            it.pattern("#  ", "## ", "###").define('#' to planks).unlockedBy(planks)
        }

    fun sword(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.COMBAT, result) {
            it.pattern("#", "#", "X").define('#' to material, 'X' to Tags.Items.RODS_WOODEN).unlockedBy(material)
        }

    fun shovel(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.TOOLS, result) {
            it.pattern("#", "X", "X").define('#' to material, 'X' to Tags.Items.RODS_WOODEN).unlockedBy(material)
        }

    fun pickaxe(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.TOOLS, result) {
            it.pattern("###", " X ", " X ").define('#' to material, 'X' to Tags.Items.RODS_WOODEN).unlockedBy(material)
        }

    fun axe(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.TOOLS, result) {
            it.pattern("##", "#X", " X").define('#' to material, 'X' to Tags.Items.RODS_WOODEN).unlockedBy(material)
        }

    fun hoe(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.TOOLS, result) {
            it.pattern("##", " X", " X").define('#' to material, 'X' to Tags.Items.RODS_WOODEN).unlockedBy(material)
        }

    fun helmet(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.COMBAT, result) {
            it.pattern("###", "# #").define('#' to material).unlockedBy(material)
        }

    fun chestplate(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.COMBAT, result) {
            it.pattern("# #", "###", "###").define('#' to material).unlockedBy(material)
        }

    fun leggings(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.COMBAT, result) {
            it.pattern("###", "# #", "# #").define('#' to material).unlockedBy(material)
        }
    }

    fun boots(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.COMBAT, result) {
            it.pattern("# #", "# #").define('#' to material).unlockedBy(material)
        }

    fun boat(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.TRANSPORTATION, result) {
            it.pattern("# #", "###").define('#' to material).unlockedBy(material)
        }

    fun chestBoat(result: ItemLike, boat: ItemLike) =
        this.shapeless(RecipeCategory.TRANSPORTATION, result) {
            it.requires(boat, Tags.Items.CHESTS_WOODEN).unlockedBy(boat, Tags.Items.CHESTS_WOODEN)
        }

    fun glassPane(result: ItemLike, glass: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 16) {
            it.pattern("###", "###").define('#' to glass).unlockedBy(glass)
        }

    fun woodenStairs(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4) {
            it.group("wooden_stairs").pattern("#  ", "## ", "###").define('#' to planks).unlockedBy(planks)
        }

    fun sign(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.DECORATIONS, result, 3) {
            it.pattern("###", "###", " X ").define('#' to planks, 'X' to Tags.Items.RODS_WOODEN).unlockedBy(planks)
        }

    fun door(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3) {
            it.pattern("##", "##", "##").define('#' to planks).unlockedBy(planks)
        }

    fun trapdoor(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 2) {
            it.pattern("###", "###").define('#' to planks).unlockedBy(planks)
        }

    fun fence(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3) {
            it.pattern("#X#", "#X#").define('#' to planks, 'X' to Tags.Items.RODS_WOODEN).unlockedBy(planks)
        }

    fun fenceGate(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result) {
            it.pattern("#X#", "#X#").define('X' to planks, '#' to Tags.Items.RODS_WOODEN).unlockedBy(planks)
        }

    fun button(result: ItemLike, planks: ItemLike) =
        this.shapeless(RecipeCategory.REDSTONE, result) {
            it.requires(planks).unlockedBy(planks)
        }

    fun pressurePlate(result: ItemLike, planks: DataForgeRecipePart) =
        this.shaped(RecipeCategory.REDSTONE, result) {
            it.pattern("##").define('#' to planks).unlockedBy(planks)
        }

    fun carpet(result: ItemLike, material: DataForgeRecipePart) =
        this.shaped(RecipeCategory.DECORATIONS, result, 3) {
            it.pattern("##").define('#' to material).unlockedBy(material)
        }

    fun shaped(
        category: RecipeCategory,
        result: ItemLike,
        count: Int = 1,
        path: String? = null,
        transformer: ShapedRecipeTransformer,
    ) = add(transformer(ShapedRecipeBuilder.shaped(items, category, result, count)), path)

    fun shapeless(
        category: RecipeCategory,
        result: ItemLike,
        count: Int = 1,
        path: String? = null,
        transformer: ShapelessRecipeTransformer,
    ) = add(transformer(ShapelessRecipeBuilder.shapeless(items, category, result, count)), path)

    fun <T : RecipeBuilder> T.unlockedBy(vararg parts: DataForgeRecipePart) = apply {
        parts.forEach { unlockedBy(it) }
    }

    fun <T : RecipeBuilder> T.unlockedBy(part: DataForgeRecipePart) = apply {
        this.unlockedBy(part.getHasName(), inventoryTrigger(part.createPredicate(items)))
    }

    companion object {
        fun inventoryTrigger(vararg predicates: ItemPredicate): Criterion<InventoryChangeTrigger.TriggerInstance> =
            CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
                InventoryChangeTrigger.TriggerInstance(
                    Optional.empty(),
                    InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                    listOf(*predicates)
                )
            )
    }
}

fun ItemLike.getName(): String = BuiltInRegistries.ITEM.getKey(this.asItem()).path

typealias ShapedRecipeTransformer = (ShapedRecipeBuilder) -> ShapedRecipeBuilder
typealias ShapelessRecipeTransformer = (ShapelessRecipeBuilder) -> ShapelessRecipeBuilder
