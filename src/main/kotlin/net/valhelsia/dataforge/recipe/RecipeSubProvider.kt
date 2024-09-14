package net.valhelsia.dataforge.recipe

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.InventoryChangeTrigger
import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.neoforged.neoforge.common.Tags
import net.valhelsia.dataforge.recipe.ValhelsiaShapedRecipeBuilder.Companion.shaped
import java.util.*

abstract class RecipeSubProvider(private val provider: DataForgeRecipeProvider) {
    abstract fun registerRecipes(lookupProvider: HolderLookup.Provider)

    fun add(builder: RecipeBuilder, path: String? = null) {
        val id = when {
            path == null -> RecipeBuilder.getDefaultRecipeId(builder.result)
            else -> ResourceLocation.fromNamespaceAndPath(provider.getModId(), path)
        }

        builder.save(provider.recipeOutput, id)
    }

    fun storageRecipe(item: ItemLike, block: ItemLike, groupItem: String? = null, groupBlock: String? = null) {
        this.shaped(
            category = RecipeCategory.MISC,
            result = block,
            path = getName(block) + "_from_" + getName(item)
        ) { it.group(groupBlock).pattern("###").pattern("###").pattern("###").define('#', item).unlockedBy(this, item) }

        this.shapeless(
            category = RecipeCategory.BUILDING_BLOCKS,
            result = item,
            count = 9,
            path = getName(item) + "_from_" + getName(block)
        ) { it.group(groupItem).requires(block).unlockedBy("has_item", has(block)) }
    }

    fun simple2x2(category: RecipeCategory, result: ItemLike, item: DataForgeRecipePart) {
        this.shaped(category, result) { it.pattern("##").pattern("##").define('#', item).unlockedBy(this, item) }
    }

    fun singleRow(category: RecipeCategory, result: ItemLike, item: DataForgeRecipePart) {
        this.shaped(category, result) { it.pattern("###").define('#', item).unlockedBy(this, item) }
    }

    fun surroundingItem(
        category: RecipeCategory,
        result: ItemLike,
        middle: DataForgeRecipePart,
        outside: DataForgeRecipePart,
        amount: Int
    ) {
        this.shaped(category, result, amount) {
            it.pattern("###").pattern("#X#").pattern("###").define('#', outside).define('X', middle)
                .unlockedBy(this, middle).unlockedBy(this, outside)
        }
    }

