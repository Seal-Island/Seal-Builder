package com.focamacho.sealbuilder.command;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.inventory.PokemonSelectInventory;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.command.ISealCommand;
import com.focamacho.seallibrary.command.lib.ISealCommandSender;
import com.focamacho.seallibrary.player.ISealPlayer;

public class BuilderCommand implements ISealCommand {

    @Override
    public String getName() {
        return SealBuilderLang.getLang("command.builder.name");
    }

    @Override
    public String getDescription() {
        return SealBuilderLang.getLang("command.builder.description");
    }

    @Override
    public String[] getAliases() {
        return SealBuilder.config.builderAliases;
    }

    @Override
    public String getPermission() {
        return "sealbuilder.builder";
    }

    @Override
    public void run(ISealCommandSender sender, String[] args) {
        if(!sender.isPlayer()) {
            sender.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.onlyplayer")));
            return;
        }

        ISealPlayer player = sender.getPlayer();
        player.openInventory(PokemonSelectInventory.get(player, player));
    }

}
