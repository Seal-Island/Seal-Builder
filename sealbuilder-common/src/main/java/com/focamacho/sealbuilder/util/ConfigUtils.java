package com.focamacho.sealbuilder.util;

import com.focamacho.sealbuilder.config.OverridesConfig;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.config.lib.*;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigUtils {

    public static String getCurrencyOverrides(EnumSpecies specie, ModuleTypes module, String value) {
        switch(module) {
            case CREATE:
                if(OverridesConfig.createPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.createPriceOverrides.get(specie).getKey();
                }
                break;
            case SHINY:
                if(OverridesConfig.shinyPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.shinyPriceOverrides.get(specie).getKey();
                }
                break;
            case HIDDEN_ABILITY:
                if(OverridesConfig.hiddenAbilityPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.hiddenAbilityPriceOverrides.get(specie).getKey();
                }
                break;
            case GENDER:
                if(OverridesConfig.genderPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.genderPriceOverrides.get(specie).getKey();
                }
                break;
            case NATURE:
                EnumNature nature = EnumNature.natureFromString(value);
                if (OverridesConfig.naturePriceOverrides.containsKey(specie)) {
                    List<OverrideNature> natureOverrides = OverridesConfig.naturePriceOverrides.get(specie);
                    for (OverrideNature override : natureOverrides) {
                        if (override.nature == nature) return override.currency;
                    }
                }
                if (OverridesConfig.naturePriceOverridesGlobal.containsKey(nature)) {
                    return OverridesConfig.naturePriceOverridesGlobal.get(nature).getKey();
                }
                break;
            case POKEBALL:
                EnumPokeballs pokeball = EnumPokeballs.getPokeballFromString(value);
                if (OverridesConfig.pokeballPriceOverrides.containsKey(specie)) {
                    List<OverridePokeball> pokeballOverrides = OverridesConfig.pokeballPriceOverrides.get(specie);
                    for (OverridePokeball override : pokeballOverrides) {
                        if (override.pokeball == pokeball) return override.currency;
                    }
                }
                if (OverridesConfig.pokeballPriceOverridesGlobal.containsKey(pokeball)) {
                    return OverridesConfig.pokeballPriceOverridesGlobal.get(pokeball).getKey();
                }
                break;
            case GROWTH:
                EnumGrowth growth = EnumGrowth.growthFromString(value);
                if (OverridesConfig.growthPriceOverrides.containsKey(specie)) {
                    List<OverrideGrowth> growthOverrides = OverridesConfig.growthPriceOverrides.get(specie);
                    for (OverrideGrowth override : growthOverrides) {
                        if (override.growth == growth) return override.currency;
                    }
                }
                if (OverridesConfig.growthPriceOverridesGlobal.containsKey(growth)) {
                    return OverridesConfig.growthPriceOverridesGlobal.get(growth).getKey();
                }
                break;
            case IVS:
                StatsType stats = (StatsType) Arrays.stream(StatsType.values()).filter(type -> type.toString().equalsIgnoreCase(value)).toArray()[0];
                if (OverridesConfig.ivsPriceOverrides.containsKey(specie)) {
                    List<OverrideStats> statsOverrides = OverridesConfig.ivsPriceOverrides.get(specie);
                    for (OverrideStats override : statsOverrides) {
                        if (override.statsType == stats) return override.currency;
                    }
                }
                if (OverridesConfig.ivsPriceOverridesGlobal.containsKey(stats)) {
                    return OverridesConfig.ivsPriceOverridesGlobal.get(stats).getKey();
                }
        }

        return null;
    }

    public static Double getPriceOverrides(EnumSpecies specie, ModuleTypes types, String value) {
        switch(types) {
            case CREATE:
                if(OverridesConfig.createPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.createPriceOverrides.get(specie).getValue();
                }
                break;
            case SHINY:
                if(OverridesConfig.shinyPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.shinyPriceOverrides.get(specie).getValue();
                }
                break;
            case HIDDEN_ABILITY:
                if(OverridesConfig.hiddenAbilityPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.hiddenAbilityPriceOverrides.get(specie).getValue();
                }
                break;
            case GENDER:
                if(OverridesConfig.genderPriceOverrides.containsKey(specie)) {
                    return OverridesConfig.genderPriceOverrides.get(specie).getValue();
                }
                break;
            case NATURE:
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
            case POKEBALL:
                EnumPokeballs pokeball = EnumPokeballs.getPokeballFromString(value);
                if (OverridesConfig.pokeballPriceOverrides.containsKey(specie)) {
                    List<OverridePokeball> pokeballOverrides = OverridesConfig.pokeballPriceOverrides.get(specie);
                    for (OverridePokeball override : pokeballOverrides) {
                        if (override.pokeball == pokeball) return override.value;
                    }
                }
                if (OverridesConfig.pokeballPriceOverridesGlobal.containsKey(pokeball)) {
                    return OverridesConfig.pokeballPriceOverridesGlobal.get(pokeball).getValue();
                }
                break;
            case GROWTH:
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
            case IVS:
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

    public static boolean isModifierDisabled(ModuleTypes type) {
        for(String modifier : OverridesConfig.modifierBlacklist) {
            if(type.getKey().equalsIgnoreCase(modifier)) return true;
        }
        return false;
    }

    public static boolean isBlacklisted(EnumSpecies specie, ModuleTypes type) {
        if(OverridesConfig.blacklist.containsKey(specie)) {
            for (String s : OverridesConfig.blacklist.get(specie)) {
                if (s.equalsIgnoreCase(type.getKey())) return true;
            }
        }
        return false;
    }

    public static boolean isBlacklisted(Pokemon pokemon, ModuleTypes type) {
        return isBlacklisted(pokemon.getSpecies(), type);
    }

    public static List<String> getBlacklistedModifiers(EnumSpecies species) {
        List<String> blacklisted = new ArrayList<>();

        for (ModuleTypes modifier : ModuleTypes.values()) {
            if(isBlacklisted(species, modifier)) blacklisted.add(SealBuilderLang.getLang("modifier." + modifier.getKey()));
            if(!blacklisted.contains(SealBuilderLang.getLang("modifier." + modifier.getKey()))) {
                if(isModifierDisabled(modifier)) blacklisted.add(SealBuilderLang.getLang("modifier." + modifier.getKey()));
            }
        }

        return blacklisted;
    }

}
