package io.github.discusser.tntarrows;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class TNTArrowItem extends ArrowItem {
    public TNTArrowItem(Properties properties) {
        super(properties);
    }

    @Nullable
    public static Component getTntComponent(ItemStack itemStack) {
        return getTntComponent(itemStack.get(TNTArrows.DATA_TNT_BLOCK.get()));
    }

    @Nullable
    public static Component getTntComponent(ResourceLocation tntBlock) {
        if (tntBlock != null) {
            int color = MapColor.COLOR_RED.col;
            Optional<Holder.Reference<Block>> tnt = BuiltInRegistries.BLOCK.get(tntBlock);
            if (tnt.isPresent())
                color = tnt.get().value().defaultMapColor().col;
            int finalColor = color;
            return Component.translatable("block." + tntBlock.toLanguageKey())
                    .withStyle(style -> style.withColor(finalColor));
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list,
            TooltipFlag tooltipFlag) {
        Component comp = getTntComponent(itemStack);
        if (comp != null) {
            comp = Component.translatable("tntarrows.mixed_with").append(comp);
            list.add(comp);
        }
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        MutableComponent main = super.getName(itemStack).copy();
        Component comp = getTntComponent(itemStack);
        if (comp != null) {
            main.append(" (");
            main.append(comp);
            main.append(")");
        }
        return main;
    }

    public @NotNull Component getName(ResourceLocation tntBlock) {
        ItemStack itemStack = new ItemStack(TNTArrows.TNT_ARROW, 1,
                DataComponentPatch.builder().set(TNTArrows.DATA_TNT_BLOCK.get(), tntBlock).build());
        return getName(itemStack);
    }

    @Override
    public @NotNull AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity,
            @Nullable ItemStack itemStack2) {
        return new TNTArrowProjectile(level, livingEntity, itemStack.copyWithCount(1), itemStack2);
    }

    public @NotNull Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        TNTArrowProjectile arrow = new TNTArrowProjectile(level, position.x(), position.y(), position.z(),
                itemStack.copyWithCount(1), null);
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }
}
