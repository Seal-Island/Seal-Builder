package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.config.lib.ModuleTypes;
import com.focamacho.sealbuilder.util.ConfigUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.forge.ForgeUtils;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.menu.Menu;
import com.focamacho.seallibrary.menu.item.ClickableItem;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;

import java.util.Arrays;
import java.util.List;

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonShinyInventory {

    private static final Menu base;

    static {
        Menu builder = Menu.create()
                .setRows(3)
                .setTitle(SealBuilderLang.getLang("menu.shiny.title"));

        ISealStack whiteGlass = SealStack.get("minecraft:stained_glass_pane").setName("");
        ISealStack purpleGlass = SealStack.get("minecraft:stained_glass_pane:10").setName("");

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 4, 9, 18, 19, 20, 21);
        List<Integer> emptySlots = Arrays.asList(10, 11, 12, 13, 14, 15, 16);

        for(int i = 0; i < 27; i++) {
            if(whiteGlassSlots.contains(i)) {
                builder.addItem(ClickableItem.create(i, whiteGlass.copy()));
            } else if(!emptySlots.contains(i)) {
                builder.addItem(ClickableItem.create(i, purpleGlass.copy()));
            }
        }

        base = builder;
    }

    public static Menu get(Pokemon pokemon, ISealPlayer player) {
        Menu menu = base.copy();

        String currency = getShinyCurrency(pokemon);
        double price = getShinyPrice(pokemon);

        String pokemonItemName = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.shiny.pokemon.name"), currency, price);
        String pokemonItemLore = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.shiny.pokemon.lore"), currency, price);
        String cancelItemName = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.shiny.cancel.name"), currency, price);
        String cancelItemLore = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.shiny.cancel.lore"), currency, price);
        String confirmItemName = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.shiny.confirm.name"), currency, price);
        String confirmItemLore = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.shiny.confirm.lore"), currency, price);

        Pokemon fakeShinyPokemon = Pixelmon.pokemonFactory.create(pokemon.getSpecies());
        fakeShinyPokemon.setShiny(!pokemon.isShiny());

        ISealStack pokemonItem = ForgeUtils.getServerStack(ItemPixelmonSprite.getPhoto(fakeShinyPokemon)).setName(TextUtils.getFormattedText(pokemonItemName, pokemon, player)).setLore(TextUtils.getFormattedLore(pokemonItemLore, pokemon, player));
        ISealStack cancelItem = SealStack.get("pixelmon:red_apricorn").setName(TextUtils.getFormattedText(cancelItemName, pokemon, player)).setLore(TextUtils.getFormattedLore(cancelItemLore, pokemon, player));
        ISealStack confirmItem = SealStack.get("pixelmon:green_apricorn").setName(TextUtils.getFormattedText(confirmItemName, pokemon, player)).setLore(TextUtils.getFormattedLore(confirmItemLore, pokemon, player));

        menu.addItem(ClickableItem.create(13, pokemonItem));
        menu.addItem(ClickableItem.create(10, cancelItem).setOnPrimary(click -> click.getPlayer().openInventory(PokemonEditInventory.get(pokemon, click.getPlayer()))));
        menu.addItem(ClickableItem.create(16, confirmItem).setOnPrimary(click -> {
            ISealPlayer source = click.getPlayer();

            if(source.hasMoney(price, currency)) {
                source.removeMoney(price, currency);
                pokemon.setShiny(!pokemon.isShiny());
                source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.edit.shiny"), currency, price)));
                source.openInventory(PokemonEditInventory.get(pokemon, source));
                return;
            } else {
                source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), currency, price)));
            }
            source.closeInventory();
        }));

        return menu;
    }

    private static String getShinyCurrency(Pokemon pokemon) {
        String override = ConfigUtils.getCurrencyOverrides(pokemon.getSpecies(), ModuleTypes.SHINY, "");
        return override != null ? override : config.currencyId;
    }

    private static double getShinyPrice(Pokemon pokemon) {
        Double override = ConfigUtils.getPriceOverrides(pokemon.getSpecies(), ModuleTypes.SHINY, "");
        return override != null ? override : config.shinyPrice;
    }

}
