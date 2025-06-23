package io.github.discusser.tntarrows;

import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record TNTArrowTintSource() implements ItemTintSource {
    public static final MapCodec<TNTArrowTintSource> MAP_CODEC = MapCodec.of(Encoder.empty(),
            Decoder.unit(TNTArrowTintSource::new));

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
        ResourceLocation location = itemStack.get(TNTArrows.DATA_TNT_BLOCK.get());
        Optional<Holder.Reference<Block>> block = BuiltInRegistries.BLOCK.get(location);
        return block.map(blockReference -> ARGB.opaque(blockReference.value().defaultMapColor().col)).orElse(-1);
    }

    @Override
    public @NotNull MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
