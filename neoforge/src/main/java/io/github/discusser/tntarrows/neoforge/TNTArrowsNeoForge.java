package io.github.discusser.tntarrows.neoforge;

import io.github.discusser.tntarrows.TNTArrows;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(TNTArrows.MOD_ID)
public final class TNTArrowsNeoForge {
    public TNTArrowsNeoForge(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(TNTArrowsNeoForgeEventHandler::commonSetup);
        modEventBus.addListener(TNTArrowsNeoForgeEventHandler::buildContents);
        modEventBus.addListener(TNTArrowsNeoForgeEventHandler::registerItemColors);

        // Run our common setup.
        TNTArrows.init();
    }
}
