package com.focamacho.sealbuilder.util;

import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.config.SealBuilderOverrides;
import com.focamacho.sealbuilder.config.lib.ModuleTypes;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;

import java.util.ArrayList;
import java.util.List;

import static com.focamacho.sealbuilder.SealBuilder.overrides;

public class ConfigUtils {

    public static String getCurrencyOverrides(EnumSpecies specie, ModuleTypes module, String value) {
        switch(module) {
            case CREATE:
                for(SealBuilderOverrides.PriceOverride priceOverride : overrides.createPriceOverrides) {
                    if (priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        return priceOverride.currency;
                    }
                }
                break;
            case SHINY:
                for(SealBuilderOverrides.PriceOverride priceOverride : overrides.shinyPriceOverrides) {
                    if (priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        return priceOverride.currency;
                    }
                }
                break;
            case HIDDEN_ABILITY:
                for(SealBuilderOverrides.PriceOverride priceOverride : overrides.hiddenAbilityPriceOverrides) {
                    if (priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        return priceOverride.currency;
                    }
                }
                break;
            case GENDER:
                for(SealBuilderOverrides.PriceOverride priceOverride : overrides.genderPriceOverrides) {
                    if (priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        return priceOverride.currency;
                    }
                }
                break;
            case NATURE:
                for(SealBuilderOverrides.ValuePriceOverride priceOverride : overrides.naturePriceOverrides) {
                    if (priceOverride.pokemon == null || priceOverride.pokemon.isEmpty() || priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        if(priceOverride.value == null || priceOverride.value.isEmpty() || priceOverride.value.equalsIgnoreCase(value)) return priceOverride.currency;
                    }
                }
                break;
            case POKEBALL:
                for(SealBuilderOverrides.ValuePriceOverride priceOverride : overrides.pokeballPriceOverrides) {
                    if (priceOverride.pokemon == null || priceOverride.pokemon.isEmpty() || priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        if(priceOverride.value == null || priceOverride.value.isEmpty() || priceOverride.value.equalsIgnoreCase(value)) return priceOverride.currency;
                    }
                }
                break;
            case GROWTH:
                for(SealBuilderOverrides.ValuePriceOverride priceOverride : overrides.growthPriceOverrides) {
                    if (priceOverride.pokemon == null || priceOverride.pokemon.isEmpty() || priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        if(priceOverride.value == null || priceOverride.value.isEmpty() || priceOverride.value.equalsIgnoreCase(value)) return priceOverride.currency;
                    }
                }
                break;
            case IVS:
                for(SealBuilderOverrides.ValuePriceOverride priceOverride : overrides.ivsPriceOverrides) {
                    if (priceOverride.pokemon == null || priceOverride.pokemon.isEmpty() || priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        if(priceOverride.value == null || priceOverride.value.isEmpty() || priceOverride.value.equalsIgnoreCase(value)) return priceOverride.currency;
                    }
                }
        }

        return null;
    }

    public static Double getPriceOverrides(EnumSpecies specie, ModuleTypes types, String value) {
        switch(types) {
            case CREATE:
                for(SealBuilderOverrides.PriceOverride priceOverride : overrides.createPriceOverrides) {
                    if (priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        return priceOverride.price;
                    }
                }
                break;
            case SHINY:
                for(SealBuilderOverrides.PriceOverride priceOverride : overrides.shinyPriceOverrides) {
                    if (priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        return priceOverride.price;
                    }
                }
                break;
            case HIDDEN_ABILITY:
                for(SealBuilderOverrides.PriceOverride priceOverride : overrides.hiddenAbilityPriceOverrides) {
                    if (priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        return priceOverride.price;
                    }
                }
                break;
            case GENDER:
                for(SealBuilderOverrides.PriceOverride priceOverride : overrides.genderPriceOverrides) {
                    if (priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        return priceOverride.price;
                    }
                }
                break;
            case NATURE:
                for(SealBuilderOverrides.ValuePriceOverride priceOverride : overrides.naturePriceOverrides) {
                    if (priceOverride.pokemon == null || priceOverride.pokemon.isEmpty() || priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        if(priceOverride.value == null || priceOverride.value.isEmpty() || priceOverride.value.equalsIgnoreCase(value)) return priceOverride.price;
                    }
                }
                break;
            case POKEBALL:
                for(SealBuilderOverrides.ValuePriceOverride priceOverride : overrides.pokeballPriceOverrides) {
                    if (priceOverride.pokemon == null || priceOverride.pokemon.isEmpty() || priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        if(priceOverride.value == null || priceOverride.value.isEmpty() || priceOverride.value.equalsIgnoreCase(value)) return priceOverride.price;
                    }
                }
                break;
            case GROWTH:
                for(SealBuilderOverrides.ValuePriceOverride priceOverride : overrides.growthPriceOverrides) {
                    if (priceOverride.pokemon == null || priceOverride.pokemon.isEmpty() || priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        if(priceOverride.value == null || priceOverride.value.isEmpty() || priceOverride.value.equalsIgnoreCase(value)) return priceOverride.price;
                    }
                }
                break;
            case IVS:
                for(SealBuilderOverrides.ValuePriceOverride priceOverride : overrides.ivsPriceOverrides) {
                    if (priceOverride.pokemon == null || priceOverride.pokemon.isEmpty() || priceOverride.pokemon.equalsIgnoreCase(specie.name)) {
                        if(priceOverride.value == null || priceOverride.value.isEmpty() || priceOverride.value.equalsIgnoreCase(value)) return priceOverride.price;
                    }
                }
        }

        return null;
    }

    public static boolean isModifierDisabled(ModuleTypes type) {
        if(overrides.blacklist.containsKey("all")) {
            for(String modifier : overrides.blacklist.get("all")) {
                if(type.getKey().equalsIgnoreCase(modifier)) return true;
            }
        }
        return false;
    }

    public static boolean isBlacklisted(EnumSpecies specie, ModuleTypes type) {
        if(overrides.blacklist.containsKey(specie.name)) {
            for (String s : overrides.blacklist.get(specie.name)) {
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
