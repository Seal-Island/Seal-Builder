package com.focamacho.sealbuilder;

import com.focamacho.sealbuilder.config.SealBuilderLang;
import com.focamacho.sealbuilder.inventory.PokemonSelectInventory;
import com.focamacho.sealbuilder.util.TextUtils;
import com.focamacho.seallibrary.player.ISealPlayer;
import com.focamacho.seallibrary.player.SealPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

@SuppressWarnings("unused")
public class SealBuilderBukkit extends JavaPlugin {

    @Override
    public void onEnable() {
        SealBuilder.onEnable(this, new File("./plugins/SealBuilder/"));

        registerCommand(new Command(SealBuilderLang.getLang("command.builder.name"), SealBuilderLang.getLang("command.builder.description"), "", Arrays.asList(SealBuilder.config.builderAliases)) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.getFormattedText(SealBuilderLang.getLang("chat.onlyplayer")));
                    return true;
                }
                ISealPlayer player = SealPlayer.get(sender);
                player.openInventory(PokemonSelectInventory.get(player, player));
                return true;
            }

            @Override
            public String getPermission() {
                return "sealbuilder.builder";
            }
        });
    }

    @Override
    public void reloadConfig() {
        SealBuilder.onReload();
    }

    private void registerCommand(Command command) {
        try {
            Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            Object commandMapObject = commandMapField.get(Bukkit.getPluginManager());
            if (commandMapObject instanceof CommandMap) {
                CommandMap commandMap = (CommandMap) commandMapObject;
                commandMap.register(command.getName(), command);
            }
        } catch(IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
