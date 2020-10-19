package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.LangConfig;
import com.focamacho.sealbuilder.config.PluginConfig;
import com.focamacho.sealbuilder.listener.CreatePokemonListener;
import com.focamacho.sealbuilder.util.PokemonUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.menu.ClickableItem;
import com.focamacho.seallibrary.menu.MenuBuilder;
import com.focamacho.seallibrary.util.InventoryUtils;
import com.focamacho.seallibrary.util.ItemStackUtils;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PokemonSelectInventory {

    private static final MenuBuilder base = getBase();
    private static final int[] basePokemonSlots = new int[]{12, 13, 14, 21, 22, 23};

    public static Inventory get(Player player) {
        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(playerMP);

        MenuBuilder menu = base.copy();

        boolean canCreatePokemon = PluginConfig.createPokemon && player.hasPermission(PluginConfig.createPokemonPermission);
        ItemStack noPokemonItem = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(PluginConfig.noPokemonItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(canCreatePokemon ? LangConfig.get("menu.main.create") : LangConfig.get("menu.main.nopokemon"), player)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();

        for(int i = 0; i < basePokemonSlots.length; i++) {
            Pokemon pokemon = party.get(i);

            if(pokemon != null) {
                ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(LangConfig.get("menu.main.pokemon.name"), pokemon)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(LangConfig.get("menu.main.pokemon.lore"), pokemon)).build();
                menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> InventoryUtils.openInventory(player, PokemonEditInventory.get(pokemon), SealBuilder.instance)).build(basePokemonSlots[i], pokemonItem));
            } else {
                menu.addClickableItem(new ClickableItem.Builder().onPrimary(click -> {
                    //Criar pokémon
                    if(canCreatePokemon) {
                        Player source = (Player) click.getSource();
                        source.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.create"), source));
                        CreatePokemonListener.players.add(source.getUniqueId());
                        Task.builder().delay(2, TimeUnit.MINUTES).execute(() -> CreatePokemonListener.players.remove(source.getUniqueId())).submit(SealBuilder.instance);
                        InventoryUtils.closePlayerInventory(source, SealBuilder.instance);
                    }
                }).build(basePokemonSlots[i], noPokemonItem));
            }
        }

        return menu.build(SealBuilder.instance);
    }

    private static MenuBuilder getBase() {
        MenuBuilder builder = new MenuBuilder()
                .setRows(4)
                .setTitle(LangConfig.get("menu.main.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 4, 9, 10, 11, 18, 19, 20, 27, 28, 29, 30);

        for(int i = 0; i < 36; i++) {
            if(whiteGlassSlots.contains(i)) {
                builder.addClickableItem(new ClickableItem.Builder().build(i, whiteGlass.copy()));
            } else {
                builder.addClickableItem(new ClickableItem.Builder().build(i, purpleGlass.copy()));
            }
        }

        return builder;
    }

}
