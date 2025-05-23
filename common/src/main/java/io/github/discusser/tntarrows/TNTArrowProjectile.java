package io.github.discusser.tntarrows;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TNTArrowProjectile extends AbstractArrow {
    @Nullable
    private TntBlock tntBlock;

    public TNTArrowProjectile(EntityType<? extends AbstractArrow> entityType, Level level, ItemStack itemStack) {
        super(entityType, level);
        this.setTntBlock(itemStack);
    }

    public TNTArrowProjectile(Level level, double d, double e, double f, ItemStack itemStack, @Nullable ItemStack itemStack2) {
        super(EntityType.ARROW, d, e, f, level, itemStack, itemStack2);
        this.setTntBlock(itemStack);
    }

    public TNTArrowProjectile(Level level, LivingEntity livingEntity, ItemStack itemStack, @Nullable ItemStack itemStack2) {
        super(EntityType.ARROW, livingEntity, level, itemStack, itemStack2);
        this.setTntBlock(itemStack);
    }

    public void setTntBlock(ItemStack itemStack) {
        this.pickup = Pickup.CREATIVE_ONLY;
        this.setBaseDamage(0);
        ResourceLocation location = itemStack.get(TNTArrows.DATA_TNT_BLOCK.get());
        if (location == null) {
            this.tntBlock = null;
            return;
        }
        try {
            this.tntBlock = (TntBlock) BuiltInRegistries.BLOCK.get(location);
        } catch (ClassCastException e) {
            TNTArrows.LOGGER.info("Tried creating a TNT arrow that has an invalid TNT block: " + location);
            this.tntBlock = null;
        }
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    public void explode(HitResult hitResult) {
        try {
            if ((hitResult.getType() == HitResult.Type.BLOCK || hitResult.getType() == HitResult.Type.ENTITY) &&
                    !this.level().isClientSide && this.tntBlock != null) {
                // This is done because the code has to work on fabric aswell
                // NeoForge provides a `onCaughtFire` method but fabric does not give any methods that replace the static `explode`
                // so we have to improvise and simulate a fire charge use.
                FakePlayer player = FakePlayer.get((ServerLevel) this.level());
                player.getInventory().setItem(0, new ItemStack(Items.FIRE_CHARGE));
                BlockHitResult blockHitResult = new BlockHitResult(hitResult.getLocation(), Direction.NORTH, this.blockPosition(), false);
                BlockState blockState = this.tntBlock.defaultBlockState();
                this.level().setBlock(this.blockPosition(), blockState, 11);
                this.tntBlock.useItemOn(player.getInventory().getItem(0), blockState, this.level(), this.blockPosition(), player, InteractionHand.MAIN_HAND, blockHitResult);
                player.getInventory().setItem(0, ItemStack.EMPTY);
                PrimedTnt entity = (PrimedTnt) this.level().getEntity(ENTITY_COUNTER.get());
                if (entity != null) entity.setFuse(0);
                this.remove(RemovalReason.DISCARDED);
            }
        } catch (ClassCastException e) {
            if (this.tntBlock != null) {
                TNTArrows.LOGGER.info("Tried exploding a TNT arrow that has an invalid TNT block: " + this.tntBlock.arch$registryName());
            }
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        this.explode(hitResult);
    }
}
