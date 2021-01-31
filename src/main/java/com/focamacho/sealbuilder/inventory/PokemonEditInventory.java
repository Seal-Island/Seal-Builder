package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.util.ConfigUtils;
import com.focamacho.sealbuilder.util.PokemonUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.sponge.menu.Menu;
import com.focamacho.seallibrary.sponge.menu.item.ClickableItem;
import com.focamacho.seallibrary.sponge.util.InventoryUtils;
import com.focamacho.seallibrary.sponge.util.ItemStackUtils;
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

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonEditInventory {

    private static final Menu base = getBase();

    public static Inventory get(Pokemon pokemon, Player target) {
        Menu menu = base.copy();

        //Retornar ao Menu de Seleção
        ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(SealBuilderLang.getLang("menu.main.pokemon.name"), pokemon, target)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.main.pokemon.lore"), pokemon, target)).build();
        menu.addMenuItem(ClickableItem.create(22, pokemonItem).setOnPrimary(click -> {
            Player player = (Player) click.getSource();
            InventoryUtils.openInventory(player, PokemonSelectInventory.get(player, target), SealBuilder.instance);
        }));

        //Modificar Pokébola
        ItemStack pokeball = ItemStack.builder().from(ItemStackUtil.fromNative(new net.minecraft.item.ItemStack(pokemon.getCaughtBall().getItem()))).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.pokeball.name"), pokemon, target)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.pokeball.lore"), pokemon, target)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addMenuItem(ClickableItem.create(31, pokeball).setOnPrimary(click -> {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("pokeball")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "pokeball")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.pokeball.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonPokeballInventory.get(pokemon, target), SealBuilder.instance);
        }));

        //Alternar Shiny
        ItemStack shiny = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(config.shinyIcon)).add(Keys.ITEM_ENCHANTMENTS, pokemon.isShiny() ? Collections.singletonList(Enchantment.builder().type(EnchantmentTypes.UNBREAKING).level(1).build()) : Collections.emptyList()).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.shiny.name"), pokemon, target)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.shiny.lore"), pokemon, target)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).add(Keys.HIDE_ENCHANTMENTS, true).build();
        menu.addMenuItem(ClickableItem.create(19, shiny).setOnPrimary(click ->  {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("shiny")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "shiny")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.shiny.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonShinyInventory.get(pokemon, target), SealBuilder.instance);
        }));

        //Alterar Tamanho do Pokémon
        ItemStack growth = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(config.growthIcon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.growth.name"), pokemon, target)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.growth.lore"), pokemon, target)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addMenuItem(ClickableItem.create(28, growth).setOnPrimary(click -> {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("growth")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "growth")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.growth.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonGrowthInventory.get(pokemon, target), SealBuilder.instance);
        }));

        //Liberar Habilidade Oculta
        boolean hasHiddenAbility = pokemon.getBaseStats().abilities.length == 3 && pokemon.getBaseStats().abilities[2] != null && !pokemon.getBaseStats().abilities[0].equals(pokemon.getBaseStats().abilities[2]);
        boolean isHiddenAbility = !(pokemon.getAbilitySlot() < 2 && hasHiddenAbility);
        ItemStack hiddenAbility = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(config.hiddenAbilityIcon)).add(Keys.ITEM_ENCHANTMENTS, isHiddenAbility ? Collections.singletonList(Enchantment.builder().type(EnchantmentTypes.UNBREAKING).level(1).build()) : Collections.emptyList()).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.hiddenability.name"), pokemon, target)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(hasHiddenAbility ? isHiddenAbility ? SealBuilderLang.getLang("menu.edit.hiddenability.your") : SealBuilderLang.getLang("menu.edit.hiddenability.lore") : SealBuilderLang.getLang("menu.edit.hiddenability.none"), pokemon, target)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).add(Keys.HIDE_ENCHANTMENTS, true).build();
        menu.addMenuItem(ClickableItem.create(25, hiddenAbility).setOnPrimary(click ->  {
            Player player = (Player) click.getSource();
            if(!hasHiddenAbility) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.nohidden")));
                return;
            }
            if(isHiddenAbility) return;
            if(ConfigUtils.isModifierDisabled("hiddenability")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "hiddenability")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.hiddenability.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonHiddenAbilityInventory.get(pokemon, target), SealBuilder.instance);
        }));

        //Alternar Gênero do Pokémon
        boolean hasOtherGender = Math.abs(pokemon.getBaseStats().malePercent - 50) < 50;
        ItemStack gender = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(pokemon.getGender() == Gender.None ? config.noGenderIcon : pokemon.getGender() == Gender.Female ? config.femaleGenderIcon : config.maleGenderIcon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.gender.name"), pokemon, target)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(hasOtherGender ? SealBuilderLang.getLang("menu.edit.gender.lore") : SealBuilderLang.getLang("menu.edit.gender.nogender"), pokemon, target)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).add(Keys.HIDE_ENCHANTMENTS, true).build();
        menu.addMenuItem(ClickableItem.create(34, gender).setOnPrimary(click ->  {
            Player player = (Player) click.getSource();
            if(!hasOtherGender) return;
            if(ConfigUtils.isModifierDisabled("gender")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "gender")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.gender.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonGenderInventory.get(pokemon, target), SealBuilder.instance);
        }));

        //Alterar Natureza do Pokémon
        ItemStack nature = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(config.natureIcon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.nature.name"), pokemon, target)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.nature.lore"), pokemon, target)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addMenuItem(ClickableItem.create(10, nature).setOnPrimary(click -> {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("nature")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "nature")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.nature.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonNatureInventory.get(pokemon, target), SealBuilder.instance);
        }));

        //Alterar IVs do Pokémon
        ItemStack ivs = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(config.ivIcon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(SealBuilderLang.getLang("menu.edit.ivs.name"), pokemon, target)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.edit.ivs.lore"), pokemon, target)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addMenuItem(ClickableItem.create(16, ivs).setOnPrimary(click -> {
            Player player = (Player) click.getSource();
            if(ConfigUtils.isModifierDisabled("ivs")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.modifier.disabled")));
                return;
            }
            if(ConfigUtils.isBlacklisted(pokemon, "ivs")) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.ivs.blacklist")));
            } else InventoryUtils.openInventory(player, PokemonIVsInventory.get(pokemon, target), SealBuilder.instance);
        }));

        return menu.get();
    }

    private static Menu getBase() {
        Menu builder = Menu.create(SealBuilder.instance)
                .setRows(5)
                .setTitle(SealBuilderLang.getLang("menu.edit.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> purpleGlassSlots = Arrays.asList(9, 13, 17, 18, 26, 27, 35);

        for(int i = 0; i < 45; i++) {
            if(purpleGlassSlots.contains(i)) {
                builder.addMenuItem(ClickableItem.create(i, purpleGlass.copy()));
            } else {
                builder.addMenuItem(ClickableItem.create(i, whiteGlass.copy()));
            }
        }

        return builder;
    }

}
