package com.focamacho.sealbuilder.util;

import com.focamacho.sealbuilder.config.LangConfig;
import com.focamacho.sealbuilder.config.OverridesConfig;
import com.focamacho.sealbuilder.config.lib.OverrideGrowth;
import com.focamacho.sealbuilder.config.lib.OverrideNature;
import com.focamacho.sealbuilder.config.lib.OverridePokeball;
import com.focamacho.sealbuilder.config.lib.OverrideStats;
import com.focamacho.seallibrary.util.MoneyUtils;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.Currency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigUtils {

    public static Currency getCurrencyOverrides(EnumSpecies specie, String key, String value) {
        switch(key) {
            case "create":
                if(OverridesConfig.createPriceOverrides.containsKey(specie)) {
                    return MoneyUtils.getCurrencyByIdOrDefault(OverridesConfig.createPriceOverrides.get(specie).getKey());
                }
                break;
            case "shiny":
                if(OverridesConfig.shinyPriceOverrides.containsKey(specie)) {
                    return MoneyUtils.getCurrencyByIdOrDefault(OverridesConfig.shinyPriceOverrides.get(specie).getKey());
                }
                break;
            case "hiddenability":
                if(OverridesConfig.hiddenAbilityPriceOverrides.containsKey(specie)) {
                    return MoneyUtils.getCurrencyByIdOrDefault(OverridesConfig.hiddenAbilityPriceOverrides.get(specie).getKey());
                }
                break;
            case "gender":
                if(OverridesConfig.genderPriceOverrides.containsKey(specie)) {
                    return MoneyUtils.getCurrencyByIdOrDefault(OverridesConfig.genderPriceOverrides.get(specie).getKey());
                }
                break;
            case "nature":
                EnumNature nature = EnumNature.natureFromString(value);
                if (OverridesConfig.naturePriceOverrides.containsKey(specie)) {
                    List<OverrideNature> natureOverrides = OverridesConfig.naturePriceOverrides.get(specie);
                    for (OverrideNature override : natureOverrides) {
                        if (override.nature == nature) return MoneyUtils.getCurrencyByIdOrDefault(override.currency);
                    }
                }
                if (OverridesConfig.naturePriceOverridesGlobal.containsKey(nature)) {
                    return MoneyUtils.getCurrencyByIdOrDefault(OverridesConfig.naturePriceOverridesGlobal.get(nature).getKey());
                }
                break;
            case "pokeball":
                EnumPokeballs pokeball = EnumPokeballs.getPokeballFromString(value);
                if (OverridesConfig.pokeballPriceOverrides.containsKey(specie)) {
                    List<OverridePokeball> pokeballOverrides = OverridesConfig.pokeballPriceOverrides.get(specie);
                    for (OverridePokeball override : pokeballOverrides) {
                        if (override.pokeball == pokeball) return MoneyUtils.getCurrencyByIdOrDefault(override.currency);
                    }
                }
                if (OverridesConfig.pokeballPriceOverridesGlobal.containsKey(pokeball)) {
                    return MoneyUtils.getCurrencyByIdOrDefault(OverridesConfig.naturePriceOverridesGlobal.get(pokeball).getKey());
                }
                break;
            case "growth":
                EnumGrowth growth = EnumGrowth.growthFromString(value);
                if (OverridesConfig.growthPriceOverrides.containsKey(specie)) {
                    List<OverrideGrowth> growthOverrides = OverridesConfig.growthPriceOverrides.get(specie);
                    for (OverrideGrowth override : growthOverrides) {
                        if (override.growth == growth) return MoneyUtils.getCurrencyByIdOrDefault(override.currency);
                    }
                }
                if (OverridesConfig.growthPriceOverridesGlobal.containsKey(growth)) {
                    return MoneyUtils.getCurrencyByIdOrDefault(OverridesConfig.growthPriceOverridesGlobal.get(growth).getKey());
                }
                break;
            case "ivs":
                StatsType stats = (StatsType) Arrays.stream(StatsType.values()).filter(type -> type.toString().equalsIgnoreCase(value)).toArray()[0];
                if (OverridesConfig.ivsPriceOverrides.containsKey(specie)) {
                    List<OverrideStats> statsOverrides = OverridesConfig.ivsPriceOverrides.get(specie);
                    for (OverrideStats override : statsOverrides) {
                        if (override.statsType == stats) return MoneyUtils.getCurrencyByIdOrDefault(override.currency);
                    }
                }
                if (OverridesConfig.ivsPriceOverridesGlobal.containsKey(stats)) {
                    return MoneyUtils.getCurrencyByIdOrDefault(OverridesConfig.ivsPriceOverridesGlobal.get(stats).getKey());
                }
        }

        return null;
    }

    public static Double getPriceOverrides(EnumSpecies specie, String key, String value) {
        switch(key) {
            case "create":
                if(OverridesConfig.createPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.createPriceOverrides.get(specie).getValue();
                }
                break;
            case "shiny":
                if(OverridesConfig.shinyPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.shinyPriceOverrides.get(specie).getValue();
                }
                break;
            case "hiddenability":
                if(OverridesConfig.hiddenAbilityPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.hiddenAbilityPriceOverrides.get(specie).getValue();
                }
                break;
            case "gender":
                if(OverridesConfig.genderPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.genderPriceOverrides.get(specie).getValue();
                }
                break;
            case "nature":
                EnumNature nature = EnumNature.natureFromString(value);
                if(OverridesConfig.naturePriceOverrides.containsKey(specie)) {
                    List<OverrideNature> natureOverrides = OverridesConfig.naturePriceOverrides.get(specie);
                    for(OverrideNature override : natureOverrides) {
                        if(override.nature == nature) return override.value;
                    }
                }
                if(OverridesConfig.naturePriceOverridesGlobal.containsKey(nature)) {
                    return OverridesConfig.naturePriceOverridesGlobal.get(nature).getValue();
                }
                break;
            case "pokeball":
                EnumPokeballs pokeball = EnumPokeballs.getPokeballFromString(value);
                if (OverridesConfig.pokeballPriceOverrides.containsKey(specie)) {
                    List<OverridePokeball> pokeballOverrides = OverridesConfig.pokeballPriceOverrides.get(specie);
                    for (OverridePokeball override : pokeballOverrides) {
                        if (override.pokeball == pokeball) return override.value;
                    }
                }
                if (OverridesConfig.pokeballPriceOverridesGlobal.containsKey(pokeball)) {
                    return OverridesConfig.naturePriceOverridesGlobal.get(pokeball).getValue();
                }
                break;
            case "growth":
                EnumGrowth growth = EnumGrowth.growthFromString(value);
                if (OverridesConfig.growthPriceOverrides.containsKey(specie)) {
                    List<OverrideGrowth> growthOverrides = OverridesConfig.growthPriceOverrides.get(specie);
                    for (OverrideGrowth override : growthOverrides) {
                        if (override.growth == growth) return override.value;
                    }
                }
                if (OverridesConfig.growthPriceOverridesGlobal.containsKey(growth)) {
                    return OverridesConfig.growthPriceOverridesGlobal.get(growth).getValue();
                }
                break;
            case "ivs":
                StatsType stats = (StatsType) Arrays.stream(StatsType.values()).filter(type -> type.toString().equalsIgnoreCase(value)).toArray()[0];
                if (OverridesConfig.ivsPriceOverrides.containsKey(specie)) {
                    List<OverrideStats> statsOverrides = OverridesConfig.ivsPriceOverrides.get(specie);
                    for (OverrideStats override : statsOverrides) {
                        if (override.statsType == stats) return override.value;
                    }
                }
                if (OverridesConfig.ivsPriceOverridesGlobal.containsKey(stats)) {
                    return OverridesConfig.ivsPriceOverridesGlobal.get(stats).getValue();
                }
        }

        return null;
    }

    public static boolean isModifierDisabled(String key) {
        for(String modifier : OverridesConfig.modifierBlacklist) {
            if(key.equalsIgnoreCase(modifier)) return true;
        }
        return false;
    }

    public static boolean isBlacklisted(EnumSpecies specie, String key) {
        if(OverridesConfig.blacklist.containsKey(specie)) {
            for (String s : OverridesConfig.blacklist.get(specie)) {
                if (s.equalsIgnoreCase(key)) return true;
            }
        }
        return false;
    }

    public static boolean isBlacklisted(Pokemon pokemon, String key) {
        return isBlacklisted(pokemon.getSpecies(), key);
    }

    public static List<String> getBlacklistedModifiers(EnumSpecies species) {
        String[] modifiers = {"pokeball", "shiny", "growth", "hiddenability", "gender", "nature", "ivs"};
        List<String> blacklisted = new ArrayList<>();

        for (String modifier : modifiers) {
            if(isBlacklisted(species, modifier)) blacklisted.add(LangConfig.get("modifiers." + modifier));
            if(!blacklisted.contains(LangConfig.get("modifiers." + modifier))) {
                if(isModifierDisabled(modifier)) blacklisted.add(LangConfig.get("modifiers." + modifier));
            }
        }

        return blacklisted;
    }

    public static double applyDiscount(double price, Player player) {
        return (price / 100) * (100 - Double.parseDouble(player.getOption("sealbuilder.discount").orElse("0")));
    }
}
