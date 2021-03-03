package com.focamacho.sealbuilder;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class SealBuilderBukkit extends JavaPlugin {

    @Override
    public void onEnable() {
        SealBuilder.onEnable(this);
    }

    @Override
    public void reloadConfig() {
        SealBuilder.onReload();
    }

}
