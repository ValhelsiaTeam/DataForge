package net.valhelsia.dataforge.mixin;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.valhelsia.dataforge.recipe.DataForgeRecipePart;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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
        return new ItemPredicate(Optional.of(this.getValues()), MinMaxBounds.Ints.ANY, DataComponentPredicate.EMPTY, Map.of());
    }
}
