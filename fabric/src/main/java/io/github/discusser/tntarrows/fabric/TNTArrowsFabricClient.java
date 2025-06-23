package io.github.discusser.tntarrows.fabric;

import io.github.discusser.tntarrows.TNTArrowTintSource;
import io.github.discusser.tntarrows.TNTArrows;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.color.item.ItemTintSources;

public class TNTArrowsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTintSources.ID_MAPPER.put(TNTArrows.TNT_ARROW.getId(), TNTArrowTintSource.MAP_CODEC);
    }
}
