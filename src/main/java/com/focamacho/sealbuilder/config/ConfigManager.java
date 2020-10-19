package com.focamacho.sealbuilder.config;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

@SuppressWarnings("UnstableApiUsage")
public class ConfigManager {

    private static ConfigurationLoader<CommentedConfigurationNode> loader;
    private static ConfigurationOptions options;
    private static ConfigurationNode node;

    public ConfigManager(ConfigurationLoader<CommentedConfigurationNode> loader) {
        ConfigManager.loader = loader;
        ConfigManager.options = ConfigurationOptions.defaults().setShouldCopyDefaults(true);
        load();
    }

    public static void load() {
        try {
            node = loader.load(options);
            node.getValue(TypeToken.of(PluginConfig.class), new PluginConfig());
            save();
        } catch (IOException | ObjectMappingException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            node.setValue(TypeToken.of(PluginConfig.class), new PluginConfig());
            loader.save(node);
        } catch (IOException | ObjectMappingException e) {
            e.printStackTrace();
        }
    }
}
