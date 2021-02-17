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
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Gender;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;

import java.util.Arrays;
import java.util.List;

public class PokemonEditInventory {

    private static final AbstractMenu base;

    static {
        AbstractMenu builder = Menu.create(SealBuilder.instance)
                .setRows(5)
                .setTitle(SealBuilderLang.getLang("menu.edit.title"));

        ISealStack whiteGlass = SealStack.get("minecraft:stained_glass_pane").setName("");
        ISealStack purpleGlass = SealStack.get("minecraft:stained_glass_pane:10").setName("");

        List<Integer> purpleGlassSlots = Arrays.asList(9, 13, 17, 18, 26, 27, 35);

        for(int i = 0; i < 45; i++) {
            if(purpleGlassSlots.contains(i)) {
                builder.addItem(ClickableItem.create(i, purpleGlass.copy()));
            } else {
                builder.addItem(ClickableItem.create(i, whiteGlass.copy()));
            }
        }

        base = builder;
    }

    public static AbstractMenu get(Pokemon pokemon, ISealPlayer target) {
        AbstractMenu menu = base.copy();

        //Retornar ao Menu de Seleção
        ISealStack pokemonItem = ForgeUtils.getServerStack(ItemPixelmonSprite.getPhoto(pokemon)).setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.main.pokemon.name"), pokemon, target)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.main.pokemon.lore"), pokemon, target));
        menu.addItem(ClickableItem.create(22, pokemonItem).setOnPrimary(click -> click.getPlayer().openInventory(PokemonSelectInventory.get(click.getPlayer(), target))));

        //Modificar Pokébola
        ISealStack pokeball = ForgeUtils.getServerStack(new net.minecraft.item.ItemStack(pokemon.getCaughtBall().getItem())).setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.pokeball.name"), pokemon, target)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.pokeball.lore"), pokemon, target)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        menu.addItem(ClickableItem.create(31, pokeball).setOnPrimary(click -> {
            if(ConfigUtils.isModifierDisabled(ModuleTypes.POKEBALL)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, ModuleTypes.POKEBALL)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.pokeball.blacklist")));
            } else click.getPlayer().openInventory(PokemonPokeballInventory.get(pokemon, target));
        }));

        //Alternar Shiny
        ISealStack shiny = SealStack.get("pixelmon:lure_shiny_weak").setGlowing(pokemon.isShiny()).setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.shiny.name"), pokemon, target)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.shiny.lore"), pokemon, target)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true).setFlag(ItemFlag.HIDE_ENCHANTS, true);
        menu.addItem(ClickableItem.create(19, shiny).setOnPrimary(click ->  {
            if(ConfigUtils.isModifierDisabled(ModuleTypes.SHINY)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, ModuleTypes.SHINY)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.shiny.blacklist")));
            } else click.getPlayer().openInventory(PokemonShinyInventory.get(pokemon, target));
        }));

        //Alterar Tamanho do Pokémon
        ISealStack growth = SealStack.get("pixelmon:berrytree_kasib").setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.growth.name"), pokemon, target)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.growth.lore"), pokemon, target)).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true).setFlag(ItemFlag.HIDE_ATTRIBUTES, true);
        menu.addItem(ClickableItem.create(28, growth).setOnPrimary(click -> {
            if(ConfigUtils.isModifierDisabled(ModuleTypes.GROWTH)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, ModuleTypes.GROWTH)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.growth.blacklist")));
            } else click.getPlayer().openInventory(PokemonGrowthInventory.get(pokemon, target));
        }));

        //Liberar Habilidade Oculta
        boolean hasHiddenAbility = pokemon.getBaseStats().abilities.length == 3 && pokemon.getBaseStats().abilities[2] != null && !pokemon.getBaseStats().abilities[0].equals(pokemon.getBaseStats().abilities[2]);
        boolean isHiddenAbility = !(pokemon.getAbilitySlot() < 2 && hasHiddenAbility);
        ISealStack hiddenAbility = SealStack.get("pixelmon:lure_ha_weak").setGlowing(isHiddenAbility).setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.hiddenability.name"), pokemon, target)).setLore(TextUtils.getFormattedLore(hasHiddenAbility ? isHiddenAbility ? SealBuilderLang.getLang("menu.edit.hiddenability.your") : SealBuilderLang.getLang("menu.edit.hiddenability.lore") : SealBuilderLang.getLang("menu.edit.hiddenability.none"), pokemon, target)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true).setFlag(ItemFlag.HIDE_ENCHANTS, true);
        menu.addItem(ClickableItem.create(25, hiddenAbility).setOnPrimary(click ->  {
            if(!hasHiddenAbility) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.nohidden")));
                return;
            }
            if(isHiddenAbility) return;
            if(ConfigUtils.isModifierDisabled(ModuleTypes.HIDDEN_ABILITY)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, ModuleTypes.HIDDEN_ABILITY)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.hiddenability.blacklist")));
            } else click.getPlayer().openInventory(PokemonHiddenAbilityInventory.get(pokemon, target));
        }));

        //Alternar Gênero do Pokémon
        boolean hasOtherGender = Math.abs(pokemon.getBaseStats().malePercent - 50) < 50;
        ISealStack gender = SealStack.get(pokemon.getGender() == Gender.None ? "pixelmon:mint:4" : pokemon.getGender() == Gender.Female ? "pixelmon:mint:24" : "pixelmon:mint:12").setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.gender.name"), pokemon, target)).setLore(TextUtils.getFormattedLore(hasOtherGender ? SealBuilderLang.getLang("menu.edit.gender.lore") : SealBuilderLang.getLang("menu.edit.gender.nogender"), pokemon, target)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true).setFlag(ItemFlag.HIDE_ENCHANTS, true);
        menu.addItem(ClickableItem.create(34, gender).setOnPrimary(click ->  {
            if(!hasOtherGender) return;
            if(ConfigUtils.isModifierDisabled(ModuleTypes.GENDER)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, ModuleTypes.GENDER)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.gender.blacklist")));
            } else click.getPlayer().openInventory(PokemonGenderInventory.get(pokemon, target));
        }));

        //Alterar Natureza do Pokémon
        ISealStack nature = SealStack.get("pixelmon:gracidea").setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.nature.name"), pokemon, target)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.nature.lore"), pokemon, target)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        menu.addItem(ClickableItem.create(10, nature).setOnPrimary(click -> {
            if(ConfigUtils.isModifierDisabled(ModuleTypes.NATURE)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, ModuleTypes.NATURE)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.nature.blacklist")));
            } else click.getPlayer().openInventory(PokemonNatureInventory.get(pokemon, target));
        }));

        //Alterar IVs do Pokémon
        ISealStack ivs = SealStack.get("pixelmon:ice_gem").setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.ivs.name"), pokemon, target)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.ivs.lore"), pokemon, target)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        menu.addItem(ClickableItem.create(16, ivs).setOnPrimary(click -> {
            if(ConfigUtils.isModifierDisabled(ModuleTypes.IVS)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, ModuleTypes.IVS)) {
                click.getPlayer().sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.ivs.blacklist")));
            } else click.getPlayer().openInventory(PokemonIVsInventory.get(pokemon, target));
        }));

        return menu;
    }

}
