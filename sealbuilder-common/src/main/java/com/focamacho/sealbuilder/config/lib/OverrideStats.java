package com.focamacho.sealbuilder.config.lib;

import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;

public class OverrideStats {

    public final String currency;
    public final Double value;
    public final StatsType statsType;

    public OverrideStats(StatsType statsType, String currency, double value) {
        this.statsType = statsType;
        this.currency = currency;
        this.value = value;
    }

}
