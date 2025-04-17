package io.github.discusser.tntarrows.neoforge;

import io.github.discusser.tntarrows.TNTArrows;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = TNTArrows.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class TNTArrowsNeoForgeEventHandler {
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(TNTArrows.ARROW_COLOR, TNTArrows.TNT_ARROW.get());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        DispenserBlock.registerProjectileBehavior(TNTArrows.TNT_ARROW.get());
    }
}
