package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.util.ConfigUtils;
import com.focamacho.sealbuilder.util.PokemonUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.common.util.Utils;
import com.focamacho.seallibrary.sponge.menu.Menu;
import com.focamacho.seallibrary.sponge.menu.item.ClickableItem;
import com.focamacho.seallibrary.sponge.util.InventoryUtils;
import com.focamacho.seallibrary.sponge.util.ItemStackUtils;
import com.focamacho.seallibrary.sponge.util.MoneyUtils;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
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

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonCreateInventory {

    private static final Menu base = getBase();

    public static Inventory get(EnumSpecies specie, Player player) {
        Menu menu = base.copy();

        Pokemon pokemon = Pixelmon.pokemonFactory.create(specie);

        Currency currency = getPokemonCurrency(specie);
        double price = getPokemonPrice(specie, player);

        String pokemonItemName = getFormattedCurrency(SealBuilderLang.getLang("menu.create.pokemon.name"), currency, price);
        String pokemonItemLore = getFormattedCurrency(SealBuilderLang.getLang("menu.create.pokemon.lore"), currency, price);
        String cancelItemName = getFormattedCurrency(SealBuilderLang.getLang("menu.create.cancel.name"), currency, price);
        String cancelItemLore = getFormattedCurrency(SealBuilderLang.getLang("menu.create.cancel.lore"), currency, price);
        String confirmItemName = getFormattedCurrency(SealBuilderLang.getLang("menu.create.confirm.name"), currency, price);
        String confirmItemLore = getFormattedCurrency(SealBuilderLang.getLang("menu.create.confirm.lore"), currency, price);

        ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(pokemonItemName, pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(pokemonItemLore, pokemon, player)).build();
        ItemStack cancelItem = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(config.cancelItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(cancelItemName, pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(cancelItemLore, pokemon, player)).build();
        ItemStack confirmItem = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(config.confirmItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(confirmItemName, pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(confirmItemLore, pokemon, player)).build();

        menu.addMenuItem(ClickableItem.create(13, pokemonItem));
        menu.addMenuItem(ClickableItem.create(10, cancelItem).setOnPrimary(click -> InventoryUtils.closeInventory((Player) click.getSource(), SealBuilder.instance)));
        menu.addMenuItem(ClickableItem.create(16, confirmItem).setOnPrimary(click -> {
            Player source = (Player) click.getSource();

            if(MoneyUtils.hasMoney(source, BigDecimal.valueOf(price), currency)) {
                PlayerPartyStorage party = Pixelmon.storageManager.getParty((EntityPlayerMP)source);
                if(party.add(pokemon)) {
                    MoneyUtils.removeMoney(source, BigDecimal.valueOf(price), currency);
                    source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.buy.success"), currency, price)));
                    InventoryUtils.openInventory(source, PokemonSelectInventory.get(source, player), SealBuilder.instance);
                    return;
                }
            } else {
                source.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), currency, price)));
            }
            InventoryUtils.closeInventory(source, SealBuilder.instance);
        }));

        return menu.get();
    }

    private static String getFormattedCurrency(String text, Currency currency, double price) {
        return text.replace("%currencykey%", currency.getSymbol().toPlain())
                .replace("%currencyname%", currency.getPluralDisplayName().toPlain())
                .replace("%price%", Utils.formatDouble(price));
    }

    private static Currency getPokemonCurrency(EnumSpecies specie) {
        Currency override = ConfigUtils.getCurrencyOverrides(specie, "create", "");
        return override != null ? override : MoneyUtils.getCurrencyByIdOrDefault(config.currencyId);
    }

    private static double getPokemonPrice(EnumSpecies specie, Player player) {
        Double override = ConfigUtils.getPriceOverrides(specie, "create", "");
        return ConfigUtils.applyDiscount(override != null ? override : specie.isLegendary() ? config.legendaryPokemonPrice : config.normalPokemonPrice, player);
    }

    private static Menu getBase() {
        Menu builder = Menu.create(SealBuilder.instance)
                .setRows(3)
                .setTitle(SealBuilderLang.getLang("menu.create.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 4, 9, 18, 19, 20, 21);
        List<Integer> emptySlots = Arrays.asList(10, 11, 12, 13, 14, 15, 16);

        for(int i = 0; i < 27; i++) {
            if(whiteGlassSlots.contains(i)) {
                builder.addMenuItem(ClickableItem.create(i, whiteGlass.copy()));
            } else if(!emptySlots.contains(i)) {
                builder.addMenuItem(ClickableItem.create(i, purpleGlass.copy()));
            }
        }

        return builder;
    }
}
