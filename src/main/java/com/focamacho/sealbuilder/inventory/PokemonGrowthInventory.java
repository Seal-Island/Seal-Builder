package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.LangConfig;
import com.focamacho.sealbuilder.config.PluginConfig;
import com.focamacho.sealbuilder.util.ConfigUtils;
import com.focamacho.sealbuilder.util.PokemonUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.menu.ClickableItem;
import com.focamacho.seallibrary.menu.MenuBuilder;
import com.focamacho.seallibrary.util.InventoryUtils;
import com.focamacho.seallibrary.util.MoneyUtils;
import com.focamacho.seallibrary.util.Utils;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class PokemonGrowthInventory {

    private static final MenuBuilder base = getBase();

    public static Inventory get(Pokemon pokemon, Player player) {
        MenuBuilder menu = base.copy();

        //Retornar ao Menu de Edição
        ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.main.pokemon.name"), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.main.pokemon.lore"), pokemon, player)).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player player2 = (Player) click.getSource();
            InventoryUtils.openInventory(player2, PokemonEditInventory.get(pokemon, player), SealBuilder.instance);
        }).build(4, pokemonItem));

        //Container Base para Poção
        DataContainer potionContainer = ItemTypes.POTION.getTemplate().toContainer().set(DataQuery.of("UnsafeData" , "Potion"), "minecraft:strong_swiftness");

        //Valores iniciais
        EnumGrowth growth = EnumGrowth.Microscopic;
        Currency currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        double price = getGrowthPrice(pokemon.getSpecies(), growth, player);
        
        //Microscópico
        ItemStack microscopic = ItemStack.builder().fromContainer(potionContainer).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("growth.microscopic"), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(pokemon.getGrowth() == growth ? LangConfig.get("menu.growth.your") : LangConfig.get("menu.growth.lore"), currency, price), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        addGrowthToMenu(menu, growth, pokemon, currency, price, 12, microscopic);

        //Pigmeu
        growth = EnumGrowth.Pygmy;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth, player);
        ItemStack pygmy = ItemStack.builder().fromContainer(potionContainer.set(DataQuery.of("UnsafeData" , "Potion"), "minecraft:long_poison")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("growth.pygmy"), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(pokemon.getGrowth() == growth ? LangConfig.get("menu.growth.your") : LangConfig.get("menu.growth.lore"), currency, price), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        addGrowthToMenu(menu, growth, pokemon, currency, price, 13, pygmy);

        //Nanico
        growth = EnumGrowth.Runt;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth, player);
        ItemStack runt = ItemStack.builder().fromContainer(potionContainer.set(DataQuery.of("UnsafeData" , "Potion"), "minecraft:leaping")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("growth.runt"), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(pokemon.getGrowth() == growth ? LangConfig.get("menu.growth.your") : LangConfig.get("menu.growth.lore"), currency, price), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        addGrowthToMenu(menu, growth, pokemon, currency, price, 14, runt);

        //Pequeno
        growth = EnumGrowth.Small;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth, player);
        ItemStack small = ItemStack.builder().fromContainer(potionContainer.set(DataQuery.of("UnsafeData" , "Potion"), "minecraft:long_fire_resistance")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("growth.small"), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(pokemon.getGrowth() == growth ? LangConfig.get("menu.growth.your") : LangConfig.get("menu.growth.lore"), currency, price), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        addGrowthToMenu(menu, growth, pokemon, currency, price, 21, small);

        //Comum
        growth = EnumGrowth.Ordinary;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth, player);
        ItemStack ordinary = ItemStack.builder().fromContainer(potionContainer.set(DataQuery.of("UnsafeData" , "Potion"), "minecraft:long_slowness")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("growth.ordinary"), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(pokemon.getGrowth() == growth ? LangConfig.get("menu.growth.your") : LangConfig.get("menu.growth.lore"), currency, price), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        addGrowthToMenu(menu, growth, pokemon, currency, price, 22, ordinary);

        //Imenso
        growth = EnumGrowth.Huge;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth, player);
        ItemStack huge = ItemStack.builder().fromContainer(potionContainer.set(DataQuery.of("UnsafeData" , "Potion"), "minecraft:long_water_breathing")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("growth.huge"), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(pokemon.getGrowth() == growth ? LangConfig.get("menu.growth.your") : LangConfig.get("menu.growth.lore"), currency, price), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        addGrowthToMenu(menu, growth, pokemon, currency, price, 23, huge);

        //Gigante
        growth = EnumGrowth.Giant;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth, player);
        ItemStack giant = ItemStack.builder().fromContainer(potionContainer.set(DataQuery.of("UnsafeData" , "Potion"), "minecraft:long_invisibility")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("growth.giant"), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(pokemon.getGrowth() == growth ? LangConfig.get("menu.growth.your") : LangConfig.get("menu.growth.lore"), currency, price), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        addGrowthToMenu(menu, growth, pokemon, currency, price, 30, giant);

        //Enorme
        growth = EnumGrowth.Enormous;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth, player);
        ItemStack enormous = ItemStack.builder().fromContainer(potionContainer.set(DataQuery.of("UnsafeData" , "Potion"), "minecraft:strong_healing")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("growth.enormous"), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(pokemon.getGrowth() == growth ? LangConfig.get("menu.growth.your") : LangConfig.get("menu.growth.lore"), currency, price), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        addGrowthToMenu(menu, growth, pokemon, currency, price, 31, enormous);

        //Gigantesco
        growth = EnumGrowth.Ginormous;
        currency = getGrowthCurrency(pokemon.getSpecies(), growth);
        price = getGrowthPrice(pokemon.getSpecies(), growth, player);
        ItemStack ginormous = ItemStack.builder().fromContainer(potionContainer.set(DataQuery.of("UnsafeData" , "Potion"), "minecraft:night_vision")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("growth.ginormous"), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(pokemon.getGrowth() == growth ? LangConfig.get("menu.growth.your") : LangConfig.get("menu.growth.lore"), currency, price), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        addGrowthToMenu(menu, growth, pokemon, currency, price, 32, ginormous);

        return menu.build(SealBuilder.instance);
    }

    private static void addGrowthToMenu(MenuBuilder menu, EnumGrowth growth, Pokemon pokemon, Currency currency, double price, int slot, ItemStack stack) {
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> growthClickAction(click, growth, pokemon, currency, price)).build(slot, stack));
    }

    private static void growthClickAction(ClickInventoryEvent click, EnumGrowth growth, Pokemon pokemon, Currency currency, double price) {
        if(growth == pokemon.getGrowth()) return;

        Player source = (Player) click.getSource();

        if(MoneyUtils.hasMoney(source, BigDecimal.valueOf(price), currency)) {
            pokemon.setGrowth(growth);
            MoneyUtils.removeMoney(source, BigDecimal.valueOf(price), currency);
            source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.edit.growth"), currency, price)));
            InventoryUtils.openInventory(source, PokemonEditInventory.get(pokemon, source), SealBuilder.instance);
            return;
        } else {
            source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.money.insufficient"), currency, price)));
        }
        InventoryUtils.closePlayerInventory(source, SealBuilder.instance);
    }

    private static String getFormattedCurrency(String text, Currency currency, double price) {
        return text.replace("%currencykey%", currency.getSymbol().toPlain())
                .replace("%currencyname%", currency.getPluralDisplayName().toPlain())
                .replace("%price%", Utils.formatDouble(price));
    }

    private static Currency getGrowthCurrency(EnumSpecies pokemon, EnumGrowth growth) {
        Currency override = ConfigUtils.getCurrencyOverrides(pokemon, "growth", growth.toString());
        return override != null ? override : MoneyUtils.getCurrencyByIdOrDefault(PluginConfig.currencyId);
    }

    private static double getGrowthPrice(EnumSpecies pokemon, EnumGrowth growth, Player player) {
        Double override = ConfigUtils.getPriceOverrides(pokemon, "growth", growth.toString());
        return ConfigUtils.applyDiscount(override != null ? override : PluginConfig.growthPrice, player);
    }

    private static MenuBuilder getBase() {
        MenuBuilder builder = new MenuBuilder()
                .setRows(5)
                .setTitle(LangConfig.get("menu.growth.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 9, 10, 11, 18, 19, 20, 27, 28, 29, 36, 37, 38, 39);

        for (int i = 0; i < 54; i++) {
            if (whiteGlassSlots.contains(i)) {
                builder.addClickableItem(new ClickableItem.Builder().build(i, whiteGlass.copy()));
            } else {
                builder.addClickableItem(new ClickableItem.Builder().build(i, purpleGlass.copy()));
            }
        }

        return builder;
    }
}
