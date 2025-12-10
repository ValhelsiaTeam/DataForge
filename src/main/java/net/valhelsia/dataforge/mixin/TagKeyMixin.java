package net.valhelsia.dataforge.mixin;

import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.valhelsia.dataforge.recipe.DataForgeRecipePart;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TagKey.class)
public class TagKeyMixin implements DataForgeRecipePart {

    @Shadow @Final private Identifier location;

    @NotNull
    @Override
    public String getHasName() {
        return "has_"+ this.location.getPath();
    }

    @NotNull
    @Override
    public ItemPredicate createPredicate(@NotNull HolderGetter<Item> itemGetter) {
        return ItemPredicate.Builder.item().of(itemGetter, (TagKey<Item>) (Object) this).build();
    }
}
