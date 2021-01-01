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
import com.focamacho.seallibrary.util.ItemStackUtils;
import com.focamacho.seallibrary.util.MoneyUtils;
import com.focamacho.seallibrary.util.Utils;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class PokemonHiddenAbilityInventory {

    private static final MenuBuilder base = getBase();

    public static Inventory get(Pokemon pokemon, Player player) {
        MenuBuilder menu = base.copy();

        Currency currency = getHiddenAbilityCurrency(pokemon);
        double price = getHiddenAbilityPrice(pokemon, player);

        String pokemonItemName = getFormattedCurrency(LangConfig.get("menu.hiddenability.pokemon.name"), currency, price);
        String pokemonItemLore = getFormattedCurrency(LangConfig.get("menu.hiddenability.pokemon.lore"), currency, price);
        String cancelItemName = getFormattedCurrency(LangConfig.get("menu.hiddenability.cancel.name"), currency, price);
        String cancelItemLore = getFormattedCurrency(LangConfig.get("menu.hiddenability.cancel.lore"), currency, price);
        String confirmItemName = getFormattedCurrency(LangConfig.get("menu.hiddenability.confirm.name"), currency, price);
        String confirmItemLore = getFormattedCurrency(LangConfig.get("menu.hiddenability.confirm.lore"), currency, price);

        Pokemon fakeHiddenAbilityPokemon = Pixelmon.pokemonFactory.create(pokemon.getSpecies());
        fakeHiddenAbilityPokemon.setAbilitySlot(2);

        ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(fakeHiddenAbilityPokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(pokemonItemName, fakeHiddenAbilityPokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(pokemonItemLore, fakeHiddenAbilityPokemon, player)).build();
        ItemStack cancelItem = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(PluginConfig.cancelItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(cancelItemName, pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(cancelItemLore, pokemon, player)).build();
        ItemStack confirmItem = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(PluginConfig.confirmItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(confirmItemName, pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(confirmItemLore, pokemon, player)).build();

        menu.addClickableItem(new ClickableItem.Builder().build(13, pokemonItem));
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> InventoryUtils.openInventory((Player) click.getSource(), PokemonEditInventory.get(pokemon, (Player) click.getSource()), SealBuilder.instance)).build(10, cancelItem));
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player source = (Player) click.getSource();

            if(MoneyUtils.hasMoney(source, BigDecimal.valueOf(price), currency)) {
                MoneyUtils.removeMoney(source, BigDecimal.valueOf(price), currency);
                pokemon.setAbilitySlot(2);
                source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.edit.hiddenability"), currency, price)));
                InventoryUtils.openInventory(source, PokemonEditInventory.get(pokemon, source), SealBuilder.instance);
                return;
            } else {
                source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.money.insufficient"), currency, price)));
            }
            InventoryUtils.closeInventory(source, SealBuilder.instance);
        }).build(16, confirmItem));

        return menu.build();
    }

    private static String getFormattedCurrency(String text, Currency currency, double price) {
        return text.replace("%currencykey%", currency.getSymbol().toPlain())
                .replace("%currencyname%", currency.getPluralDisplayName().toPlain())
                .replace("%price%", Utils.formatDouble(price));
    }

    private static Currency getHiddenAbilityCurrency(Pokemon pokemon) {
        Currency override = ConfigUtils.getCurrencyOverrides(pokemon.getSpecies(), "hiddenability", "");
        return override != null ? override : MoneyUtils.getCurrencyByIdOrDefault(PluginConfig.currencyId);
    }

    private static double getHiddenAbilityPrice(Pokemon pokemon, Player player) {
        Double override = ConfigUtils.getPriceOverrides(pokemon.getSpecies(), "hiddenability", "");
        return ConfigUtils.applyDiscount(override != null ? override : PluginConfig.hiddenAbilityPrice, player);
    }

    private static MenuBuilder getBase() {
        MenuBuilder builder = MenuBuilder.create(SealBuilder.instance)
                .setRows(3)
                .setTitle(LangConfig.get("menu.hiddenability.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 4, 9, 18, 19, 20, 21);
        List<Integer> emptySlots = Arrays.asList(10, 11, 12, 13, 14, 15, 16);

        for(int i = 0; i < 27; i++) {
            if(whiteGlassSlots.contains(i)) {
                builder.addClickableItem(new ClickableItem.Builder().build(i, whiteGlass.copy()));
            } else if(!emptySlots.contains(i)) {
                builder.addClickableItem(new ClickableItem.Builder().build(i, purpleGlass.copy()));
            }
        }

        return builder;
    }

}
