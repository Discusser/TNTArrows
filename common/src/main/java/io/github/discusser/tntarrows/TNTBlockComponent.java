package io.github.discusser.tntarrows;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record TNTBlockComponent(ResourceLocation location) implements TooltipProvider {
    public static final Codec<TNTBlockComponent> CODEC = ResourceLocation.CODEC.xmap(TNTBlockComponent::new,
            TNTBlockComponent::location);
    public static final StreamCodec<ByteBuf, TNTBlockComponent> STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(
            TNTBlockComponent::new, TNTBlockComponent::location);

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag,
            DataComponentGetter dataComponentGetter) {
        Component comp = TNTArrowItem.getTntComponent(location);
        if (comp != null) {
            comp = Component.translatable("tntarrows.mixed_with").append(comp);
            consumer.accept(comp);
        }
    }
}
