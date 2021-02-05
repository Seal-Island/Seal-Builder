package com.focamacho.sealbuilder.config.lib;

import com.pixelmonmod.pixelmon.enums.EnumNature;

public class OverrideNature {

    public final String currency;
    public final Double value;
    public final EnumNature nature;

    public OverrideNature(EnumNature nature, String currency, double value) {
        this.nature = nature;
        this.currency = currency;
        this.value = value;
    }

}
