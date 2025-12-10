package net.valhelsia.dataforge.mixin;

import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.valhelsia.dataforge.recipe.DataForgeRecipePart;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(Ingredient.class)
public abstract class IngredientMixin implements DataForgeRecipePart {

    @Shadow
    public abstract HolderSet<Item> getValues();

    @NotNull
    @Override
    public String getHasName() {
        return "has_item";
    }

    @NotNull
    @Override
    public ItemPredicate createPredicate(@NotNull HolderGetter<Item> itemGetter) {
        return new ItemPredicate(Optional.of(this.getValues()), MinMaxBounds.Ints.ANY, DataComponentMatchers.ANY);
    }
}
