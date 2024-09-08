package net.valhelsia.dataforge.mixin;

import com.google.gson.JsonElement;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.valhelsia.dataforge.model.DataForgeModelProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(ModelProvider.class)
public abstract class ModelProviderMixin {

    @Unique
    private final static Map<ResourceLocation, Supplier<JsonElement>> BLOCK_ITEMS = new HashMap<>();

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/models/BlockModelGenerators;run()V"))
    private void dataforge_run$registerBlockStateModels(BlockModelGenerators instance) {
        if (!this.createModels(modelProvider -> {
            modelProvider.generateBlockStateModels(instance);

            modelProvider.getBlocks().forEach(entry -> {
                Item item = Item.BY_BLOCK.get(entry.invoke());

                if (item == null) {
                    return;
                }

                ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(item);

                BLOCK_ITEMS.putIfAbsent(resourceLocation, new DelegatedModel(ModelLocationUtils.getModelLocation(entry.invoke())));
            });
        })) {
            instance.run();
        }
    }

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/models/ItemModelGenerators;run()V"))
    private void dataforge_run$registerItemModels(ItemModelGenerators instance) {
        if (!this.createModels(modelProvider -> modelProvider.generateItemModels(instance))) {
            instance.run();
        }
    }

    @Unique
    private boolean createModels(Consumer<DataForgeModelProvider> consumer) {
        if (((Object) this) instanceof DataForgeModelProvider modelProvider) {
            consumer.accept(modelProvider);

            return true;
        }

        return false;
    }

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private boolean dataforge_run$preventException(List<Block> instance) {
        return true;
    }

    @Redirect(method = "lambda$run$4", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    private static boolean dataforge_run$registerBlockStateModels(Set<Item> instance, Object o) {
        if (o instanceof Item item) {
            return !BLOCK_ITEMS.containsKey(ModelLocationUtils.getModelLocation(item));
        }
        return true;
    }
}
