package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.config.lib.ModuleTypes;
import com.focamacho.sealbuilder.util.ConfigUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.forge.ForgeUtils;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.item.lib.ItemFlag;
import com.focamacho.seallibrary.menu.Menu;
import com.focamacho.seallibrary.menu.item.ClickableItem;
import com.focamacho.seallibrary.menu.lib.AbstractClick;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;

import java.util.Arrays;
import java.util.List;

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonGrowthInventory {

    private static final Menu base;

    static {
        Menu builder = Menu.create()
                .setRows(5)
                .setTitle(SealBuilderLang.getLang("menu.growth.title"));

        ISealStack whiteGlass = SealStack.get("minecraft:stained_glass_pane").setName("");
        ISealStack purpleGlass = SealStack.get("minecraft:stained_glass_pane:10").setName("");

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 39);

        for (int i = 0; i < 54; i++) {
            if (whiteGlassSlots.contains(i)) {
                builder.addItem(ClickableItem.create(i, whiteGlass.copy()));
            } else {
                builder.addItem(ClickableItem.create(i, purpleGlass.copy()));
            }
        }

        base = builder;
    }

    public static Menu get(Pokemon pokemon, ISealPlayer player) {
        Menu menu = base.copy();

        //Retornar ao Menu de Edição
        ISealStack pokemonItem = ForgeUtils.getServerStack(ItemPixelmonSprite.getPhoto(pokemon)).setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.main.pokemon.name"), pokemon, player)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.main.pokemon.lore"), pokemon, player));
        menu.addItem(ClickableItem.create(4, pokemonItem).setOnPrimary(click -> click.getPlayer().openInventory(PokemonEditInventory.get(pokemon, player))));

        //Stack Base para Poção
        ISealStack potionItem = SealStack.get("minecraft:potion").setData("{\"Potion\":\"minecraft:strong_swiftness\"}");

        //Valores iniciais
        EnumGrowth growth = EnumGrowth.Microscopic;
        String currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        double price = getGrowthPrice(pokemon.getSpecies(), growth);
        
        //Microscópico
        ISealStack microscopic = potionItem.copy().setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("growth.microscopic"), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(pokemon.getGrowth() == growth ? SealBuilderLang.getLang("menu.growth.your") : SealBuilderLang.getLang("menu.growth.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        addGrowthToMenu(menu, growth, pokemon, currency, price, 12, microscopic);

        //Pigmeu
        growth = EnumGrowth.Pygmy;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth);
        ISealStack pygmy = potionItem.copy().setData("{\"Potion\":\"minecraft:long_poison\"}").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("growth.pygmy"), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(pokemon.getGrowth() == growth ? SealBuilderLang.getLang("menu.growth.your") : SealBuilderLang.getLang("menu.growth.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        addGrowthToMenu(menu, growth, pokemon, currency, price, 13, pygmy);

        //Nanico
        growth = EnumGrowth.Runt;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth);
        ISealStack runt = potionItem.copy().setData("{\"Potion\":\"minecraft:leaping\"}").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("growth.runt"), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(pokemon.getGrowth() == growth ? SealBuilderLang.getLang("menu.growth.your") : SealBuilderLang.getLang("menu.growth.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        addGrowthToMenu(menu, growth, pokemon, currency, price, 14, runt);

        //Pequeno
        growth = EnumGrowth.Small;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth);
        ISealStack small = potionItem.copy().setData("{\"Potion\":\"minecraft:long_fire_resistance\"}").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("growth.small"), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(pokemon.getGrowth() == growth ? SealBuilderLang.getLang("menu.growth.your") : SealBuilderLang.getLang("menu.growth.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        addGrowthToMenu(menu, growth, pokemon, currency, price, 21, small);

        //Comum
        growth = EnumGrowth.Ordinary;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth);
        ISealStack ordinary = potionItem.copy().setData("{\"Potion\":\"minecraft:long_slowness\"}").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("growth.ordinary"), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(pokemon.getGrowth() == growth ? SealBuilderLang.getLang("menu.growth.your") : SealBuilderLang.getLang("menu.growth.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        addGrowthToMenu(menu, growth, pokemon, currency, price, 22, ordinary);

        //Imenso
        growth = EnumGrowth.Huge;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth);
        ISealStack huge = potionItem.copy().setData("{\"Potion\":\"minecraft:long_water_breathing\"}").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("growth.huge"), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(pokemon.getGrowth() == growth ? SealBuilderLang.getLang("menu.growth.your") : SealBuilderLang.getLang("menu.growth.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        addGrowthToMenu(menu, growth, pokemon, currency, price, 23, huge);

        //Gigante
        growth = EnumGrowth.Giant;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth);
        ISealStack giant = potionItem.copy().setData("{\"Potion\":\"minecraft:long_invisibility\"}").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("growth.giant"), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(pokemon.getGrowth() == growth ? SealBuilderLang.getLang("menu.growth.your") : SealBuilderLang.getLang("menu.growth.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        addGrowthToMenu(menu, growth, pokemon, currency, price, 30, giant);

        //Enorme
        growth = EnumGrowth.Enormous;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth);
        ISealStack enormous = potionItem.copy().setData("{\"Potion\":\"minecraft:strong_healing\"}").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("growth.enormous"), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(pokemon.getGrowth() == growth ? SealBuilderLang.getLang("menu.growth.your") : SealBuilderLang.getLang("menu.growth.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        addGrowthToMenu(menu, growth, pokemon, currency, price, 31, enormous);

        //Gigantesco
        growth = EnumGrowth.Ginormous;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth);
        ISealStack ginormous = potionItem.copy().setData("{\"Potion\":\"minecraft:night_vision\"}").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("growth.ginormous"), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(pokemon.getGrowth() == growth ? SealBuilderLang.getLang("menu.growth.your") : SealBuilderLang.getLang("menu.growth.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        addGrowthToMenu(menu, growth, pokemon, currency, price, 32, ginormous);

        return menu;
    }

    private static void addGrowthToMenu(Menu menu, EnumGrowth growth, Pokemon pokemon, String currency, double price, int slot, ISealStack stack) {
        menu.addItem(ClickableItem.create(slot, stack).setOnPrimary(click -> growthClickAction(click, growth, pokemon, currency, price)));
    }

    private static void growthClickAction(AbstractClick click, EnumGrowth growth, Pokemon pokemon, String currency, double price) {
        if(growth == pokemon.getGrowth()) return;

        ISealPlayer source = click.getPlayer();

        if(source.hasMoney(price, currency)) {
            pokemon.setGrowth(growth);
            source.removeMoney(price, currency);
            source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.edit.growth"), currency, price)));
            source.openInventory(PokemonEditInventory.get(pokemon, source));
            return;
        } else {
            source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), currency, price)));
        }
        source.closeInventory();
    }

    private static String getGrowthCurrency(EnumSpecies pokemon, EnumGrowth growth) {
        String override = ConfigUtils.getCurrencyOverrides(pokemon, ModuleTypes.GROWTH, growth.toString());
        return override != null ? override : config.currencyId;
    }

    private static double getGrowthPrice(EnumSpecies pokemon, EnumGrowth growth) {
        Double override = ConfigUtils.getPriceOverrides(pokemon, ModuleTypes.GROWTH, growth.toString());
        return override != null ? override : config.growthPrice;
    }

}