    fun wood(result: ItemLike, log: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3) {
            it.group("bark").pattern("##").pattern("##").define('#', log).unlockedBy(this, log)
        }
    }

    fun planks(result: ItemLike, log: TagKey<Item>, count: Int = 4) {
        this.shapeless(
            RecipeCategory.BUILDING_BLOCKS,
            result,
            count
        ) { it.requires(log).unlockedBy("has_log", has(log)) }
    }

    fun slab(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6) {
            it.pattern("###").define('#', planks).unlockedBy(this, planks)
        }
    }

    fun woodenSlab(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6) {
            it.group("wooden_slab").pattern("###").define('#', planks).unlockedBy(this, planks)
        }
    }

    fun stairs(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4) {
            it.pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy(this, planks)
        }
    }

    fun sword(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.COMBAT, result) {
            it.pattern("#").pattern("#").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN)
                .unlockedBy(this, material)
        }
    }

    fun shovel(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.TOOLS, result) {
            it.pattern("#").pattern("X").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN)
                .unlockedBy(this, material)
        }
    }

    fun pickaxe(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.TOOLS, result) {
            it.pattern("###").pattern(" X ").pattern(" X ").define('#', material).define('X', Tags.Items.RODS_WOODEN)
                .unlockedBy(this, material)
        }
    }

    fun axe(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.TOOLS, result) {
            it.pattern("##").pattern("#X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN)
                .unlockedBy(this, material)
        }
    }

    fun hoe(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.TOOLS, result) {
            it.pattern("##").pattern(" X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN)
                .unlockedBy(this, material)
        }
    }

    fun helmet(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.COMBAT, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("###").pattern("# #").define('#', material).unlockedBy(
                this, material
            )
        }
    }

    fun chestplate(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.COMBAT, result) {
            it.pattern("# #").pattern("###").pattern("###").define('#', material).unlockedBy(this, material)
        }
    }

    fun leggings(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.COMBAT, result) {
            it.pattern("###").pattern("# #").pattern("# #").define('#', material).unlockedBy(this, material)
        }
    }

    fun boots(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.COMBAT, result) {
            it.pattern("# #").pattern("# #").define('#', material).unlockedBy(this, material)
        }
    }

    fun boat(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.TRANSPORTATION, result) {
            it.pattern("# #").pattern("###").define('#', material).unlockedBy(this, material)
        }
    }

    fun chestBoat(result: ItemLike, boat: ItemLike) {
        this.shapeless(RecipeCategory.TRANSPORTATION, result) {
            it.requires(boat).requires(Tags.Items.CHESTS_WOODEN)
                .unlockedBy("has_" + getName(boat), has(boat))
                .unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN))
        }
    }

    fun glassPane(result: ItemLike, glass: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 16) {
            it.pattern("###").pattern("###").define('#', glass).unlockedBy(this, glass)
        }
    }

    fun woodenStairs(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4) {
            it.group("wooden_stairs").pattern("#  ").pattern("## ").pattern("###").define('#', planks)
                .unlockedBy(this, planks)
        }
    }

    fun sign(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.DECORATIONS, result, 3) {
            it.pattern("###").pattern("###").pattern(" X ").define('#', planks).define('X', Tags.Items.RODS_WOODEN)
                .unlockedBy(this, planks)
        }
    }

    fun door(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3) {
            it.pattern("##").pattern("##").pattern("##").define('#', planks).unlockedBy(this, planks)
        }
    }

    fun trapdoor(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 2) {
            it.pattern("###").pattern("###").define('#', planks).unlockedBy(this, planks)
        }
    }

    fun fence(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3) {
            it.pattern("#X#").pattern("#X#").define('#', planks).define('X', Tags.Items.RODS_WOODEN)
                .unlockedBy(this, planks)
        }
    }

    fun fenceGate(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result) {
            it.pattern("#X#").pattern("#X#").define('X', planks).define('#', Tags.Items.RODS_WOODEN)
                .unlockedBy(this, planks)
        }
    }

    fun button(result: ItemLike, planks: ItemLike) {
        this.shapeless(RecipeCategory.REDSTONE, result) {
            it.requires(planks).unlockedBy("has_planks", has(planks))
        }
    }

    fun pressurePlate(result: ItemLike, planks: DataForgeRecipePart) {
        this.shaped(RecipeCategory.REDSTONE, result) {
            it.pattern("##").define('#', planks).unlockedBy(this, planks)
        }
    }

    fun carpet(result: ItemLike, material: DataForgeRecipePart) {
        this.shaped(RecipeCategory.DECORATIONS, result, 3) {
            it.pattern("##").define('#', material).unlockedBy(this, material)
        }
    }

    fun shaped(
        category: RecipeCategory,
        result: ItemLike,
        count: Int = 1,
        path: String? = null,
        recipe: ShapedRecipeTransformer,
    ) = this.add(recipe.invoke(shaped(category, result, count)).builder, path)

    fun shapeless(
        category: RecipeCategory,
        result: ItemLike,
        count: Int = 1,
        path: String? = null,
        recipe: ShapelessRecipeTransformer,
    ) = this.add(recipe.invoke(ShapelessRecipeBuilder.shapeless(category, result, count)), path)

    fun has(vararg items: ItemLike): Criterion<InventoryChangeTrigger.TriggerInstance> {
        return inventoryTrigger(ItemPredicate.Builder.item().of(*items).build())
    }

    fun has(tagKey: TagKey<Item>): Criterion<InventoryChangeTrigger.TriggerInstance> {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tagKey).build())
    }

    fun has(part: RecipePart<*>): Criterion<InventoryChangeTrigger.TriggerInstance> {
        if (part.get() is ItemLike) {
            return has(part.get() as ItemLike)
        }

        if (part.get() is TagKey<*>) {
            return has(part.get() as TagKey<Item>)
        }

        if (part.get() is Ingredient) {
            val itemLikes = Arrays.stream((part.get() as Ingredient).getItems()).map { obj: ItemStack -> obj.item }

            return inventoryTrigger(ItemPredicate.Builder.item().of().build())
        }

        throw IllegalArgumentException("Invalid type: " + part.get()!!.javaClass)
    }

    fun getHasName(part: RecipePart<*>): String {
        if (part.get() is ItemLike) {
            return Companion.getHasName(part.get() as ItemLike)
        }

        if (part.get() is TagKey<*>) {
            return "has_" + (part.get() as TagKey<*>).location().path
        }

        if (part.get() is Ingredient) {
            return "has_item"
        }

        throw IllegalArgumentException("Invalid type: " + part.get()!!.javaClass)
    }

    companion object {
        fun getName(item: ItemLike): String {
            return BuiltInRegistries.ITEM.getKey(item.asItem()).path
        }

        protected fun inventoryTrigger(vararg predicates: ItemPredicate?): Criterion<InventoryChangeTrigger.TriggerInstance> {
            return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
                InventoryChangeTrigger.TriggerInstance(
                    Optional.empty(),
                    InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                    listOf(*predicates)
                )
            )
        }

        protected fun getHasName(itemLike: ItemLike): String {
            return "has_" + getName(itemLike)
        }
    }
}

typealias ShapedRecipeTransformer = (ValhelsiaShapedRecipeBuilder) -> ValhelsiaShapedRecipeBuilder
typealias ShapelessRecipeTransformer = (ShapelessRecipeBuilder) -> ShapelessRecipeBuilder
