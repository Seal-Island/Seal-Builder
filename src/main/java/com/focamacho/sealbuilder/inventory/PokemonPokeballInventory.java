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
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class PokemonPokeballInventory {

    private static final MenuBuilder base = getBase();
    private static final int[] pokeballSlots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public static Inventory get(Pokemon pokemon) {
        MenuBuilder menu = base.copy();

        //Retornar ao Menu de Edição
        ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.main.pokemon.name"), pokemon)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.main.pokemon.lore"), pokemon)).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player player = (Player) click.getSource();
            InventoryUtils.openInventory(player, PokemonEditInventory.get(pokemon), SealBuilder.instance);
        }).build(4, pokemonItem));

        EnumPokeballs[] pokeballs = EnumPokeballs.values();

        for(int i = 0; i < pokeballs.length; i++) {
            EnumPokeballs pokeball = pokeballs[i];

            Currency currency = getPokeballCurrency(pokemon.getSpecies(), pokeball);
            double price = getPokeballPrice(pokemon.getSpecies(), pokeball);

            if(pokeball == pokemon.getCaughtBall()) {
                ItemStack stack = ItemStack.builder().from(ItemStackUtil.fromNative(new net.minecraft.item.ItemStack(pokeball.getItem()))).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(LangConfig.get("menu.pokeball.your"), currency, price), pokemon)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
                menu.addClickableItem(new ClickableItem.Builder().build(pokeballSlots[i], stack));
                continue;
            }

            ItemStack stack = ItemStack.builder().from(ItemStackUtil.fromNative(new net.minecraft.item.ItemStack(pokeball.getItem()))).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(LangConfig.get("menu.pokeball.lore"), currency, price), pokemon)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
            menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
                Player source = (Player) click.getSource();

                if(MoneyUtils.hasMoney(source, BigDecimal.valueOf(price), currency)) {
                    pokemon.setCaughtBall(pokeball);
                    MoneyUtils.removeMoney(source, BigDecimal.valueOf(price), currency);
                    source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.edit.pokeball"), currency, price)));
                    InventoryUtils.openInventory(source, PokemonEditInventory.get(pokemon), SealBuilder.instance);
                    return;
                } else {
                    source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.money.insufficient"), currency, price)));
                }
                InventoryUtils.closePlayerInventory(source, SealBuilder.instance);
            }).build(pokeballSlots[i], stack));
        }

        return menu.build(SealBuilder.instance);
    }

    private static String getFormattedCurrency(String text, Currency currency, double price) {
        return text.replace("%currencykey%", currency.getSymbol().toPlain())
                .replace("%currencyname%", currency.getPluralDisplayName().toPlain())
                .replace("%price%", Utils.formatDouble(price));
    }

    private static Currency getPokeballCurrency(EnumSpecies pokemon, EnumPokeballs pokeball) {
        Currency override = ConfigUtils.getCurrencyOverrides(pokemon, "pokeball", pokeball.getFilenamePrefix().toLowerCase());
        return override != null ? override : MoneyUtils.getCurrencyByIdOrDefault(PluginConfig.currencyId);
    }

    private static double getPokeballPrice(EnumSpecies pokemon, EnumPokeballs pokeball) {
        Double override = ConfigUtils.getPriceOverrides(pokemon, "pokeball", pokeball.getFilenamePrefix().toLowerCase());
        return override != null ? override : PluginConfig.pokeballPrice;
    }

    private static MenuBuilder getBase() {
        MenuBuilder builder = new MenuBuilder()
                .setRows(6)
                .setTitle(LangConfig.get("menu.pokeball.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 9, 18, 27, 36, 45, 46, 47, 48);

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
