package com.focamacho.sealbuilder.config;

import com.focamacho.sealbuilder.config.lib.OverrideGrowth;
import com.focamacho.sealbuilder.config.lib.OverrideNature;
import com.focamacho.sealbuilder.config.lib.OverridePokeball;
import com.focamacho.sealbuilder.config.lib.OverrideStats;
import com.focamacho.seallibrary.util.JsonHandler;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.*;

import static com.focamacho.sealbuilder.SealBuilder.config;

public class OverridesConfig {

    public static Map<EnumSpecies, AbstractMap.SimpleEntry<String, Double>> createPriceOverrides = new HashMap<>();
    public static Map<EnumSpecies, AbstractMap.SimpleEntry<String, Double>> shinyPriceOverrides = new HashMap<>();
    public static Map<EnumSpecies, AbstractMap.SimpleEntry<String, Double>> hiddenAbilityPriceOverrides = new HashMap<>();
    public static Map<EnumSpecies, AbstractMap.SimpleEntry<String, Double>> genderPriceOverrides = new HashMap<>();

    public static Map<EnumPokeballs, AbstractMap.SimpleEntry<String, Double>> pokeballPriceOverridesGlobal = new HashMap<>();
    public static Map<EnumSpecies, List<OverridePokeball>> pokeballPriceOverrides = new HashMap<>();

    public static Map<EnumGrowth, AbstractMap.SimpleEntry<String, Double>> growthPriceOverridesGlobal = new HashMap<>();
    public static Map<EnumSpecies, List<OverrideGrowth>> growthPriceOverrides = new HashMap<>();

    public static Map<EnumNature, AbstractMap.SimpleEntry<String, Double>> naturePriceOverridesGlobal = new HashMap<>();
    public static Map<EnumSpecies, List<OverrideNature>> naturePriceOverrides = new HashMap<>();

    public static Map<StatsType, AbstractMap.SimpleEntry<String, Double>> ivsPriceOverridesGlobal = new HashMap<>();
    public static Map<EnumSpecies, List<OverrideStats>> ivsPriceOverrides = new HashMap<>();

    public static Map<EnumSpecies, String[]> blacklist = new HashMap<>();
    public static String[] modifierBlacklist = new String[]{};

