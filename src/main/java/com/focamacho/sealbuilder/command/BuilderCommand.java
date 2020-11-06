package com.focamacho.sealbuilder.command;

import com.focamacho.sealbuilder.SealBuilder;
import com.focamacho.sealbuilder.config.LangConfig;
import com.focamacho.sealbuilder.inventory.PokemonSelectInventory;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.util.InventoryUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class BuilderCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) {
            src.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.onlyplayer")));
            return CommandResult.success();
        }

        Player player = (Player) src;

        if(args.getOne("player").isPresent()) {
            if(src.hasPermission("sealbuilder.admin")) {
                InventoryUtils.openInventory(player, PokemonSelectInventory.get((Player)args.getOne("player").get()), SealBuilder.instance);
            } else {
                src.sendMessage(TextUtils.getFormattedText(LangConfig.get("chat.nopermission")));
            }
        } else {
            InventoryUtils.openInventory(player, PokemonSelectInventory.get(player), SealBuilder.instance);
        }

        return CommandResult.success();
    }

}