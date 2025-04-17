package io.github.discusser.tntarrows.neoforge;

import io.github.discusser.tntarrows.TNTArrows;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(TNTArrows.MOD_ID)
public final class TNTArrowsNeoForge {
    public TNTArrowsNeoForge(IEventBus modEventBus, ModContainer container) {
        // Run our common setup.
        TNTArrows.init();
    }
}
