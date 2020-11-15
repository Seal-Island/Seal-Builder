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
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Gender;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PokemonEditInventory {

    private static final MenuBuilder base = getBase();

    public static Inventory get(Pokemon pokemon, Player plyer) {
        MenuBuilder menu = base.copy();

        //Retornar ao Menu de Seleção
        ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.main.pokemon.name"), pokemon, plyer)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.main.pokemon.lore"), pokemon, plyer)).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player player = (Player) click.getSource();
            InventoryUtils.openInventory(player, PokemonSelectInventory.get(player), SealBuilder.instance);
        }).build(22, pokemonItem));

        //Modificar Pokébola
        ItemStack pokeball = ItemStack.builder().from(ItemStackUtil.fromNative(new net.minecraft.item.ItemStack(pokemon.getCaughtBall().getItem()))).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.edit.pokeball.name"), pokemon, plyer)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.edit.pokeball.lore"), pokemon, plyer)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("pokeball")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "pokeball")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.pokeball.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonPokeballInventory.get(pokemon, player), SealBuilder.instance);
        }).build(31, pokeball));

        //Alternar Shiny
        ItemStack shiny = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(PluginConfig.shinyIcon)).add(Keys.ITEM_ENCHANTMENTS, pokemon.isShiny() ? Collections.singletonList(Enchantment.builder().type(EnchantmentTypes.UNBREAKING).level(1).build()) : Collections.emptyList()).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.edit.shiny.name"), pokemon, plyer)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.edit.shiny.lore"), pokemon, plyer)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).add(Keys.HIDE_ENCHANTMENTS, true).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click ->  {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("shiny")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "shiny")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.shiny.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonShinyInventory.get(pokemon, player), SealBuilder.instance);
        }).build(19, shiny));

        //Alterar Tamanho do Pokémon
        ItemStack growth = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(PluginConfig.growthIcon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.edit.growth.name"), pokemon, plyer)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.edit.growth.lore"), pokemon, plyer)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("growth")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "growth")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.growth.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonGrowthInventory.get(pokemon, player), SealBuilder.instance);
        }).build(28, growth));

        //Liberar Habilidade Oculta
        boolean hasHiddenAbility = pokemon.getBaseStats().abilities.length == 3 && pokemon.getBaseStats().abilities[2] != null;
        boolean isHiddenAbility = !(pokemon.getAbilitySlot() < 2 && hasHiddenAbility);
        ItemStack hiddenAbility = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(PluginConfig.hiddenAbilityIcon)).add(Keys.ITEM_ENCHANTMENTS, isHiddenAbility ? Collections.singletonList(Enchantment.builder().type(EnchantmentTypes.UNBREAKING).level(1).build()) : Collections.emptyList()).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.edit.hiddenability.name"), pokemon, plyer)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(hasHiddenAbility ? isHiddenAbility ? LangConfig.get("menu.edit.hiddenability.your") : LangConfig.get("menu.edit.hiddenability.lore") : LangConfig.get("menu.edit.hiddenability.none"), pokemon, plyer)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).add(Keys.HIDE_ENCHANTMENTS, true).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click ->  {
            Player player = (Player) click.getSource();
            if(!hasHiddenAbility) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.nohidden")));
                return;
            }
            if(isHiddenAbility) return;
            if(ConfigUtils.isModifierDisabled("hiddenability")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "hiddenability")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.hiddenability.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonHiddenAbilityInventory.get(pokemon, player), SealBuilder.instance);
        }).build(25, hiddenAbility));

        //Alternar Gênero do Pokémon
        boolean hasOtherGender = Math.abs(pokemon.getBaseStats().malePercent - 50) < 50;
        ItemStack gender = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(pokemon.getGender() == Gender.None ? PluginConfig.noGenderIcon : pokemon.getGender() == Gender.Female ? PluginConfig.femaleGenderIcon : PluginConfig.maleGenderIcon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.edit.gender.name"), pokemon, plyer)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(hasOtherGender ? LangConfig.get("menu.edit.gender.lore") : LangConfig.get("menu.edit.gender.nogender"), pokemon, plyer)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).add(Keys.HIDE_ENCHANTMENTS, true).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click ->  {
            Player player = (Player) click.getSource();
            if(!hasOtherGender) return;
            if(ConfigUtils.isModifierDisabled("gender")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "gender")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.gender.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonGenderInventory.get(pokemon, player), SealBuilder.instance);
        }).build(34, gender));

        //Alterar Natureza do Pokémon
        ItemStack nature = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(PluginConfig.natureIcon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.edit.nature.name"), pokemon, plyer)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.edit.nature.lore"), pokemon, plyer)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("nature")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "nature")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.nature.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonNatureInventory.get(pokemon, player), SealBuilder.instance);
        }).build(10, nature));

        //Alterar IVs do Pokémon
        ItemStack ivs = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(PluginConfig.ivIcon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.edit.ivs.name"), pokemon, plyer)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.edit.ivs.lore"), pokemon, plyer)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("ivs")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "ivs")) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.ivs.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonIVsInventory.get(pokemon, player), SealBuilder.instance);
        }).build(16, ivs));

        return menu.build(SealBuilder.instance);
    }

    private static MenuBuilder getBase() {
        MenuBuilder builder = new MenuBuilder()
                .setRows(5)
                .setTitle(LangConfig.get("menu.edit.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> purpleGlassSlots = Arrays.asList(9, 13, 17, 18, 26, 27, 35);

        for(int i = 0; i < 45; i++) {
            if(purpleGlassSlots.contains(i)) {
                builder.addClickableItem(new ClickableItem.Builder().build(i, purpleGlass.copy()));
            } else {
                builder.addClickableItem(new ClickableItem.Builder().build(i, whiteGlass.copy()));
            }
        }

        return builder;
    }

}
