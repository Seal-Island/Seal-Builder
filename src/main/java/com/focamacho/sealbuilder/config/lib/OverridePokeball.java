package com.focamacho.sealbuilder.config.lib;

import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;

public class OverridePokeball {

    public final String currency;
    public final Double value;
    public final EnumPokeballs pokeball;

    public OverridePokeball(EnumPokeballs pokeball, String currency, double value) {
        this.pokeball = pokeball;
        this.currency = currency;
        this.value = value;
    }

}
