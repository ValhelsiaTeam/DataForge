package net.valhelsia.dataforge.mixin;

import net.minecraft.tags.TagKey;
import net.valhelsia.dataforge.recipe.DataForgeRecipePart;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TagKey.class)
public class TagKeyMixin implements DataForgeRecipePart {
}
