package io.github.discusser.tntarrows;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TNTArrowItem extends ArrowItem {
    public TNTArrowItem(Properties properties) {
        super(properties);
    }

    @Nullable
    public Component getTntComponent(ItemStack itemStack) {
        ResourceLocation location = itemStack.get(TNTArrows.DATA_TNT_BLOCK.get());
        if (location != null) {
            int color = MapColor.COLOR_RED.col;
            Block tnt = BuiltInRegistries.BLOCK.get(location);
            if (tnt != Blocks.AIR) color = tnt.defaultMapColor().col;
            int finalColor = color;
            return Component.translatable("block." + location.toLanguageKey()).withStyle(style -> style.withColor(finalColor));
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
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
        Component comp = this.getTntComponent(itemStack);
        if (comp != null) {
            main.append(" (");
            main.append(comp);
            main.append(")");
        }
        return main;
    }

    @Override
    public @NotNull AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity livingEntity, @Nullable ItemStack itemStack2) {
        return new TNTArrowProjectile(level, livingEntity, itemStack.copyWithCount(1), itemStack2);
    }

    public @NotNull Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        TNTArrowProjectile arrow = new TNTArrowProjectile(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1), null);
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }
}
