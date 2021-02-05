package com.focamacho.sealbuilder.util;

import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.seallibrary.economy.EconomyHandler;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.specialAttacks.basic.HiddenPower;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Stats;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtils {

    public static final DecimalFormat df = new DecimalFormat("#0.##");

    public static String getFormattedText(String text) {
        return "§r" + text.replace("&", "§");
    }

    public static String getFormattedText(String text, ISealPlayer player) {
        return getFormattedText(text.replace("%player%", player.getName()));
    }

    public static String getFormattedText(String text, Pokemon pokemon, ISealPlayer player) {
        EnumNature nature = pokemon.getNature();
        Stats stats = pokemon.getStats();
        Moveset moveset = pokemon.getMoveset();

        IVStore ivStore = null;
        EVStore evStore = null;

        int ivSum = 0;
        int evSum = 0;

        if(stats != null) {
            ivStore = stats.ivs;
            evStore = stats.evs;
            ivSum = Arrays.stream(ivStore.getArray()).sum();
            evSum = Arrays.stream(evStore.getArray()).sum();
        }

        String pokemonPlaceholders = text.replace("%pokemonname%", pokemon.getSpecies().getPokemonName())
                .replace("%pokemonlvl%", "" + pokemon.getLevel())
                .replace("%pokemonshiny%", pokemon.isShiny() ? SealBuilderLang.getLang("yes") : SealBuilderLang.getLang("no"))
                .replace("%pokemongender%", SealBuilderLang.getLang("gender." + pokemon.getGender().name().toLowerCase()))
                .replace("%pokemonball%", pokemon.getCaughtBall().name())
                .replace("%pokemonability%", pokemon.getAbilityName())
                .replace("%pokemonsize%", SealBuilderLang.getLang("growth." + pokemon.getGrowth().name().toLowerCase()))
                .replace("%pokemonnature%", nature.getLocalizedName())
                .replace("%pokemonnatureinc%", SealBuilderLang.getLang("statstype." + nature.increasedStat.name().toLowerCase()))
                .replace("%pokemonnaturedec%", SealBuilderLang.getLang("statstype." + nature.decreasedStat.name().toLowerCase()))
                .replace("%pokemonnatureincc%", SealBuilderLang.getLang("statstype." + nature.increasedStat.name().toLowerCase() + ".compact"))
                .replace("%pokemonnaturedecc%", SealBuilderLang.getLang("statstype." + nature.decreasedStat.name().toLowerCase() + ".compact"))
                .replace("%pokemonhappiness%", "" + pokemon.getFriendship())
                .replace("%pokemonhiddenpower%", HiddenPower.getHiddenPowerType(pokemon.getIVs()).getLocalizedName())
                .replace("%pokemonivsum%", "" + ivSum)
                .replace("%pokemonivhp%", ivStore != null ? "" + ivStore.hp : "0")
                .replace("%pokemonivatk%", ivStore != null ? "" + ivStore.attack : "0")
                .replace("%pokemonivdef%", ivStore != null ? "" + ivStore.defence : "0")
                .replace("%pokemonivspa%", ivStore != null ? "" + ivStore.specialAttack : "0")
                .replace("%pokemonivspd%", ivStore != null ? "" + ivStore.specialDefence : "0")
                .replace("%pokemonivspe%", ivStore != null ? "" + ivStore.speed : "0")
                .replace("%pokemonevsum%", "" + evSum)
                .replace("%pokemonevhp%", evStore != null ? "" + evStore.hp : "0")
                .replace("%pokemonevatk%", evStore != null ? "" + evStore.attack : "0")
                .replace("%pokemonevdef%", evStore != null ? "" + evStore.defence : "0")
                .replace("%pokemonevspa%", evStore != null ? "" + evStore.specialAttack : "0")
                .replace("%pokemonevspd%", evStore != null ? "" + evStore.specialDefence : "0")
                .replace("%pokemonevspe%", evStore != null ? "" + evStore.speed : "0")
                .replace("%pokemonmoveone%", moveset.get(0) == null ? SealBuilderLang.getLang("none") : moveset.get(0).getActualMove().getLocalizedName())
                .replace("%pokemonmovetwo%", moveset.get(1) == null ? SealBuilderLang.getLang("none") : moveset.get(1).getActualMove().getLocalizedName())
                .replace("%pokemonmovethree%", moveset.get(2) == null ? SealBuilderLang.getLang("none") : moveset.get(2).getActualMove().getLocalizedName())
                .replace("%pokemonmovefour%", moveset.get(3) == null ? SealBuilderLang.getLang("none") : moveset.get(3).getActualMove().getLocalizedName())
                .replace("%pokemonivpercentage%", df.format((int) ((double) ivSum / 186.0 * 100.0)))
                .replace("%pokemonevpercentage%", df.format((int) ((double) evSum / 510.0 * 100.0)));

        return getFormattedText(pokemonPlaceholders, player);
    }

    private static String getBlacklistedModifiers(EnumSpecies specie) {
        StringBuilder blacklist = new StringBuilder();
        List<String> blacklisted = ConfigUtils.getBlacklistedModifiers(specie);
        if(!blacklisted.isEmpty()) {
            blacklisted.forEach(modifier -> blacklist.append("\n").append(modifier));
        }
        return blacklist.toString();
    }

    public static List<String> getFormattedLore(String text, Pokemon pokemon, ISealPlayer player) {
        List<String> lore = new ArrayList<>();

        text = text.replace("%blacklistwarn%", ConfigUtils.getBlacklistedModifiers(pokemon.getSpecies()).size() > 1 ? SealBuilderLang.getLang("menu.create.blacklistwarn") : "").replace("%blacklistedmodifiers%", getBlacklistedModifiers(pokemon.getSpecies()));

        for(String line : text.split("\n")) {
            lore.add(getFormattedText(line, pokemon, player));
        }
        return lore;
    }

    public static String getFormattedCurrency(String text, String currency, double price) {
        return text.replace("%currencykey%", EconomyHandler.getCurrencySymbol(currency))
                .replace("%currencyname%", EconomyHandler.getCurrencyPlural(currency))
                .replace("%price%", TextUtils.df.format(price));
    }

}
