package io.github.discusser.tntarrows.fabric;

import io.github.discusser.tntarrows.TNTArrows;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.level.block.DispenserBlock;

public final class TNTArrowsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        TNTArrows.init();
        ColorProviderRegistry.ITEM.register(TNTArrows.ARROW_COLOR, TNTArrows.TNT_ARROW.get());
        DispenserBlock.registerProjectileBehavior(TNTArrows.TNT_ARROW.get());
    }
}
