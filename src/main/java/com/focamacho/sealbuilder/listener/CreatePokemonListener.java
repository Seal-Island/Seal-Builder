package com.focamacho.sealbuilder.listener;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.LangConfig;
import com.focamacho.sealbuilder.inventory.PokemonCreateInventory;
import com.focamacho.sealbuilder.util.ConfigUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.util.InventoryUtils;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CreatePokemonListener {

    public static List<UUID> players = new ArrayList<>();

    @Listener
    public void onChatMessage(MessageChannelEvent.Chat event) {
        if(event.getSource() instanceof Player) {
            Player player = (Player) event.getSource();
            Iterator<UUID> it = players.iterator();
            while(it.hasNext()) {
                UUID uuid = it.next();
                if(uuid.equals(player.getUniqueId())) {
                    event.setCancelled(true);
                    it.remove();

                    EnumSpecies specie = EnumSpecies.getFromNameAnyCase(event.getRawMessage().toPlain());

                    if(ConfigUtils.isBlacklisted(specie, "create")) {
                        player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.create.blacklist"), player));
                        return;
                    }

                    if(specie == null) {
                        player.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.prefix") + LangConfig.get("chat.nopokemon"), player));
                        return;
                    }

                    InventoryUtils.openInventory(player, PokemonCreateInventory.get(specie, player), SealBuilder.instance);
                }
            }
        }
    }

}
