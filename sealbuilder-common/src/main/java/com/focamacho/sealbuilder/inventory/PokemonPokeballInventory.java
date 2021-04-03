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
import com.focamacho.seallibrary.player.ISealPlayer;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;

import java.util.Arrays;
import java.util.List;

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonPokeballInventory {

    private static final Menu base;
    private static final int[] pokeballSlots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    static {
        Menu builder = Menu.create()
                .setRows(6)
                .setTitle(SealBuilderLang.getLang("menu.pokeball.title"));

        ISealStack whiteGlass = SealStack.get("minecraft:stained_glass_pane").setName("");
        ISealStack purpleGlass = SealStack.get("minecraft:stained_glass_pane:10").setName("");

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 9, 18, 27, 36, 45, 46, 47, 48);

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
        menu.addItem(ClickableItem.create(4, pokemonItem).setOnPrimary(click -> click.getPlayer().openInventory(PokemonEditInventory.get(pokemon, click.getPlayer()))));

        EnumPokeballs[] pokeballs = EnumPokeballs.values();

        for(int i = 0; i < pokeballs.length; i++) {
            EnumPokeballs pokeball = pokeballs[i];

            String currency = getPokeballCurrency(pokemon.getSpecies(), pokeball);
            double price = getPokeballPrice(pokemon.getSpecies(), pokeball);

            if(pokeball == pokemon.getCaughtBall()) {
                ISealStack stack = ForgeUtils.getServerStack(new net.minecraft.item.ItemStack(pokeball.getItem())).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.pokeball.your"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
                menu.addItem(ClickableItem.create(pokeballSlots[i], stack));
                continue;
            }

            ISealStack stack = ForgeUtils.getServerStack(new net.minecraft.item.ItemStack(pokeball.getItem())).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.pokeball.lore"), currency, price), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
            menu.addItem(ClickableItem.create(pokeballSlots[i], stack).setOnPrimary(click -> {
                ISealPlayer source = click.getPlayer();

                if(source.hasMoney(price, currency)) {
                    pokemon.setCaughtBall(pokeball);
                    source.removeMoney(price, currency);
                    source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.edit.pokeball"), currency, price)));
                    source.openInventory(PokemonEditInventory.get(pokemon, source));
                    return;
                } else {
                    source.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), currency, price)));
                }
                source.closeInventory();
            }));
        }

        return menu;
    }

    private static String getPokeballCurrency(EnumSpecies pokemon, EnumPokeballs pokeball) {
        String override = ConfigUtils.getCurrencyOverrides(pokemon, ModuleTypes.POKEBALL, pokeball.getFilenamePrefix().toLowerCase());
        return override != null ? override : config.currencyId;
    }

    private static double getPokeballPrice(EnumSpecies pokemon, EnumPokeballs pokeball) {
        Double override = ConfigUtils.getPriceOverrides(pokemon, ModuleTypes.POKEBALL, pokeball.getFilenamePrefix().toLowerCase());
        return override != null ? override : config.pokeballPrice;
    }

}
