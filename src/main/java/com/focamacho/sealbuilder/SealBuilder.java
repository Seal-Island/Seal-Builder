package com.focamacho.sealbuilder;

import com.focamacho.sealbuilder.command.BuilderCommand;
import com.focamacho.sealbuilder.config.OverridesConfig;
import com.focamacho.sealbuilder.config.SealBuilderConfig;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.listener.CreatePokemonListener;
import com.focamacho.seallibrary.common.config.ILangConfig;
import com.focamacho.seallibrary.common.config.SealConfig;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;

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

    public SealConfig sealConfig;
    public static SealBuilderConfig config;
    public static ILangConfig lang;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;

        sealConfig = new SealConfig(new File("./config/SealBuilder/lang/"), new SealBuilderLang());
        sealConfig.getConfig(new File("./config/SealBuilder/SealBuilder.json"), SealBuilderConfig.class);
        lang = sealConfig.getLangConfig();

        loadOverrides();

        Sponge.getCommandManager().register(this, CommandSpec.builder()./*arguments(GenericArguments.optional(GenericArguments.player(Text.of("player")))).*/executor(new BuilderCommand()).build(), "builder");

        Sponge.getEventManager().registerListeners(this, new CreatePokemonListener());
    }

    @Listener
    public void onReload(GameReloadEvent event) {
        sealConfig.reload();
        loadOverrides();
    }

    private void loadOverrides() {
        try {
            OverridesConfig.initOverrides();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
