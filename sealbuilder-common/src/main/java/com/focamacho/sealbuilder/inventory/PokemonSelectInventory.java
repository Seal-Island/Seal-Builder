package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.SealBuilder;
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
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;

import java.util.Arrays;
import java.util.List;

public class PokemonSelectInventory {

    private static final AbstractMenu base;
    private static final int[] basePokemonSlots = new int[]{12, 13, 14, 21, 22, 23};

    static {
        AbstractMenu builder = Menu.create(SealBuilder.instance)
                .setRows(4)
                .setTitle(SealBuilderLang.getLang("menu.main.title"));

        ISealStack whiteGlass = SealStack.get("minecraft:stained_glass_pane").setName("");
        ISealStack purpleGlass = SealStack.get("minecraft:stained_glass_pane:10").setName("");

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 4, 9, 10, 11, 18, 19, 20, 27, 28, 29, 30);

        for(int i = 0; i < 36; i++) {
            if(whiteGlassSlots.contains(i)) {
                builder.addItem(ClickableItem.create(i, whiteGlass.copy()));
            } else {
                builder.addItem(ClickableItem.create(i, purpleGlass.copy()));
            }
        }

        base = builder;
    }

    public static AbstractMenu get(ISealPlayer player, ISealPlayer target) {
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(target.getUUID());

        AbstractMenu menu = base.copy();

        boolean canCreatePokemon = target.hasPermission("sealbuilder.create");
        ISealStack noPokemonItem = SealStack.get("pixelmon:master_ball").setName(TextUtils.getFormattedText(canCreatePokemon ? SealBuilderLang.getLang("menu.main.create") : SealBuilderLang.getLang("menu.main.nopokemon"), target)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);

        for(int i = 0; i < basePokemonSlots.length; i++) {
            Pokemon pokemon = party.get(i);

            if(pokemon != null) {
                ISealStack pokemonItem = ForgeUtils.getServerStack(ItemPixelmonSprite.getPhoto(pokemon)).setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.main.pokemon.name"), pokemon, target)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.main.pokemon.lore"), pokemon, target));
                menu.addItem(ClickableItem.create(basePokemonSlots[i], pokemonItem).setOnPrimary(click -> click.getPlayer().openInventory(PokemonEditInventory.get(pokemon, target))));
            } else {
                menu.addItem(ClickableItem.create(basePokemonSlots[i], noPokemonItem).setOnPrimary(click -> {
                    //Criar pokémon
                    if(canCreatePokemon) {
                        ISealPlayer source = click.getPlayer();
                        source.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.create"), source));
                        source.closeInventory();
                        source.waitForMessage((message) -> {
                            EnumSpecies specie = EnumSpecies.getFromNameAnyCase(message.getMessage());

                            if(specie == null) {
                                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.nopokemon"), player));
                                return;
                            }

                            if(ConfigUtils.isBlacklisted(specie, ModuleTypes.CREATE)) {
                                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.create.blacklist"), player));
                                return;
                            }

                            message.getPlayer().openInventory(PokemonCreateInventory.get(specie, target));
                        }, 120);
                    }
                }));
            }
        }

        return menu;
    }

}
