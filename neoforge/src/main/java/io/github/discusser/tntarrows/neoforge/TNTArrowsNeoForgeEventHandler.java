package io.github.discusser.tntarrows.neoforge;

import io.github.discusser.tntarrows.TNTArrowTintSource;
import io.github.discusser.tntarrows.TNTArrows;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class TNTArrowsNeoForgeEventHandler {
    public static void registerItemColors(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(TNTArrows.TNT_ARROW.getId(), TNTArrowTintSource.MAP_CODEC);
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        DispenserBlock.registerProjectileBehavior(TNTArrows.TNT_ARROW.get());
    }

    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(TNTArrows.BASE_TNT_ARROW.get());
        }
    }
}