    public static void initOverrides(File overridesFile) {
        try {
            createPriceOverrides.clear();
            shinyPriceOverrides.clear();
            hiddenAbilityPriceOverrides.clear();
            genderPriceOverrides.clear();

            pokeballPriceOverridesGlobal.clear();
            pokeballPriceOverrides.clear();

            growthPriceOverridesGlobal.clear();
            growthPriceOverrides.clear();

            naturePriceOverridesGlobal.clear();
            naturePriceOverrides.clear();

            ivsPriceOverridesGlobal.clear();
            ivsPriceOverrides.clear();

            blacklist.clear();
            modifierBlacklist = new String[]{};

            JSONObject json = JsonHandler.getOrCreateJson(overridesFile);

            //Override nos preços de Criação de Pokémons
            JSONArray array = JsonHandler.getOrCreateJsonArray(overridesFile, "createPriceOverrides");
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    EnumSpecies pokemon = EnumSpecies.getFromNameAnyCase(object.getString("pokemon"));
                    String currency = object.optString("currency", config.currencyId);
                    double price = object.getDouble("price");

                    if (object.getString("pokemon").equalsIgnoreCase("legendary")) {
                        for (EnumSpecies legendary : EnumSpecies.LEGENDARY_ENUMS) {
                            createPriceOverrides.put(legendary, new AbstractMap.SimpleEntry<>(currency, price));
                        }
                    } else if (pokemon != null) {
                        createPriceOverrides.put(pokemon, new AbstractMap.SimpleEntry<>(currency, price));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Override no preço do Shiny
            array = JsonHandler.getOrCreateJsonArray(overridesFile, "shinyPriceOverrides");
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    EnumSpecies pokemon = EnumSpecies.getFromNameAnyCase(object.getString("pokemon"));
                    String currency = object.optString("currency", config.currencyId);
                    double price = object.getDouble("price");

                    if (object.getString("pokemon").equalsIgnoreCase("legendary")) {
                        for (EnumSpecies legendary : EnumSpecies.LEGENDARY_ENUMS) {
                            shinyPriceOverrides.put(legendary, new AbstractMap.SimpleEntry<>(currency, price));
                        }
                    } else if (pokemon != null) {
                        shinyPriceOverrides.put(pokemon, new AbstractMap.SimpleEntry<>(currency, price));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Override nos preços do desbloqueio de Habilidades Ocultas
            array = JsonHandler.getOrCreateJsonArray(overridesFile, "hiddenAbilityPriceOverrides");
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    EnumSpecies pokemon = EnumSpecies.getFromNameAnyCase(object.getString("pokemon"));
                    String currency = object.optString("currency", config.currencyId);
                    double price = object.getDouble("price");

                    if (object.getString("pokemon").equalsIgnoreCase("legendary")) {
                        for (EnumSpecies legendary : EnumSpecies.LEGENDARY_ENUMS) {
                            hiddenAbilityPriceOverrides.put(legendary, new AbstractMap.SimpleEntry<>(currency, price));
                        }
                    } else if (pokemon != null) {
                        hiddenAbilityPriceOverrides.put(pokemon, new AbstractMap.SimpleEntry<>(currency, price));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Override nos preços de mudança de Gênero
            array = JsonHandler.getOrCreateJsonArray(overridesFile, "genderPriceOverrides");
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    EnumSpecies pokemon = EnumSpecies.getFromNameAnyCase(object.getString("pokemon"));
                    String currency = object.optString("currency", config.currencyId);
                    double price = object.getDouble("price");

                    if (object.getString("pokemon").equalsIgnoreCase("legendary")) {
                        for (EnumSpecies legendary : EnumSpecies.LEGENDARY_ENUMS) {
                            genderPriceOverrides.put(legendary, new AbstractMap.SimpleEntry<>(currency, price));
                        }
                    } else if (pokemon != null) {
                        genderPriceOverrides.put(pokemon, new AbstractMap.SimpleEntry<>(currency, price));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Override nos preços das Natures
            array = JsonHandler.getOrCreateJsonArray(overridesFile, "naturePriceOverrides");
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    EnumSpecies specie = EnumSpecies.getFromNameAnyCase(object.optString("pokemon"));
                    EnumNature nature = EnumNature.natureFromString(object.optString("nature"));
                    String currency = object.optString("currency", config.currencyId);
                    double price = object.getDouble("price");

                    if (object.optString("pokemon").equalsIgnoreCase("legendary")) {
                        for (EnumSpecies legendary : EnumSpecies.LEGENDARY_ENUMS) {
                            if (!naturePriceOverrides.containsKey(legendary))
                                naturePriceOverrides.put(legendary, new ArrayList<>());
                            naturePriceOverrides.get(legendary).add(new OverrideNature(nature, currency, price));
                        }
                    } else {
                        if (specie != null || nature != null) {
                            if (nature != null && specie == null) {
                                naturePriceOverridesGlobal.put(nature, new AbstractMap.SimpleEntry<>(currency, price));
                            } else {
                                if (!naturePriceOverrides.containsKey(specie))
                                    naturePriceOverrides.put(specie, new ArrayList<>());
                                naturePriceOverrides.get(specie).add(new OverrideNature(nature, currency, price));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Override nos preços das Pokébolas
            array = JsonHandler.getOrCreateJsonArray(overridesFile, "pokeballPriceOverrides");
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    EnumSpecies specie = EnumSpecies.getFromNameAnyCase(object.optString("pokemon"));
                    EnumPokeballs pokeball = EnumPokeballs.getPokeballFromString(object.optString("pokeball"));
                    String currency = object.optString("currency", config.currencyId);
                    double price = object.getDouble("price");

                    if (object.optString("pokemon").equalsIgnoreCase("legendary")) {
                        for (EnumSpecies legendary : EnumSpecies.LEGENDARY_ENUMS) {
                            if (!pokeballPriceOverrides.containsKey(legendary))
                                pokeballPriceOverrides.put(legendary, new ArrayList<>());
                            pokeballPriceOverrides.get(legendary).add(new OverridePokeball(pokeball, currency, price));
                        }
                    } else {
                        if (specie != null || pokeball != null) {
                            if (pokeball != null && specie == null) {
                                pokeballPriceOverridesGlobal.put(pokeball, new AbstractMap.SimpleEntry<>(currency, price));
                            } else {
                                if (!pokeballPriceOverrides.containsKey(specie))
                                    pokeballPriceOverrides.put(specie, new ArrayList<>());
                                pokeballPriceOverrides.get(specie).add(new OverridePokeball(pokeball, currency, price));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Override nos preços dos Tamanhos
            array = JsonHandler.getOrCreateJsonArray(overridesFile, "growthPriceOverrides");
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    EnumSpecies specie = EnumSpecies.getFromNameAnyCase(object.optString("pokemon"));
                    EnumGrowth growth = EnumGrowth.growthFromString(object.optString("growth"));
                    String currency = object.optString("currency", config.currencyId);
                    double price = object.getDouble("price");

                    if (object.optString("pokemon").equalsIgnoreCase("legendary")) {
                        for (EnumSpecies legendary : EnumSpecies.LEGENDARY_ENUMS) {
                            if (!growthPriceOverrides.containsKey(legendary))
                                growthPriceOverrides.put(legendary, new ArrayList<>());
                            growthPriceOverrides.get(legendary).add(new OverrideGrowth(growth, currency, price));
                        }
                    } else {
                        if (specie != null || growth != null) {
                            if (growth != null && specie == null) {
                                growthPriceOverridesGlobal.put(growth, new AbstractMap.SimpleEntry<>(currency, price));
                            } else {
                                if (!growthPriceOverrides.containsKey(specie))
                                    growthPriceOverrides.put(specie, new ArrayList<>());
                                growthPriceOverrides.get(specie).add(new OverrideGrowth(growth, currency, price));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Override nos preços dos IVs
            array = JsonHandler.getOrCreateJsonArray(overridesFile, "ivsPriceOverrides");
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    EnumSpecies specie = EnumSpecies.getFromNameAnyCase(object.optString("pokemon"));
                    String stts = object.optString("type");
                    StatsType statsType = null;

                    if (!stts.isEmpty()) {
                        for (StatsType value : StatsType.values()) {
                            if (value.toString().equalsIgnoreCase(stts)) {
                                statsType = value;
                                break;
                            }
                        }
                    }

                    String currency = object.optString("currency", config.currencyId);
                    double price = object.getDouble("price");

                    if (object.optString("pokemon").equalsIgnoreCase("legendary")) {
                        for (EnumSpecies legendary : EnumSpecies.LEGENDARY_ENUMS) {
                            if (!ivsPriceOverrides.containsKey(legendary))
                                ivsPriceOverrides.put(legendary, new ArrayList<>());
                            ivsPriceOverrides.get(legendary).add(new OverrideStats(statsType, currency, price));
                        }
                    } else {
                        if (specie != null || statsType != null) {
                            if (statsType != null && specie == null) {
                                ivsPriceOverridesGlobal.put(statsType, new AbstractMap.SimpleEntry<>(currency, price));
                            } else {
                                if (!ivsPriceOverrides.containsKey(specie))
                                    ivsPriceOverrides.put(specie, new ArrayList<>());
                                ivsPriceOverrides.get(specie).add(new OverrideStats(statsType, currency, price));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Carregar blackList
            JSONObject blacklistObject;
            if (json.has("blacklist")) blacklistObject = json.getJSONObject("blacklist");
            else {
                json.put("blacklist", new JSONObject());
                JsonHandler.saveToJson(overridesFile, json);
                blacklistObject = new JSONObject();
            }
            blacklistObject.keySet().forEach(key -> {
                EnumSpecies pokemon = EnumSpecies.getFromNameAnyCase(key);
                if (pokemon != null) {
                    JSONArray blacklistedModifiersArray = blacklistObject.getJSONArray(key);
                    String[] blacklistedModifiers = new String[blacklistedModifiersArray.length()];

                    for (int i = 0; i < blacklistedModifiersArray.length(); i++) {
                        blacklistedModifiers[i] = blacklistedModifiersArray.getString(i);
                    }

                    blacklist.put(pokemon, blacklistedModifiers);
                }
            });

            //Carregar blacklist de modifier
            JSONArray blacklistArray = JsonHandler.getOrCreateJsonArray(overridesFile, "modifierBlacklist");
            String[] blacklist = new String[blacklistArray.length()];
            for (int i = 0; i < blacklistArray.length(); i++) {
                blacklist[i] = blacklistArray.getString(i);
            }
            modifierBlacklist = blacklist;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
