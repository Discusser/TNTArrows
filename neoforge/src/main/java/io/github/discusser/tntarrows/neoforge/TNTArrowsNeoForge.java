package io.github.discusser.tntarrows.neoforge;

import io.github.discusser.tntarrows.TNTArrows;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(TNTArrows.MOD_ID)
public final class TNTArrowsNeoForge {
    public TNTArrowsNeoForge(IEventBus modEventBus, ModContainer container) {
        NeoForge.EVENT_BUS.addListener(TNTArrowsNeoForgeEventHandler::commonSetup);
        NeoForge.EVENT_BUS.addListener(TNTArrowsNeoForgeEventHandler::buildContents);
        NeoForge.EVENT_BUS.addListener(TNTArrowsNeoForgeEventHandler::registerItemColors);

        // Run our common setup.
        TNTArrows.init();
    }
}
