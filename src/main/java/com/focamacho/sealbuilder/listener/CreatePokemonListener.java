package com.focamacho.sealbuilder.listener;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.inventory.PokemonCreateInventory;
import com.focamacho.sealbuilder.util.ConfigUtils;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.sponge.util.InventoryUtils;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class CreatePokemonListener {

    public static Map<UUID, Player> players = new HashMap<>();

    @Listener
    public void onChatMessage(MessageChannelEvent.Chat event) {
        if(event.getSource() instanceof Player) {
            Player player = (Player) event.getSource();
            Iterator<Map.Entry<UUID, Player>> it = players.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<UUID, Player> entry = it.next();
                UUID uuid = entry.getKey();
                Player target = entry.getValue();
                if(uuid.equals(player.getUniqueId())) {
                    event.setCancelled(true);
                    it.remove();

                    EnumSpecies specie = EnumSpecies.getFromNameAnyCase(event.getRawMessage().toPlain());

                    if(ConfigUtils.isBlacklisted(specie, "create")) {
                        player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.create.blacklist"), player));
                        return;
                    }

                    if(specie == null) {
                        player.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.prefix") + SealBuilderLang.getLang("chat.nopokemon"), player));
                        return;
                    }

                    InventoryUtils.openInventory(player, PokemonCreateInventory.get(specie, target), SealBuilder.instance);
                }
            }
        }
    }

}
