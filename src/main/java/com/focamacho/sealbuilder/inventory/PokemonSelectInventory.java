package com.focamacho.sealbuilder.inventory;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.listener.CreatePokemonListener;
import com.focamacho.sealbuilder.util.PokemonUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.sponge.menu.Menu;
import com.focamacho.seallibrary.sponge.menu.item.ClickableItem;
import com.focamacho.seallibrary.sponge.util.InventoryUtils;
import com.focamacho.seallibrary.sponge.util.ItemStackUtils;
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

import static com.focamacho.sealbuilder.SealBuilder.config;

public class PokemonSelectInventory {

    private static final Menu base = getBase();
    private static final int[] basePokemonSlots = new int[]{12, 13, 14, 21, 22, 23};

    public static Inventory get(Player player, Player target) {
        EntityPlayerMP playerMP = (EntityPlayerMP) target;
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(playerMP);

        Menu menu = base.copy();

        boolean canCreatePokemon = target.hasPermission("sealbuilder.create");
        ItemStack noPokemonItem = ItemStack.builder().fromItemStack(ItemStackUtils.getStackFromID(config.noPokemonItem)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(canCreatePokemon ? SealBuilderLang.getLang("menu.main.create") : SealBuilderLang.getLang("menu.main.nopokemon"), target)).add(Keys.HIDE_ATTRIBUTES, true).add(Keys.HIDE_MISCELLANEOUS, true).build();

        for(int i = 0; i < basePokemonSlots.length; i++) {
            Pokemon pokemon = party.get(i);

            if(pokemon != null) {
                ItemStack pokemonItem = ItemStack.builder().from(PokemonUtils.getPokemonAsItem(pokemon)).add(Keys.DISPLAY_NAME, TextUtils.getFormattedText(SealBuilderLang.getLang("menu.main.pokemon.name"), pokemon, target)).add(Keys.ITEM_LORE, TextUtils.getFormattedLore(SealBuilderLang.getLang("menu.main.pokemon.lore"), pokemon, target)).build();
                menu.addMenuItem(ClickableItem.create(basePokemonSlots[i], pokemonItem).setOnPrimary(click -> InventoryUtils.openInventory(player, PokemonEditInventory.get(pokemon, target), SealBuilder.instance)));
            } else {
                menu.addMenuItem(ClickableItem.create(basePokemonSlots[i], noPokemonItem).setOnPrimary(click -> {
                    //Criar pokémon
                    if(canCreatePokemon) {
                        Player source = (Player) click.getSource();
                        source.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.create"), source));
                        CreatePokemonListener.players.put(source.getUniqueId(), target);
                        Task.builder().delay(2, TimeUnit.MINUTES).execute(() -> CreatePokemonListener.players.remove(source.getUniqueId())).submit(SealBuilder.instance);
                        InventoryUtils.closeInventory(source, SealBuilder.instance);
                    }
                }));
            }
        }

        return menu.get();
    }

    private static Menu getBase() {
        Menu builder = Menu.create(SealBuilder.instance)
                .setRows(4)
                .setTitle(SealBuilderLang.getLang("menu.main.title"));

        ItemStack whiteGlass = ItemStack.builder().itemType(ItemTypes.STAINED_GLASS_PANE).add(Keys.DISPLAY_NAME, Text.of("")).build();
        ItemStack purpleGlass = ItemStack.builder().fromContainer(ItemTypes.STAINED_GLASS_PANE.getTemplate().toContainer().set(DataQuery.of("UnsafeDamage"), 10)).add(Keys.DISPLAY_NAME, Text.of("")).build();

        List<Integer> whiteGlassSlots = Arrays.asList(0, 1, 2, 3, 4, 9, 10, 11, 18, 19, 20, 27, 28, 29, 30);

        for(int i = 0; i < 36; i++) {
            if(whiteGlassSlots.contains(i)) {
                builder.addMenuItem(ClickableItem.create(i, whiteGlass.copy()));
            } else {
                builder.addMenuItem(ClickableItem.create(i, purpleGlass.copy()));
            }
        }

        return builder;
    }

}
