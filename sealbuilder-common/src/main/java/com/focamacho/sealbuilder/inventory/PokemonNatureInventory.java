package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.config.lib.ModuleTypes;
import com.focamacho.sealbuilder.util.ConfigUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.forge.ForgeUtils;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.item.lib.ItemFlag;
import com.focamacho.seallibrary.menu.AbstractMenu;
import com.focamacho.seallibrary.menu.Menu;
import com.focamacho.seallibrary.menu.item.ClickableItem;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;

import java.util.Arrays;
import java.util.List;

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonNatureInventory {

    private static final AbstractMenu base;

    static {
        AbstractMenu builder = Menu.create()
                .setRows(6)
                .setTitle(SealBuilderLang.getLang("menu.nature.title"));

        ISealStack whiteGlass = SealStack.get("minecraft:stained_glass_pane").setName("");
        ISealStack purpleGlass = SealStack.get("minecraft:stained_glass_pane:10").setName("");

        List<Integer> purpleGlassSlots = Arrays.asList(10, 19, 28, 37, 46);
        List<Integer> whiteGlassSlots = Arrays.asList(0, 2, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 53);

        ISealStack[] icons = { SealStack.get("pixelmon:psychic_gem"), SealStack.get("pixelmon:water_gem"), SealStack.get("pixelmon:fire_gem"), SealStack.get("pixelmon:grass_gem"), SealStack.get("pixelmon:ice_gem") };

        for (int i = 0; i < 54; i++) {
            if (purpleGlassSlots.contains(i)) {
                builder.addItem(ClickableItem.create(i, purpleGlass.copy()));
            } else if(whiteGlassSlots.contains(i)){
                builder.addItem(ClickableItem.create(i, whiteGlass.copy()));
            }
        }

        int[] columnSlots = {3, 4, 5, 6, 7};
        int[] rowSlots = {11, 20, 29, 38, 47};
        String[] keys = {"attack", "defence", "spattack", "spdefence", "speed"};

        for(int i = 0; i < 5; i++) {
            ISealStack plusStack = icons[i].copy().setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.nature." + keys[i] + ".plus"))).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
            ISealStack minusStack = icons[i].copy().setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.nature." + keys[i] + ".minus"))).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
            builder.addItem(ClickableItem.create(columnSlots[i], plusStack));
            builder.addItem(ClickableItem.create(rowSlots[i], minusStack));
        }

        base = builder;
    }

    public static AbstractMenu get(Pokemon pokemon, ISealPlayer player) {
        AbstractMenu menu = base.copy();

        //Retornar ao Menu de Edição
        ISealStack pokemonItem = ForgeUtils.getServerStack(ItemPixelmonSprite.getPhoto(pokemon)).setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.main.pokemon.name"), pokemon, player)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.main.pokemon.lore"), pokemon, player));
        menu.addItem(ClickableItem.create(1, pokemonItem).setOnPrimary(click -> click.getPlayer().openInventory(PokemonEditInventory.get(pokemon, click.getPlayer()))));

        StatsType[] types = {StatsType.Attack, StatsType.Defence, StatsType.SpecialAttack, StatsType.SpecialDefence, StatsType.Speed};
        ISealStack[] icons = {SealStack.get("pixelmon:pink_clock"), SealStack.get("pixelmon:blue_clock"), SealStack.get("pixelmon:orange_clock"), SealStack.get("pixelmon:green_clock"), SealStack.get("pixelmon:cyan_clock")};
        ISealStack neutral = SealStack.get("pixelmon:white_clock");

        int neutralIndex = 0;

        for(int column = 0; column < types.length; column++) {
            for(int row = 0; row < types.length; row++) {
                EnumNature nature = getNatureByStats(types[column], types[row], neutralIndex);

                if(nature.increasedStat == StatsType.None && nature.decreasedStat == StatsType.None) neutralIndex++;

                String currency = getNatureCurrency(pokemon.getSpecies(), nature);
                double price = getNaturePrice(pokemon.getSpecies(), nature);

                String name = nature.getLocalizedName();
                String lore;

                if(nature == pokemon.getNature()) lore = SealBuilderLang.getLang("menu.nature.your");
                else if(column == row) lore = SealBuilderLang.getLang("menu.nature.neutral");
                else lore = SealBuilderLang.getLang("menu.nature.lore");

                lore = TextUtils.getFormattedCurrency(lore.replace("%plusstats%", SealBuilderLang.getLang("statstype." + types[column].name().toLowerCase())).replace("%minusstats%", SealBuilderLang.getLang("statstype." + types[row].name().toLowerCase())), currency, price);
                ISealStack stack = (column == row ? neutral : icons[column]).copy().setName(TextUtils.getFormattedText(name, pokemon, player)).setLore(TextUtils.getFormattedLore(lore, pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);

                menu.addItem(ClickableItem.create(12 + 9 * row + column, stack).setOnPrimary(click -> {
                    if(nature == pokemon.getNature()) return;

                    ISealPlayer source = click.getPlayer();

                    if(source.hasMoney(price, currency)) {
                        pokemon.setNature(nature);
                        source.removeMoney(price, currency);
                        source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.edit.nature"), currency, price)));
                        source.openInventory(PokemonEditInventory.get(pokemon, source));
                        return;
                    } else {
                        source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), currency, price)));
                    }
                    source.closeInventory();
                }));
            }
        }

        return menu;
    }

    private static EnumNature getNatureByStats(StatsType increased, StatsType decreased, int neutralIndex) {
        for (EnumNature value : EnumNature.values()) {
            if(value.increasedStat == increased && value.decreasedStat == decreased) return value;
        }
        return EnumNature.getNatureFromIndex(neutralIndex);
    }

    private static String getNatureCurrency(EnumSpecies pokemon, EnumNature nature) {
        String override = ConfigUtils.getCurrencyOverrides(pokemon, ModuleTypes.NATURE, nature.toString());
        return override != null ? override : config.currencyId;
    }

    private static double getNaturePrice(EnumSpecies pokemon, EnumNature nature) {
        Double override = ConfigUtils.getPriceOverrides(pokemon, ModuleTypes.NATURE, nature.toString());
        return override != null ? override : config.naturePrice;
    }

}
