package com.focamacho.sealbuilder.config.lib;

public enum ModuleTypes {

    CREATE("create"),
    SHINY("shiny"),
    HIDDEN_ABILITY("hiddenability"),
    GENDER("gender"),
    NATURE("nature"),
    POKEBALL("pokeball"),
    GROWTH("growth"),
    IVS("ivs");

    private final String key;

    ModuleTypes(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
