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
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
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

public class PokemonNatureInventory {

    private static final MenuBuilder base = getBase();

    public static Inventory get(Pokemon pokemon) {
        MenuBuilder menu = base.copy();

        //Retornar ao Menu de Edição
        ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.main.pokemon.name"), pokemon)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.main.pokemon.lore"), pokemon)).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player player = (Player) click.getSource();
            InventoryUtils.openInventory(player, PokemonEditInventory.get(pokemon), SealBuilder.instance);
        }).build(1, pokemonItem));

        StatsType[] types = {StatsType.Attack, StatsType.Defence, StatsType.SpecialAttack, StatsType.SpecialDefence, StatsType.Speed};
        ItemStack[] icons = {ItemStackUtils.getStackFromID("pixelmon:pink_clock"), ItemStackUtils.getStackFromID("pixelmon:blue_clock"), ItemStackUtils.getStackFromID("pixelmon:orange_clock"), ItemStackUtils.getStackFromID("pixelmon:green_clock"), ItemStackUtils.getStackFromID("pixelmon:cyan_clock")};
        ItemStack neutral = ItemStackUtils.getStackFromID("pixelmon:white_clock");

        int neutralIndex = 0;

        for(int column = 0; column < types.length; column++) {
            for(int row = 0; row < types.length; row++) {
                EnumNature nature = getNatureByStats(types[column], types[row], neutralIndex);

                if(nature.increasedStat == StatsType.None && nature.decreasedStat == StatsType.None) neutralIndex++;

                Currency currency = getNatureCurrency(pokemon.getSpecies(), nature);
                double price = getNaturePrice(pokemon.getSpecies(), nature);

                String name = nature.getLocalizedName();
                String lore;

                if(nature == pokemon.getNature()) lore = LangConfig.get("menu.nature.your");
                else if(column == row) lore = LangConfig.get("menu.nature.neutral");
                else lore = LangConfig.get("menu.nature.lore");

                lore = getFormattedCurrency(lore.replace("%plusstats%", LangConfig.get("statstype." + types[column].name().toLowerCase())).replace("%minusstats%", LangConfig.get("statstype." + types[row].name().toLowerCase())), currency, price);
                ItemStack stack = ItemStack.builder().fromItemStack(column == row ? neutral : icons[column]).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(name, pokemon)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(lore, pokemon)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();

                menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
                    if(nature == pokemon.getNature()) return;

                    Player source = (Player) click.getSource();

                    if(MoneyUtils.hasMoney(source, BigDecimal.valueOf(price), currency)) {
                        pokemon.setNature(nature);
                        MoneyUtils.removeMoney(source, BigDecimal.valueOf(price), currency);
                        source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.edit.nature"), currency, price)));
                        InventoryUtils.openInventory(source, PokemonEditInventory.get(pokemon), SealBuilder.instance);
                        return;
                    } else {
                        source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.money.insufficient"), currency, price)));
                    }
                    InventoryUtils.closePlayerInventory(source, SealBuilder.instance);
                }).build(12 + 9 * row + column, stack));
            }
        }

        return menu.build(SealBuilder.instance);
    }

    private static EnumNature getNatureByStats(StatsType increased, StatsType decreased, int neutralIndex) {
        for (EnumNature value : EnumNature.values()) {
            if(value.increasedStat == increased && value.decreasedStat == decreased) return value;
        }
        return EnumNature.getNatureFromIndex(neutralIndex);
    }

    private static String getFormattedCurrency(String text, Currency currency, double price) {
        return text.replace("%currencykey%", currency.getSymbol().toPlain())
                .replace("%currencyname%", currency.getPluralDisplayName().toPlain())
                .replace("%price%", Utils.formatDouble(price));
    }

    private static Currency getNatureCurrency(EnumSpecies pokemon, EnumNature nature) {
        Currency override = ConfigUtils.getCurrencyOverrides(pokemon, "nature", nature.toString());
        return override != null ? override : MoneyUtils.getCurrencyByIdOrDefault(PluginConfig.currencyId);
    }

    private static double getNaturePrice(EnumSpecies pokemon, EnumNature nature) {
        Double override = ConfigUtils.getPriceOverrides(pokemon, "nature", nature.toString());
        return override != null ? override : PluginConfig.naturePrice;
    }

    private static MenuBuilder getBase() {
        MenuBuilder builder = new MenuBuilder()
                .setRows(6)
                .setTitle(LangConfig.get("menu.nature.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> purpleGlassSlots = Arrays.asList(10, 19, 28, 37, 46);
        List<Integer> whiteGlassSlots = Arrays.asList(0, 2, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 53);

        ItemStack[] icons = { ItemStackUtils.getStackFromID(PluginConfig.attackIcon), ItemStackUtils.getStackFromID(PluginConfig.defenceIcon), ItemStackUtils.getStackFromID(PluginConfig.spAttackIcon), ItemStackUtils.getStackFromID(PluginConfig.spDefenceIcon), ItemStackUtils.getStackFromID(PluginConfig.speedIcon) };

        for (int i = 0; i < 54; i++) {
            if (purpleGlassSlots.contains(i)) {
                builder.addClickableItem(new ClickableItem.Builder().build(i, purpleGlass.copy()));
            } else if(whiteGlassSlots.contains(i)){
                builder.addClickableItem(new ClickableItem.Builder().build(i, whiteGlass.copy()));
            }
        }

        int[] columnSlots = {3, 4, 5, 6, 7};
        int[] rowSlots = {11, 20, 29, 38, 47};
        String[] keys = {"attack", "defence", "spattack", "spdefence", "speed"};

        for(int i = 0; i < 5; i++) {
            ItemStack plusStack = ItemStack.builder().from(icons[i]).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.nature." + keys[i] + ".plus"))).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
            ItemStack minusStack = ItemStack.builder().from(icons[i]).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.nature." + keys[i] + ".minus"))).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
            builder.addClickableItem(new ClickableItem.Builder().build(columnSlots[i], plusStack));
            builder.addClickableItem(new ClickableItem.Builder().build(rowSlots[i], minusStack));
        }

        return builder;
    }
}
