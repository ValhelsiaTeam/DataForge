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

    fun add(builder: RecipeBuilder) {
        builder.save(provider.recipeOutput)
    }

    fun add(builder: RecipeBuilder, path: String) {
        builder.save(
            provider.recipeOutput, ResourceLocation.fromNamespaceAndPath(
                provider.getModId(), path
            )
        )
    }

    @JvmOverloads
    fun storageRecipe(item: ItemLike, block: ItemLike, groupItem: String? = null, groupBlock: String? = null) {
        this.shaped(RecipeCategory.MISC, block, { builder: ValhelsiaShapedRecipeBuilder ->
            builder.group(groupBlock).pattern("###").pattern("###").pattern("###").define('#', item).unlockedBy(
                this, item
            )
        }, getName(block) + "_from_" + getName(item))
        this.shapeless(
            RecipeCategory.BUILDING_BLOCKS,
            item,
            9,
            { builder: ShapelessRecipeBuilder ->
                builder.group(groupItem).requires(block).unlockedBy("has_item", has(block))
            },
            getName(item) + "_from_" + getName(block)
        )
    }

    fun simple2x2(category: RecipeCategory, result: ItemLike, item: RecipePart<*>) {
        this.shaped(category, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("##").pattern("##").define('#', item!!).unlockedBy(
                this, item
            )
        }
    }

    fun singleRow(category: RecipeCategory, result: ItemLike, item: RecipePart<*>) {
        this.shaped(category, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("###").define('#', item!!).unlockedBy(
                this, item
            )
        }
    }

    fun surroundingItem(
        category: RecipeCategory,
        result: ItemLike,
        middle: RecipePart<*>,
        outside: RecipePart<*>,
        amount: Int
    ) {
        this.shaped(category, result, amount) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("###").pattern("#X#").pattern("###").define('#', outside!!).define('X', middle)
                .unlockedBy(getHasName(middle), has(middle)).unlockedBy(
                this, outside
            )
        }
    }

    fun wood(result: ItemLike, log: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.group("bark").pattern("##").pattern("##").define('#', log!!).unlockedBy(
                this, log
            )
        }
    }

