package io.github.discusser.tntarrows.fabric;

import io.github.discusser.tntarrows.TNTArrows;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.DispenserBlock;

public final class TNTArrowsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        TNTArrows.init();
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register(entries -> entries.accept(TNTArrows.BASE_TNT_ARROW.get()));
        DispenserBlock.registerProjectileBehavior(TNTArrows.TNT_ARROW.get());
    }
}
