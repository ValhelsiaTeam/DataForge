package net.valhelsia.dataforge.mixin;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.crafting.Ingredient;
import net.valhelsia.dataforge.recipe.DataForgeRecipePart;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Ingredient.class)
public class IngredientMixin implements DataForgeRecipePart {

    @NotNull
    @Override
    public String getHasName() {
        return "has_item";
    }

    @NotNull
    @Override
    public ItemPredicate createPredicate() {
        return ItemPredicate.Builder.item().of().build();
    }
}