//    @JvmOverloads
//    fun planks(result: ItemLike, log: TagKey<Item>, count: Int = 4) {
//        this.shapeless(
//            RecipeCategory.BUILDING_BLOCKS,
//            result,
//            count
//        ) { builder: ShapelessRecipeBuilder -> builder.requires(log).unlockedBy("has_log", has(log)) }
//    }

    fun slab(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("###").define('#', planks!!).unlockedBy(
                this, planks
            )
        }
    }

    fun woodenSlab(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.group("wooden_slab").pattern("###").define('#', planks!!).unlockedBy(
                this, planks
            )
        }
    }

    fun stairs(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("#  ").pattern("## ").pattern("###").define('#', planks!!).unlockedBy(
                this, planks
            )
        }
    }

    fun sword(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.COMBAT, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("#").pattern("#").pattern("X").define('#', material!!).define('X', Tags.Items.RODS_WOODEN)
                .unlockedBy(
                    this, material
                )
        }
    }

    fun shovel(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.TOOLS, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("#").pattern("X").pattern("X").define('#', material!!).define('X', Tags.Items.RODS_WOODEN)
                .unlockedBy(
                    this, material
                )
        }
    }

    fun pickaxe(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.TOOLS, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("###").pattern(" X ").pattern(" X ").define('#', material!!)
                .define('X', Tags.Items.RODS_WOODEN).unlockedBy(
                this, material
            )
        }
    }

    fun axe(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.TOOLS, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("##").pattern("#X").pattern(" X").define('#', material!!)
                .define('X', Tags.Items.RODS_WOODEN).unlockedBy(
                this, material
            )
        }
    }

    fun hoe(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.TOOLS, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("##").pattern(" X").pattern(" X").define('#', material!!)
                .define('X', Tags.Items.RODS_WOODEN).unlockedBy(
                this, material
            )
        }
    }

    fun helmet(result: ItemLike, material: DataForgeRecipePart) {
//        this.shaped(RecipeCategory.COMBAT, result) { builder: ValhelsiaShapedRecipeBuilder ->
//            builder.pattern("###").pattern("# #").define('#', material).unlockedBy(
//                this, material
//            )
//        }
    }

    fun chestplate(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.COMBAT, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("# #").pattern("###").pattern("###").define('#', material).unlockedBy(
                this, material
            )
        }
    }

    fun leggings(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.COMBAT, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("###").pattern("# #").pattern("# #").define('#', material!!).unlockedBy(
                this, material
            )
        }
    }

    fun boots(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.COMBAT, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("# #").pattern("# #").define('#', material!!).unlockedBy(
                this, material
            )
        }
    }

    fun boat(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.TRANSPORTATION, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("# #").pattern("###").define('#', material!!).unlockedBy(
                this, material
            )
        }
    }

    fun chestBoat(result: ItemLike, boat: ItemLike) {
        this.shapeless(RecipeCategory.TRANSPORTATION, result) { builder: ShapelessRecipeBuilder ->
            builder.requires(boat).requires(
                Tags.Items.CHESTS_WOODEN
            ).unlockedBy("has_" + getName(boat), has(boat)).unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN))
        }
    }

    fun glassPane(result: ItemLike, glass: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 16) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("###").pattern("###").define('#', glass!!).unlockedBy(
                this, glass
            )
        }
    }

    fun woodenStairs(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.group("wooden_stairs").pattern("#  ").pattern("## ").pattern("###").define('#', planks!!)
                .unlockedBy(
                    this, planks
                )
        }
    }

    fun sign(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.DECORATIONS, result, 3) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("###").pattern("###").pattern(" X ").define('#', planks!!)
                .define('X', Tags.Items.RODS_WOODEN).unlockedBy(
                this, planks
            )
        }
    }

    fun door(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("##").pattern("##").pattern("##").define('#', planks!!).unlockedBy(
                this, planks
            )
        }
    }

    fun trapdoor(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 2) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("###").pattern("###").define('#', planks!!).unlockedBy(
                this, planks
            )
        }
    }

    fun fence(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("#X#").pattern("#X#").define('#', planks!!).define('X', Tags.Items.RODS_WOODEN).unlockedBy(
                this, planks
            )
        }
    }

    fun fenceGate(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("#X#").pattern("#X#").define('X', planks!!).define('#', Tags.Items.RODS_WOODEN).unlockedBy(
                this, planks
            )
        }
    }

    fun button(result: ItemLike, planks: ItemLike) {
        this.shapeless(RecipeCategory.REDSTONE, result) { builder: ShapelessRecipeBuilder ->
            builder.requires(planks).unlockedBy("has_planks", has(planks))
        }
    }

    fun pressurePlate(result: ItemLike, planks: RecipePart<*>) {
        this.shaped(RecipeCategory.REDSTONE, result) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("##").define('#', planks!!).unlockedBy(
                this, planks
            )
        }
    }

    fun carpet(result: ItemLike, material: RecipePart<*>) {
        this.shaped(RecipeCategory.DECORATIONS, result, 3) { builder: ValhelsiaShapedRecipeBuilder ->
            builder.pattern("##").define('#', material!!).unlockedBy(
                this, material
            )
        }
    }

    fun shaped(category: RecipeCategory, result: ItemLike, recipe: ShapedRecipeTransformer) {
        this.add(recipe.invoke(shaped(category, result)).builder)
    }

    fun shaped(
        category: RecipeCategory,
        result: ItemLike,
        recipe: ShapedRecipeTransformer,
        path: String
    ) {
        this.add(recipe.invoke(shaped(category, result)).builder, path)
    }

    fun shaped(
        category: RecipeCategory,
        result: ItemLike,
        count: Int,
        recipe: ShapedRecipeTransformer
    ) {
        this.add(recipe.invoke(shaped(category, result, count)).builder)
    }

    fun shaped(
        category: RecipeCategory,
        result: ItemLike,
        count: Int,
        recipe: ShapedRecipeTransformer,
        path: String
    ) {
        this.add(recipe.invoke(shaped(category, result, count)).builder, path)
    }

    fun shapeless(category: RecipeCategory, result: ItemLike, recipe: ShapelessRecipeTransformer) {
        this.add(recipe.invoke(ShapelessRecipeBuilder.shapeless(category, result)))
    }

    fun shapeless(
        category: RecipeCategory,
        result: ItemLike,
        recipe: ShapelessRecipeTransformer,
        path: String
    ) {
        this.add(recipe.invoke(ShapelessRecipeBuilder.shapeless(category, result)), path)
    }

    fun shapeless(
        category: RecipeCategory,
        result: ItemLike,
        count: Int,
        recipe: ShapelessRecipeTransformer
    ) {
        this.add(recipe.invoke(ShapelessRecipeBuilder.shapeless(category, result, count)))
    }

    fun shapeless(
        category: RecipeCategory,
        result: ItemLike,
        count: Int,
        recipe: ShapelessRecipeTransformer,
        path: String
    ) {
        this.add(recipe.invoke(ShapelessRecipeBuilder.shapeless(category, result, count)), path)
    }

    fun has(vararg items: ItemLike): Criterion<InventoryChangeTrigger.TriggerInstance> {
        return inventoryTrigger(ItemPredicate.Builder.item().of(*items).build())
    }

    fun has(tagKey: TagKey<Item?>): Criterion<InventoryChangeTrigger.TriggerInstance> {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tagKey).build())
    }

    fun has(part: RecipePart<*>): Criterion<InventoryChangeTrigger.TriggerInstance> {
        if (part.get() is ItemLike) {
            return has(part.get() as ItemLike)
        }

        if (part.get() is TagKey<*>) {
            return has(part.get() as TagKey<Item?>)
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

    fun item(itemLike: ItemLike): RecipePart<ItemLike> {
        return RecipePart.of(itemLike)!!
    }

    fun tag(itemTagKey: TagKey<Item>): RecipePart<TagKey<Item>> {
        return RecipePart.of(itemTagKey)
    }

    fun ingredient(ingredient: Ingredient): RecipePart<Ingredient> {
        return RecipePart.of(ingredient)!!
    }

    companion object {
        fun getName(item: ItemLike): String {
            return BuiltInRegistries.ITEM.getKey(item.asItem()).path
        }

        protected fun has(
            forgeTag: TagKey<Item?>?,
            fabricTag: TagKey<Item?>?
        ): Criterion<InventoryChangeTrigger.TriggerInstance> {
            return inventoryTrigger(
                ItemPredicate.Builder.item().of(forgeTag).build(),
                ItemPredicate.Builder.item().of(fabricTag).build()
            )
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
