package com.focamacho.sealbuilder;

import com.focamacho.sealbuilder.command.BuilderCommand;
import com.focamacho.sealbuilder.config.ConfigManager;
import com.focamacho.sealbuilder.config.LangConfig;
import com.focamacho.sealbuilder.config.OverridesConfig;
import com.focamacho.sealbuilder.listener.CreatePokemonListener;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;

@Plugin(
        id = "sealbuilder",
        name = "Seal Builder",
        description = "PokeBuilder de Pixelmon Reforged para Sponge.",
        authors = {
                "Focamacho"
        }
)
public class SealBuilder {

    public static SealBuilder instance;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        new ConfigManager(configManager);
        loadLang();
        loadOverrides();

        Sponge.getCommandManager().register(this, CommandSpec.builder().executor(new BuilderCommand()).build(), "builder");

        Sponge.getEventManager().registerListeners(this, new CreatePokemonListener());
    }

    @Listener
    public void onReload(GameReloadEvent event) {
        ConfigManager.load();
        loadLang();
        loadOverrides();
    }

    private void loadLang() {
        try {
            LangConfig.initLang();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadOverrides() {
        try {
            OverridesConfig.initOverrides();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
