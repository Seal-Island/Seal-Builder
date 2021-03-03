package com.focamacho.sealbuilder;

import com.focamacho.sealbuilder.command.BuilderCommand;
import com.focamacho.sealbuilder.config.SealBuilderConfig;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.config.SealBuilderOverrides;
import com.focamacho.seallibrary.config.ILangConfig;
import com.focamacho.seallibrary.config.SealConfig;
import com.focamacho.seallibrary.server.SealServer;

import java.io.File;

public class SealBuilder {

    private static SealConfig sealConfig;
    public static SealBuilderConfig config;
    public static SealBuilderOverrides overrides;
    public static ILangConfig lang;
    public static Object instance;

    public static void onEnable(Object plugin) {
        instance = plugin;

        File configFolder = new File(SealServer.get().getConfigFolder() + "SealBuilder/");
        sealConfig = new SealConfig(new File(configFolder, "lang/"), new SealBuilderLang());
        config = sealConfig.getConfig(new File(configFolder, "SealBuilder.json"), SealBuilderConfig.class);
        lang = sealConfig.getLangConfig();
        overrides = sealConfig.getConfig(new File(configFolder, "SealBuilder-Overrides.json"), SealBuilderOverrides.class);

        SealServer.get().registerCommand(instance, new BuilderCommand());
    }

    public static void onReload() {
        sealConfig.reload();
    }

}
