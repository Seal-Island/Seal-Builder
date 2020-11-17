package com.focamacho.sealbuilder.util;

import com.focamacho.sealbuilder.config.LangConfig;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.attacks.specialAttacks.basic.HiddenPower;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Stats;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtils {

    private static DecimalFormat ivPercentageFormat = new DecimalFormat("#0.##");

    public static Text getFormattedText(String text) {
        return Text.of("§r" + text.replace("&", "§"));
    }

    public static Text getFormattedText(String text, Player player) {
        return getFormattedText(text.replace("%discountlore%", player.getOption("sealbuilder.discount").isPresent() ? LangConfig.get("discount.applied").replace("%discount%", player.getOption("sealbuilder.discount").get() + "%") : ("")));
    }

    public static Text getFormattedText(String text, Pokemon pokemon, Player player) {
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
                .replace("%pokemonshiny%", pokemon.isShiny() ? LangConfig.get("yes") : LangConfig.get("no"))
                .replace("%pokemongender%", LangConfig.get("gender." + pokemon.getGender().name().toLowerCase()))
                .replace("%pokemonball%", pokemon.getCaughtBall().name())
                .replace("%pokemonability%", pokemon.getAbilityName())
                .replace("%pokemonsize%", LangConfig.get("growth." + pokemon.getGrowth().name().toLowerCase()))
                .replace("%pokemonnature%", nature.getName())
                .replace("%pokemonnatureinc%", LangConfig.get("statstype." + nature.increasedStat.name().toLowerCase()))
                .replace("%pokemonnaturedec%", LangConfig.get("statstype." + nature.decreasedStat.name().toLowerCase()))
                .replace("%pokemonnatureincc%", LangConfig.get("statstype." + nature.increasedStat.name().toLowerCase() + ".compact"))
                .replace("%pokemonnaturedecc%", LangConfig.get("statstype." + nature.decreasedStat.name().toLowerCase() + ".compact"))
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
                .replace("%pokemonmoveone%", moveset.get(0) == null ? LangConfig.get("none") : moveset.get(0).getActualMove().getLocalizedName())
                .replace("%pokemonmovetwo%", moveset.get(1) == null ? LangConfig.get("none") : moveset.get(1).getActualMove().getLocalizedName())
                .replace("%pokemonmovethree%", moveset.get(2) == null ? LangConfig.get("none") : moveset.get(2).getActualMove().getLocalizedName())
                .replace("%pokemonmovefour%", moveset.get(3) == null ? LangConfig.get("none") : moveset.get(3).getActualMove().getLocalizedName())
                .replace("%pokemonivpercentage%", ivPercentageFormat.format((int) ((double) ivSum / 186.0 * 100.0)))
                .replace("%pokemonevpercentage%", ivPercentageFormat.format((int) ((double) evSum / 510.0 * 100.0)));

        return getFormattedText(pokemonPlaceholders, player);
    }

    private static String getBlacklistedModifiers(EnumSpecies specie) {
        StringBuilder blacklist = new StringBuilder();
        List<String> blacklisted = ConfigUtils.getBlacklistedModifiers(specie);
        if(!blacklisted.isEmpty()) {
            blacklist.append(LangConfig.get("menu.create.blacklistwarn"));
            blacklisted.forEach(modifier -> {
                blacklist.append("\n").append(modifier);
            });
        }
        return blacklist.toString();
    }

    public static List<Text> getFormattedLore(String text, Pokemon pokemon, Player player) {
        List<Text> lore = new ArrayList<>();

        text = text.replace("%discountlore%", player.getOption("sealbuilder.discount").isPresent() ? LangConfig.get("discount.applied").replace("%discount%", player.getOption("sealbuilder.discount").get() + "%") : "");
        text = text.replace("%blacklistwarn%", ConfigUtils.getBlacklistedModifiers(pokemon.getSpecies()).size() > 1 ? LangConfig.get("menu.create.blacklistwarn") : "").replace("%blacklistedmodifiers%", getBlacklistedModifiers(pokemon.getSpecies()));

        for(String line : text.split("\n")) {
            lore.add(getFormattedText(line, pokemon, player));
        }
        return lore;
    }

}
