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
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;

import java.util.Arrays;
import java.util.List;

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonHiddenAbilityInventory {

    private static final AbstractMenu base;

    static {
        AbstractMenu builder = Menu.create(SealBuilder.instance)
                .setRows(3)
                .setTitle(SealBuilderLang.getLang("menu.hiddenability.title"));

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

        String currency = getHiddenAbilityCurrency(pokemon);
        double price = getHiddenAbilityPrice(pokemon);

        String pokemonItemName = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.hiddenability.pokemon.name"), currency, price);
        String pokemonItemLore = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.hiddenability.pokemon.lore"), currency, price);
        String cancelItemName = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.hiddenability.cancel.name"), currency, price);
        String cancelItemLore = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.hiddenability.cancel.lore"), currency, price);
        String confirmItemName = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.hiddenability.confirm.name"), currency, price);
        String confirmItemLore = TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.hiddenability.confirm.lore"), currency, price);

        Pokemon fakeHiddenAbilityPokemon = Pixelmon.pokemonFactory.create(pokemon.getSpecies());
        fakeHiddenAbilityPokemon.setAbilitySlot(2);

        ISealStack pokemonItem = ForgeUtils.getServerStack(ItemPixelmonSprite.getPhoto(fakeHiddenAbilityPokemon)).setName(TextUtils.getFormattedText(pokemonItemName, fakeHiddenAbilityPokemon, player)).setLore(TextUtils.getFormattedLore(pokemonItemLore, fakeHiddenAbilityPokemon, player));
        ISealStack cancelItem = SealStack.get("pixelmon:red_apricorn").setName(TextUtils.getFormattedText(cancelItemName, pokemon, player)).setLore(TextUtils.getFormattedLore(cancelItemLore, pokemon, player));
        ISealStack confirmItem = SealStack.get("pixelmon:green_apricorn").setName(TextUtils.getFormattedText(confirmItemName, pokemon, player)).setLore(TextUtils.getFormattedLore(confirmItemLore, pokemon, player));

        menu.addItem(ClickableItem.create(13, pokemonItem));
        menu.addItem(ClickableItem.create(10, cancelItem).setOnPrimary(click -> click.getPlayer().openInventory(PokemonEditInventory.get(pokemon, click.getPlayer()))));
        menu.addItem(ClickableItem.create(16, confirmItem).setOnPrimary(click -> {
            ISealPlayer source = click.getPlayer();

            if(source.hasMoney(price, currency)) {
                source.removeMoney(price, currency);
                pokemon.setAbilitySlot(2);
                source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.edit.hiddenability"), currency, price)));
                source.openInventory(PokemonEditInventory.get(pokemon, source));
                return;
            } else {
                source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), currency, price)));
            }
            source.closeInventory();
        }));

        return menu;
    }
    
    private static String getHiddenAbilityCurrency(Pokemon pokemon) {
        String override = ConfigUtils.getCurrencyOverrides(pokemon.getSpecies(), ModuleTypes.HIDDEN_ABILITY, "");
        return override != null ? override : config.currencyId;
    }

    private static double getHiddenAbilityPrice(Pokemon pokemon) {
        Double override = ConfigUtils.getPriceOverrides(pokemon.getSpecies(), ModuleTypes.HIDDEN_ABILITY, "");
        return override != null ? override : config.hiddenAbilityPrice;
    }

}
