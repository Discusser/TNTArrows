package io.github.discusser.tntarrows;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;
import java.util.logging.Logger;

public final class TNTArrows {
    public static final String MOD_ID = "tntarrows";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> TNT_ARROW = ITEMS.register("tnt_arrow", () -> new TNTArrowItem(
            new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(TNTArrows.MOD_ID, "tnt_arrow")))));
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(MOD_ID,
            Registries.DATA_COMPONENT_TYPE);
    public static final RegistrySupplier<DataComponentType<ResourceLocation>> DATA_TNT_BLOCK = DATA_COMPONENTS.register(
            "tnt_block",
            () -> DataComponentType.<ResourceLocation>builder().networkSynchronized(ResourceLocation.STREAM_CODEC)
                    .persistent(ResourceLocation.CODEC).build());
    public static final Supplier<ItemStack> BASE_TNT_ARROW = () -> new ItemStack(TNT_ARROW, 1,
            DataComponentPatch.builder().set(DATA_TNT_BLOCK.get(), BuiltInRegistries.BLOCK.getKey(Blocks.TNT)).build());
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(MOD_ID,
            Registries.RECIPE_SERIALIZER);
    public static final RegistrySupplier<RecipeSerializer<TNTArrowRecipe>> RECIPE_SERIALIZER_TNT_ARROW
            = RECIPE_SERIALIZERS.register("arrow_tnt_upgrade", TNTArrowRecipe.Serializer::new);

    public static void init() {
        // Write common init code here.
        ITEMS.register();
        DATA_COMPONENTS.register();
        RECIPE_SERIALIZERS.register();
    }
}
