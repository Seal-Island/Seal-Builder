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
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PokemonIVsInventory {

    private static final MenuBuilder base = getBase();
    private static final int[] slots = {5, 14, 23, 32, 41, 50};
    private static final StatsType[] types = {StatsType.HP, StatsType.Attack, StatsType.Defence, StatsType.SpecialAttack, StatsType.SpecialDefence, StatsType.Speed};
    public static final ItemStack[] icons = {ItemStackUtils.getStackFromID(PluginConfig.hpIcon), ItemStackUtils.getStackFromID(PluginConfig.attackIcon), ItemStackUtils.getStackFromID(PluginConfig.defenceIcon), ItemStackUtils.getStackFromID(PluginConfig.spAttackIcon), ItemStackUtils.getStackFromID(PluginConfig.spDefenceIcon), ItemStackUtils.getStackFromID(PluginConfig.speedIcon)};
    public static final Random rand = new Random();

    public static Inventory get(Pokemon pokemon, Player player) {
        MenuBuilder menu = base.copy();

        //Retornar ao Menu de Edição
        ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.main.pokemon.name"), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.main.pokemon.lore"), pokemon, player)).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            Player player2 = (Player) click.getSource();
            InventoryUtils.openInventory(player2, PokemonEditInventory.get(pokemon, player2), SealBuilder.instance);
        }).build(11, pokemonItem));

        int[] ivs = pokemon.getIVs().getArray();

        for(int i = 0; i < 6; i++) {
            StatsType type = types[i];

            ItemStack stack = ItemStack.builder().fromItemStack(icons[i].copy()).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("statstype." + type.name().toLowerCase()), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
            stack.setQuantity(ivs[i]);
            menu.addClickableItem(new ClickableItem.Builder().build(slots[i], ivs[i] > 0 ? stack : ItemStack.empty()));

            Currency currency = getStatsCurrency(pokemon.getSpecies(), types[i]);
            double price = getStatsPrice(pokemon.getSpecies(), types[i], player);

            ItemStack plus = ItemStack.builder().from(ItemStackUtils.getStackFromID(PluginConfig.confirmItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("menu.ivs." + type.name().toLowerCase() + ".plus").replace("%priceiv10%", Utils.formatDouble(price * 10)), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(ivs[i] > 30 ? LangConfig.get("menu.ivs.limit.plus") : LangConfig.get("menu.ivs.lore.plus").replace("%priceiv10%", Utils.formatDouble(price * 10)), currency, price), pokemon, player)).build();
            ItemStack minus = ItemStack.builder().from(ItemStackUtils.getStackFromID(PluginConfig.cancelItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("menu.ivs." + type.name().toLowerCase() + ".minus").replace("%priceiv10%", Utils.formatDouble(price * 10)), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(ivs[i] < 1 ? LangConfig.get("menu.ivs.limit.minus") : LangConfig.get("menu.ivs.lore.minus").replace("%priceiv10%", Utils.formatDouble(price * 10)), currency, price), pokemon, player)).build();

            int index = i;
            menu.addClickableItem(new ClickableItem.Builder().onPrimary((click) -> {
                if(!(click.getSource() instanceof Player)) return;
                handleStatsChange(index, pokemon, (Player)click.getSource(), currency, price, -1);
                updateStats(pokemon, click.getTargetInventory(), player);
            }).onShift((click) -> {
                if(!(click.getSource() instanceof Player)) return;
                handleStatsChange(index, pokemon, (Player)click.getSource(), currency, price, -10);
                updateStats(pokemon, click.getTargetInventory(), player);
            }).build(slots[i] + 1, minus));

            menu.addClickableItem(new ClickableItem.Builder().onPrimary((click) -> {
                if(!(click.getSource() instanceof Player)) return;
                handleStatsChange(index, pokemon, (Player)click.getSource(), currency, price, 1);
                updateStats(pokemon, click.getTargetInventory(), player);
            }).onShift((click) -> {
                if(!(click.getSource() instanceof Player)) return;
                handleStatsChange(index, pokemon, (Player)click.getSource(), currency, price, 10);
                updateStats(pokemon, click.getTargetInventory(), player);
            }).build(slots[i] + 2, plus));
        }

        ItemStack random = ItemStack.builder().from(ItemStackUtils.getStackFromID("pixelmon:super_potion")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.ivs.random.name"), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(LangConfig.get("menu.ivs.random.lore"), getStatsCurrency(pokemon.getSpecies(), types[0]), ConfigUtils.applyDiscount(PluginConfig.randomIvPrice, player)), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            if(!(click.getSource() instanceof Player)) return;
            Player player2 = (Player) click.getSource();
            if(MoneyUtils.hasMoney(player2, BigDecimal.valueOf(ConfigUtils.applyDiscount(PluginConfig.randomIvPrice, player2)), getStatsCurrency(pokemon.getSpecies(), types[0]))) {
                MoneyUtils.removeMoney(player2, BigDecimal.valueOf(ConfigUtils.applyDiscount(PluginConfig.randomIvPrice, player2)), getStatsCurrency(pokemon.getSpecies(), types[0]));
                List<Integer> perfected = new ArrayList<>();
                for(int i = 0; i < 3; i++) {
                    int rn = rand.nextInt(6);
                    while(perfected.contains(rn)) {
                        rn = rand.nextInt(6);
                    }
                    perfected.add(rn);
                }
                int[] newIvs = new int[6];
                for(int i = 0; i < 6; i++) {
                    if(perfected.contains(i)) newIvs[i] = 31;
                    else newIvs[i] = rand.nextInt(32);
                }
                pokemon.getIVs().fillFromArray(newIvs);
                updateStats(pokemon, click.getTargetInventory(), player2);
                player2.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.ivs.random"), getStatsCurrency(pokemon.getSpecies(), types[0]), PluginConfig.randomIvPrice)));
            } else {
                player2.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.money.insufficient"), getStatsCurrency(pokemon.getSpecies(), types[0]), PluginConfig.randomIvPrice)));
            }
        }).build(37, random));

        boolean isMax = Arrays.stream(ivs).sum() >= 155;
        int missingPoints = 155 - Arrays.stream(ivs).sum();
        ItemStack max = ItemStack.builder().from(ItemStackUtils.getStackFromID("pixelmon:hyper_potion")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.ivs.max.name"), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(isMax ? LangConfig.get("menu.ivs.limit.plus") : LangConfig.get("menu.ivs.max.lore").replace("%ivmaxprice%", Utils.formatDouble(ConfigUtils.applyDiscount(PluginConfig.ivMaxPrice * missingPoints, player))), getStatsCurrency(pokemon.getSpecies(), types[0]), getStatsPrice(pokemon.getSpecies(), types[0], player)), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
        menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
            if(!(click.getSource() instanceof Player)) return;
            if(Arrays.stream(pokemon.getIVs().getArray()).sum() >= 155) return;

            Player player2 = (Player) click.getSource();
            int pointsToAdd = 155 - Arrays.stream(pokemon.getIVs().getArray()).sum();
            double price = ConfigUtils.applyDiscount(pointsToAdd * PluginConfig.ivMaxPrice, player2);
            if(MoneyUtils.hasMoney(player2, BigDecimal.valueOf(price), getStatsCurrency(pokemon.getSpecies(), types[0]))) {
                pokemon.getIVs().fillFromArray(new int[]{31, 31, 31, 31, 31, 31});
                player2.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.ivs.plus"), pokemon, player));
                MoneyUtils.removeMoney(player2, BigDecimal.valueOf(price), getStatsCurrency(pokemon.getSpecies(), types[0]));
                updateStats(pokemon, click.getTargetInventory(), player2);
            } else {
                player2.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.money.insufficient"), getStatsCurrency(pokemon.getSpecies(), types[0]), price)));
            }
        }).build(38, max));

        return menu.build();
    }

    private static void handleStatsChange(int index, Pokemon pokemon, Player player, Currency currency, double price, int quantity) {
        int[] ivs = pokemon.getIVs().getArray();

        if(ivs[index] + quantity > 31) {
            player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.ivs.limit.max"), pokemon, player));
            return;
        } else if(ivs[index] + quantity < 0) {
            player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.ivs.limit.max"), pokemon, player));
            return;
        }

        double finalPrice = price * quantity;

        if(MoneyUtils.hasMoney(player, BigDecimal.valueOf(finalPrice), currency)) {
            ivs[index] += quantity;
            pokemon.getIVs().fillFromArray(ivs);
            if(quantity > 0) {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.ivs.plus"), pokemon, player));
            } else {
                player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.ivs.minus"), pokemon, player));
            }
            MoneyUtils.removeMoney(player, BigDecimal.valueOf(finalPrice), currency);
        } else {
            player.sendMessage(TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("chat.prefix") + LangConfig.get("chat.money.insufficient"), currency, price)));
        }
    }

    private static void updateStats(Pokemon pokemon, Inventory inventory, Player player) {
        Task.builder().execute(() -> {
            Iterable<Inventory> slots = inventory.slots();
            int[] ivs = pokemon.getIVs().getArray();
            int index = 0;
            for(Inventory slot : slots) {
                StatsType type = types[index];
                int slotIndex = slot.getInventoryProperty(SlotIndex.class).get().getValue();
                if(slotIndex == 11) {
                    ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.main.pokemon.name"), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.main.pokemon.lore"), pokemon, player)).build();
                    slot.set(pokemonItem);
                } else if(slotIndex == 38) {
                    boolean isMax = Arrays.stream(ivs).sum() >= 155;
                    int missingPoints = 155 - Arrays.stream(ivs).sum();
                    ItemStack max = ItemStack.builder().from(ItemStackUtils.getStackFromID("pixelmon:hyper_potion")).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.ivs.max.name"), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(isMax ? LangConfig.get("menu.ivs.limit.plus") : LangConfig.get("menu.ivs.max.lore").replace("%ivmaxprice%", Utils.formatDouble(PluginConfig.ivMaxPrice * missingPoints)), getStatsCurrency(pokemon.getSpecies(), types[0]), getStatsPrice(pokemon.getSpecies(), types[0], player)), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
                    slot.set(max);
                } else {
                    for(int i : PokemonIVsInventory.slots) {
                        if(slotIndex == i) {
                            ItemStack stack = ItemStack.builder().fromItemStack(icons[index].copy()).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("statstype." + type.name().toLowerCase()), pokemon, player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();
                            stack.setQuantity(ivs[index]);
                            slot.set(ivs[index] > 0 ? stack : ItemStack.empty());
                            break;
                        } else if(slotIndex == i + 1) {
                            Currency currency = getStatsCurrency(pokemon.getSpecies(), type);
                            double price = getStatsPrice(pokemon.getSpecies(), type, player);

                            ItemStack minus = ItemStack.builder().from(ItemStackUtils.getStackFromID(PluginConfig.cancelItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("menu.ivs." + type.name().toLowerCase() + ".minus").replace("%priceiv10%", Utils.formatDouble(price * 10)), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(ivs[index] < 1 ? LangConfig.get("menu.ivs.limit.minus") : LangConfig.get("menu.ivs.lore.minus").replace("%priceiv10%", Utils.formatDouble(price * 10)), currency, price), pokemon, player)).build();
                            slot.set(minus);
                            break;
                        } else if(slotIndex == i + 2) {
                            Currency currency = getStatsCurrency(pokemon.getSpecies(), type);
                            double price = getStatsPrice(pokemon.getSpecies(), type, player);

                            ItemStack plus = ItemStack.builder().from(ItemStackUtils.getStackFromID(PluginConfig.confirmItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(getFormattedCurrency(LangConfig.get("menu.ivs." + type.name().toLowerCase() + ".plus").replace("%priceiv10%", Utils.formatDouble(price * 10)), currency, price), pokemon, player)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(getFormattedCurrency(ivs[index] > 30 ? LangConfig.get("menu.ivs.limit.plus") : LangConfig.get("menu.ivs.lore.plus").replace("%priceiv10%", Utils.formatDouble(price * 10)), currency, price), pokemon, player)).build();
                            slot.set(plus);
                            index++;
                            break;
                        }
                    }
                }
                if(index > 5) break;
            }
        }).submit(SealBuilder.instance);
    }

    private static String getFormattedCurrency(String text, Currency currency, double price) {
        return text.replace("%currencykey%", currency.getSymbol().toPlain())
                .replace("%currencyname%", currency.getPluralDisplayName().toPlain())
                .replace("%price%", Utils.formatDouble(price));
    }

    private static Currency getStatsCurrency(EnumSpecies pokemon, StatsType type) {
        Currency override = ConfigUtils.getCurrencyOverrides(pokemon, "stats", type.toString());
        return override != null ? override : MoneyUtils.getCurrencyByIdOrDefault(PluginConfig.currencyId);
    }

    private static double getStatsPrice(EnumSpecies pokemon, StatsType type, Player player) {
        Double override = ConfigUtils.getPriceOverrides(pokemon, "stats", type.toString());
        return ConfigUtils.applyDiscount(override != null ? override : PluginConfig.ivPrice, player);
    }

    private static MenuBuilder getBase() {
        MenuBuilder builder = MenuBuilder.create(SealBuilder.instance)
                .setRows(6)
                .setTitle(LangConfig.get("menu.ivs.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> purpleGlassSlots = Arrays.asList(4, 10, 13, 22, 28, 29, 31, 40, 49);

        for (int i = 0; i < 54; i++) {
            if (purpleGlassSlots.contains(i)) {
                builder.addClickableItem(new ClickableItem.Builder().build(i, purpleGlass.copy()));
            } else {
                builder.addClickableItem(new ClickableItem.Builder().build(i, whiteGlass.copy()));
            }
        }

        return builder;
    }
}
