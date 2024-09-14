package net.valhelsia.dataforge.mixin;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.ItemLike;
import net.valhelsia.dataforge.recipe.DataForgeRecipePart;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemLike.class)
public interface ItemLikeMixin extends DataForgeRecipePart {

    @NotNull
    @Override
    default String getHasName() {
        return "has_" + BuiltInRegistries.ITEM.getKey(((ItemLike) this).asItem()).getPath();
    }

    @Override
    default ItemPredicate createPredicate() {
        return ItemPredicate.Builder.item().of((ItemLike) this).build();
    }
}
