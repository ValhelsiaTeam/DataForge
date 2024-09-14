package net.valhelsia.dataforge.mixin;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.valhelsia.dataforge.recipe.DataForgeRecipePart;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TagKey.class)
public class TagKeyMixin implements DataForgeRecipePart {

    @Shadow @Final private ResourceLocation location;

    @NotNull
    @Override
    public String getHasName() {
        return "has_"+ this.location.getPath();
    }

    @NotNull
    @Override
    public ItemPredicate createPredicate() {
        return ItemPredicate.Builder.item().of((TagKey<Item>) (Object) this).build();
    }
}
