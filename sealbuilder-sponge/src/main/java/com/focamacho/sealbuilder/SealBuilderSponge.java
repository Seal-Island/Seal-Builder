package com.focamacho.sealbuilder;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "sealbuilder",
        name = "Seal Builder",
        description = "PokeBuilder para Pixelmon Reforged",
        dependencies = @Dependency(id = "seallibrary"),
        authors = {
                "Focamacho"
        }
)
public class SealBuilderSponge {

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        SealBuilder.onEnable(this);
    }

    @Listener
    public void onServerReload(GameReloadEvent event) {
        SealBuilder.onReload();
    }
}
