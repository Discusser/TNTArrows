package io.github.discusser.tntarrows;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class TNTArrowRecipe extends CustomRecipe {
    public TNTArrowRecipe(CraftingBookCategory craftingBookCategory) {
        super(craftingBookCategory);
    }

    public boolean isTntItem(ItemStack stack) {
        return stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof TntBlock;
    }

    @Override
    public boolean matches(CraftingInput recipeInput, Level level) {
        List<ItemStack> items = recipeInput.items().stream().filter(itemStack -> !itemStack.is(Items.AIR)).toList();
        if (items.size() != 2)
            return false;

        boolean hasArrows = items.stream().anyMatch(itemStack -> itemStack.is(Items.ARROW));
        if (!hasArrows)
            return false;

        boolean hasTnt = items.stream().anyMatch(this::isTntItem);
        return hasTnt;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
        Optional<ItemStack> tntItem = recipeInput.items().stream().filter(this::isTntItem).findFirst();
        if (tntItem.isPresent()) {
            TntBlock tntBlock = (TntBlock) ((BlockItem) tntItem.get().getItem()).getBlock();
            ItemStack result = new ItemStack(TNTArrows.TNT_ARROW);
            result.set(TNTArrows.DATA_TNT_BLOCK.get(), tntBlock.arch$registryName());
            return result;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return TNTArrows.RECIPE_SERIALIZER_TNT_ARROW.get();
    }
}
