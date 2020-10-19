package com.focamacho.sealbuilder.config.lib;

import com.pixelmonmod.pixelmon.enums.EnumGrowth;

public class OverrideGrowth {

    public final String currency;
    public final Double value;
    public final EnumGrowth growth;

    public OverrideGrowth(EnumGrowth growth, String currency, double value) {
        this.growth = growth;
        this.currency = currency;
        this.value = value;
    }

}
