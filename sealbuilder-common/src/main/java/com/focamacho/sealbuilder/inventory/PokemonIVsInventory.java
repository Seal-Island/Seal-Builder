package com.focamacho.sealbuilder.inventory;

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
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonIVsInventory {

    private static final AbstractMenu base;
    private static final int[] slots = {5, 14, 23, 32, 41, 50};
    private static final StatsType[] types = {StatsType.HP, StatsType.Attack, StatsType.Defence, StatsType.SpecialAttack, StatsType.SpecialDefence, StatsType.Speed};
    public static final ISealStack[] icons = {SealStack.get("pixelmon:fairy_gem"), SealStack.get("pixelmon:psychic_gem"), SealStack.get("pixelmon:water_gem"), SealStack.get("pixelmon:fire_gem"), SealStack.get("pixelmon:grass_gem"), SealStack.get("pixelmon:ice_gem")};
    public static final Random rand = new Random();

    static {
        AbstractMenu builder = Menu.create()
                .setRows(6)
                .setTitle(SealBuilderLang.getLang("menu.ivs.title"));

        ISealStack whiteGlass = SealStack.get("minecraft:stained_glass_pane").setName("");
        ISealStack purpleGlass = SealStack.get("minecraft:stained_glass_pane:10").setName("");

        List<Integer> purpleGlassSlots = Arrays.asList(4, 10, 13, 22, 28, 29, 31, 40, 49);

        for (int i = 0; i < 54; i++) {
            if (purpleGlassSlots.contains(i)) {
                builder.addItem(ClickableItem.create(i, purpleGlass.copy()));
            } else {
                builder.addItem(ClickableItem.create(i, whiteGlass.copy()));
            }
        }

        base = builder;
    }

    public static AbstractMenu get(Pokemon pokemon, ISealPlayer player) {
        AbstractMenu menu = base.copy();

        //Retornar ao Menu de Edição
        ISealStack pokemonItem = ForgeUtils.getServerStack(ItemPixelmonSprite.getPhoto(pokemon)).setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.main.pokemon.name"), pokemon, player)).setLore(TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.main.pokemon.lore"), pokemon, player));
        menu.addItem(ClickableItem.create(11, pokemonItem).setOnPrimary(click -> click.getPlayer().openInventory(PokemonEditInventory.get(pokemon, click.getPlayer()))));

        int[] ivs = pokemon.getIVs().getArray();

        for(int i = 0; i < 6; i++) {
            StatsType type = types[i];

            ISealStack stack = icons[i].copy().setName(TextUtils.getFormattedText(SealBuilderLang.getLang("statstype." + type.name().toLowerCase()), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
            stack.setAmount(ivs[i]);
            menu.addItem(ClickableItem.create(slots[i], ivs[i] > 0 ? stack : SealStack.get("")));

            String currency = getStatsCurrency(pokemon.getSpecies(), types[i]);
            double price = getStatsPrice(pokemon.getSpecies(), types[i]);

            ISealStack plus = SealStack.get("pixelmon:green_apricorn").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.ivs." + type.name().toLowerCase() + ".plus").replace("%priceiv10%", TextUtils.df.format(price * 10)), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(ivs[i] > 30 ? SealBuilderLang.getLang("menu.ivs.limit.plus") : SealBuilderLang.getLang("menu.ivs.lore.plus").replace("%priceiv10%", TextUtils.df.format(price * 10)), currency, price), pokemon, player));
            ISealStack minus = SealStack.get("pixelmon:red_apricorn").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.ivs." + type.name().toLowerCase() + ".minus").replace("%priceiv10%", TextUtils.df.format(price * 10)), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(ivs[i] < 1 ? SealBuilderLang.getLang("menu.ivs.limit.minus") : SealBuilderLang.getLang("menu.ivs.lore.minus").replace("%priceiv10%", TextUtils.df.format(price * 10)), currency, price), pokemon, player));

            int index = i;
            menu.addItem(ClickableItem.create(slots[i] + 1, minus).setOnPrimary((click) -> {
                handleStatsChange(index, pokemon, click.getPlayer(), currency, price, -1);
                update(click.getPlayer(), menu, pokemon);
            }).setOnShift((click) -> {
                handleStatsChange(index, pokemon, click.getPlayer(), currency, price, -10);
                update(click.getPlayer(), menu, pokemon);
            }));

            menu.addItem(ClickableItem.create(slots[i] + 2, plus).setOnPrimary((click) -> {
                handleStatsChange(index, pokemon, click.getPlayer(), currency, price, 1);
                update(click.getPlayer(), menu, pokemon);
            }).setOnShift((click) -> {
                handleStatsChange(index, pokemon, click.getPlayer(), currency, price, 10);
                update(click.getPlayer(), menu, pokemon);
            }));
        }

        ISealStack random = SealStack.get("pixelmon:super_potion").setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.ivs.random.name"), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.ivs.random.lore"), getStatsCurrency(pokemon.getSpecies(), types[0]), config.randomIvPrice), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        menu.addItem(ClickableItem.create(37, random).setOnPrimary(click -> {
            ISealPlayer player2 = click.getPlayer();
            if(player2.hasMoney(config.randomIvPrice, getStatsCurrency(pokemon.getSpecies(), types[0]))) {
                player2.removeMoney(config.randomIvPrice, getStatsCurrency(pokemon.getSpecies(), types[0]));
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
                update(click.getPlayer(), menu, pokemon);
                player2.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.ivs.random"), getStatsCurrency(pokemon.getSpecies(), types[0]), config.randomIvPrice)));
            } else {
                player2.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), getStatsCurrency(pokemon.getSpecies(), types[0]), config.randomIvPrice)));
            }
        }));

        boolean isMax = Arrays.stream(ivs).sum() >= 186;
        int missingPoints = 186 - Arrays.stream(ivs).sum();
        ISealStack max = SealStack.get("pixelmon:hyper_potion").setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.ivs.max.name"), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(isMax ? SealBuilderLang.getLang("menu.ivs.limit.plus") : SealBuilderLang.getLang("menu.ivs.max.lore").replace("%ivmaxprice%", TextUtils.df.format(config.ivMaxPrice * missingPoints)), getStatsCurrency(pokemon.getSpecies(), types[0]), getStatsPrice(pokemon.getSpecies(), types[0])), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        menu.addItem(ClickableItem.create(38, max).setOnPrimary(click -> {
            if(Arrays.stream(pokemon.getIVs().getArray()).sum() >= 155) return;

            ISealPlayer player2 = click.getPlayer();
            int pointsToAdd = 155 - Arrays.stream(pokemon.getIVs().getArray()).sum();
            double price = pointsToAdd * config.ivMaxPrice;
            if(player2.hasMoney(price, getStatsCurrency(pokemon.getSpecies(), types[0]))) {
                pokemon.getIVs().fillFromArray(new int[]{31, 31, 31, 31, 31, 31});
                player2.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.ivs.plus"), pokemon, player));
                player2.removeMoney(price, getStatsCurrency(pokemon.getSpecies(), types[0]));
                update(click.getPlayer(), menu, pokemon);
            } else {
                player2.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), getStatsCurrency(pokemon.getSpecies(), types[0]), price)));
            }
        }));

        return menu;
    }

    private static void handleStatsChange(int index, Pokemon pokemon, ISealPlayer player, String currency, double price, int quantity) {
        int[] ivs = pokemon.getIVs().getArray();

        if(ivs[index] + quantity > 31) {
            player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.ivs.limit.max"), pokemon, player));
            return;
        } else if(ivs[index] + quantity < 0) {
            player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.ivs.limit.min"), pokemon, player));
            return;
        }

        double finalPrice = price * Math.abs(quantity);

        if(player.hasMoney(finalPrice, currency)) {
            ivs[index] += quantity;
            pokemon.getIVs().fillFromArray(ivs);
            if(quantity > 0) {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.ivs.plus"), pokemon, player));
            } else {
                player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.ivs.minus"), pokemon, player));
            }
            player.removeMoney(finalPrice, currency);
        } else {
            player.sendMessage(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.money.insufficient"), currency, price)));
        }
    }

    private static void update(ISealPlayer player, AbstractMenu menu, Pokemon pokemon) {
        int[] ivs = pokemon.getIVs().getArray();

        for(int i = 0; i < 6; i++) {
            StatsType type = types[i];

            ISealStack stack = icons[i].copy().setName(TextUtils.getFormattedText(SealBuilderLang.getLang("statstype." + type.name().toLowerCase()), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
            stack.setAmount(ivs[i]);
            ((ClickableItem)menu.getItem(slots[i])).setItem(ivs[i] > 0 ? stack : SealStack.get(""));

            String currency = getStatsCurrency(pokemon.getSpecies(), types[i]);
            double price = getStatsPrice(pokemon.getSpecies(), types[i]);

            ISealStack plus = SealStack.get("pixelmon:green_apricorn").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.ivs." + type.name().toLowerCase() + ".plus").replace("%priceiv10%", TextUtils.df.format(price * 10)), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(ivs[i] > 30 ? SealBuilderLang.getLang("menu.ivs.limit.plus") : SealBuilderLang.getLang("menu.ivs.lore.plus").replace("%priceiv10%", TextUtils.df.format(price * 10)), currency, price), pokemon, player));
            ISealStack minus = SealStack.get("pixelmon:red_apricorn").setName(TextUtils.getFormattedText(TextUtils.getFormattedCurrency(SealBuilderLang.getLang("menu.ivs." + type.name().toLowerCase() + ".minus").replace("%priceiv10%", TextUtils.df.format(price * 10)), currency, price), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(ivs[i] < 1 ? SealBuilderLang.getLang("menu.ivs.limit.minus") : SealBuilderLang.getLang("menu.ivs.lore.minus").replace("%priceiv10%", TextUtils.df.format(price * 10)), currency, price), pokemon, player));

            ((ClickableItem)menu.getItem(slots[i] + 1)).setItem(minus);
            ((ClickableItem)menu.getItem(slots[i] + 2)).setItem(plus);
        }

        boolean isMax = Arrays.stream(ivs).sum() >= 186;
        int missingPoints = 186 - Arrays.stream(ivs).sum();
        ISealStack max = SealStack.get("pixelmon:hyper_potion").setName(TextUtils.getFormattedText(SealBuilderLang.getLang("menu.ivs.max.name"), pokemon, player)).setLore(TextUtils.getFormattedLore(TextUtils.getFormattedCurrency(isMax ? SealBuilderLang.getLang("menu.ivs.limit.plus") : SealBuilderLang.getLang("menu.ivs.max.lore").replace("%ivmaxprice%", TextUtils.df.format(config.ivMaxPrice * missingPoints)), getStatsCurrency(pokemon.getSpecies(), types[0]), getStatsPrice(pokemon.getSpecies(), types[0])), pokemon, player)).setFlag(ItemFlag.HIDE_ATTRIBUTES, true).setFlag(ItemFlag.HIDE_POTION_EFFECTS, true);
        ((ClickableItem)menu.getItem(38)).setItem(max);

        menu.update();
    }

    private static String getStatsCurrency(EnumSpecies pokemon, StatsType type) {
        String override = ConfigUtils.getCurrencyOverrides(pokemon, ModuleTypes.IVS, type.toString());
        return override != null ? override : config.currencyId;
    }

    private static double getStatsPrice(EnumSpecies pokemon, StatsType type) {
        Double override = ConfigUtils.getPriceOverrides(pokemon, ModuleTypes.IVS, type.toString());
        return override != null ? override : config.ivPrice;
    }

}
