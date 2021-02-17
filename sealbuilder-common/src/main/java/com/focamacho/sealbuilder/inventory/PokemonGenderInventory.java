package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.config.lib.ModuleTypes;
import com.focamacho.sealbuilder.util.ConfigUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.forge.ForgeUtils;
import com.focamacho.seallibrary.item.ISealStack;
import com.focamacho.seallibrary.item.SealStack;
import com.focamacho.seallibrary.menu.AbstractMenu;
import com.focamacho.seallibrary.menu.Menu;
import com.focamacho.seallibrary.menu.item.ClickableItem;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Gender;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;

import java.util.Arrays;
import java.util.List;

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonGenderInventory {

    private static final AbstractMenu base;

    static {
        AbstractMenu builder = Menu.create(SealBuilder.instance)
                .setRows(3)
                .setTitle(SealBuilderLang.getLang("menu.gender.title"));

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

    public static AbstractMenu get(Pokemon pokemon, ISealPlayer player) {
        AbstractMenu menu = base.copy();

        String currency = getGenderCurrency(pokemon);
        double price = getGenderPrice(pokemon);

        String pokemonItemName = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.gender.pokemon.name"), currency, price);
        String pokemonItemLore = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.gender.pokemon.lore"), currency, price);
        String cancelItemName = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.gender.cancel.name"), currency, price);
        String cancelItemLore = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.gender.cancel.lore"), currency, price);
        String confirmItemName = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.gender.confirm.name"), currency, price);
        String confirmItemLore = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.gender.confirm.lore"), currency, price);

        Pokemon fakeGenderPokemon = Pixelmon.pokemonFactory.create(pokemon.getSpecies());
        fakeGenderPokemon.setGender(pokemon.getGender() == Gender.Female ? Gender.Male : Gender.Female);

        ISealStack pokemonItem = ForgeUtils.getServerStack(ItemPixelmonSprite.getPhoto(pokemon)).setName(TextUtils.getFormattedText(pokemonItemName, pokemon, player)).setLore(TextUtils.getFormattedLore(pokemonItemLore, pokemon, player));
        ISealStack cancelItem = SealStack.get("pixelmon:red_apricorn").setName(TextUtils.getFormattedText(cancelItemName, pokemon, player)).setLore(TextUtils.getFormattedLore(cancelItemLore, pokemon, player));
        ISealStack confirmItem = SealStack.get("pixelmon:green_apricorn").setName(TextUtils.getFormattedText(confirmItemName, pokemon, player)).setLore(TextUtils.getFormattedLore(confirmItemLore, pokemon, player));

        menu.addItem(ClickableItem.create(13, pokemonItem));
        menu.addItem(ClickableItem.create(10, cancelItem).setOnPrimary(click -> click.getPlayer().openInventory(PokemonEditInventory.get(pokemon, click.getPlayer()))));
        menu.addItem(ClickableItem.create(16, confirmItem).setOnPrimary(click -> {
            if(click.getPlayer().hasMoney(price, currency)) {
                click.getPlayer().removeMoney(price, currency);
                pokemon.setGender(fakeGenderPokemon.getGender());
                click.getPlayer().sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.edit.gender"), currency, price)));
                click.getPlayer().openInventory(PokemonEditInventory.get(pokemon, click.getPlayer()));
                return;
            } else {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), currency, price)));
            }
            click.getPlayer().closeInventory();
        }));

        return menu;
    }


    private static String getGenderCurrency(Pokemon pokemon) {
        String override = ConfigUtils.getCurrencyOverrides(pokemon.getSpecies(), ModuleTypes.GENDER, "");
        return override != null ? override : config.currencyId;
    }

    private static double getGenderPrice(Pokemon pokemon) {
        Double override = ConfigUtils.getPriceOverrides(pokemon.getSpecies(), ModuleTypes.GENDER, "");
        return override != null ? override : config.genderPrice;
    }

}
