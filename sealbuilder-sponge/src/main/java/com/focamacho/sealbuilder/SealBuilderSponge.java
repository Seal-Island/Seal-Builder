package com.focamacho.sealbuilder;

import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.inventory.PokemonSelectInventory;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.io.File;

@Plugin(
        id = "sealbuilder",
        name = "Seal Builder",
        description = "PokeBuilder para Pixelmon Reforged",
        dependencies = @Dependency(id = "seallibrary"),
        authors = {
                "Focamacho"
        }
)
public class SealBuilderSponge {

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        SealBuilder.onEnable(this, new File("./config/SealBuilder/"));

        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .description(Text.of(SealBuilderLang.getLang("command.builder.description")))
                .permission("sealbuilder.builder")
                .executor((source, exec) -> {
                    if(!(source instanceof Player)) {
                        source.sendMessage(Text.of(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.onlyplayer"))));
                        return CommandResult.success();
                    }

                    ISealPlayer player = SealPlayer.get(source);
                    player.openInventory(PokemonSelectInventory.get(player, player));
                    return CommandResult.success();
                }).build(), SealBuilder.config.builderAliases);
    }

    @Listener
    public void onServerReload(GameReloadEvent event) {
        SealBuilder.onReload();
    }
}
