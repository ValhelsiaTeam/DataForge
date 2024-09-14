package net.valhelsia.dataforge.mixin;

import net.minecraft.world.item.crafting.Ingredient;
import net.valhelsia.dataforge.recipe.DataForgeRecipePart;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Ingredient.class)
public class IngredientMixin implements DataForgeRecipePart {
}
