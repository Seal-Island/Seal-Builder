package com.focamacho.sealbuilder;

import com.focamacho.sealbuilder.config.OverridesConfig;
import com.focamacho.sealbuilder.config.SealBuilderConfig;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.seallibrary.config.ILangConfig;
import com.focamacho.seallibrary.config.SealConfig;

import java.io.File;

public class SealBuilder {

    private static SealConfig sealConfig;
    public static SealBuilderConfig config;
    public static ILangConfig lang;
    public static Object instance;

    public static void onEnable(Object plugin, File configFolder) {
        instance = plugin;
        sealConfig = new SealConfig(new File(configFolder, "lang/"), new SealBuilderLang());
        SealBuilder.config = sealConfig.getConfig(new File(configFolder, "SealBuilder.json"), SealBuilderConfig.class);
        SealBuilder.lang = sealConfig.getLangConfig();
        OverridesConfig.initOverrides(new File(configFolder, "SealBuilder-Overrides.json"));
    }

    public static void onReload() {
        sealConfig.reload();
    }

}
