package net.valhelsia.dataforge.mixin;

import net.minecraft.world.level.ItemLike;
import net.valhelsia.dataforge.recipe.DataForgeRecipePart;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemLike.class)
public class ItemLikeMixin implements DataForgeRecipePart {
}
