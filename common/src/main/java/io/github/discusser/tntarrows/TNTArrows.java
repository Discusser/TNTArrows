package io.github.discusser.tntarrows;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.logging.Logger;

public final class TNTArrows {
    public static final String MOD_ID = "tntarrows";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(MOD_ID, Registries.DATA_COMPONENT_TYPE);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(MOD_ID, Registries.RECIPE_SERIALIZER);

    public static final RegistrySupplier<Item> TNT_ARROW = ITEMS.register("tnt_arrow", () -> new TNTArrowItem(new Item.Properties().arch$tab(CreativeModeTabs.COMBAT)));
    public static final RegistrySupplier<DataComponentType<ResourceLocation>> DATA_TNT_BLOCK = DATA_COMPONENTS.register("tnt_block", () -> DataComponentType.<ResourceLocation>builder().networkSynchronized(ResourceLocation.STREAM_CODEC).persistent(ResourceLocation.CODEC).build());
    public static final RegistrySupplier<RecipeSerializer<TNTArrowRecipe>> RECIPE_SERIALIZER_TNT_ARROW = RECIPE_SERIALIZERS.register("arrow_tnt_upgrade", () -> new SimpleCraftingRecipeSerializer<>(TNTArrowRecipe::new));

    public static void init() {
        // Write common init code here.
        ITEMS.register();
        DATA_COMPONENTS.register();
        RECIPE_SERIALIZERS.register();
    }

    public static final ItemColor ARROW_COLOR = (stack, i) -> {
        if (i == 0)
            return -1;

        ResourceLocation location = stack.get(TNTArrows.DATA_TNT_BLOCK.get());
        Block block = BuiltInRegistries.BLOCK.get(location);
        if (block != Blocks.AIR) {
            return FastColor.ARGB32.opaque(block.defaultMapColor().col);
        }
        return -1;
    };
}
