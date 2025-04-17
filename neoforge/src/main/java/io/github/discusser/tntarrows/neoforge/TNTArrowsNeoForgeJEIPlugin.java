package io.github.discusser.tntarrows.neoforge;

import io.github.discusser.tntarrows.TNTArrows;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.TntBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class TNTArrowsNeoForgeJEIPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(TNTArrows.MOD_ID, "crafting_jei_plugin");
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        BuiltInRegistries.ITEM.forEach(item -> {
            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof TntBlock && item != Items.TNT) {
                NonNullList<Ingredient> ingredients = NonNullList.create();
                ingredients.add(Ingredient.of(Items.ARROW));
                ingredients.add(Ingredient.of(item));

                ItemStack tntArrow = new ItemStack(TNTArrows.TNT_ARROW.get());
                ResourceLocation tntResourceLocation = BuiltInRegistries.BLOCK.getKey(((BlockItem) item).getBlock());
                tntArrow.set(TNTArrows.DATA_TNT_BLOCK.get(), tntResourceLocation);

                RecipeHolder<CraftingRecipe> recipe = new RecipeHolder<>(
                        ResourceLocation.fromNamespaceAndPath(TNTArrows.MOD_ID, "tnt_arrow_upgrade_" + tntResourceLocation.getNamespace() + "_" + tntResourceLocation.getPath()),
                        new ShapelessRecipe("tnt_arrow_upgrade", CraftingBookCategory.EQUIPMENT, tntArrow, ingredients)
                );
                registration.addRecipes(RecipeTypes.CRAFTING, List.of(recipe));
            }
        });
    }
}
