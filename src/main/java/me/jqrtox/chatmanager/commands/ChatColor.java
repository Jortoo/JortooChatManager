package me.jqrtox.chatmanager.commands;

import me.jqrtox.chatmanager.inventories.ChatColorMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class ChatColor implements CommandExecutor {

    public static String chatColorPrefix = "<dark_gray>[<dark_purple><bold>CHATCOLORS</bold><dark_gray>] <gray>▸<white> ";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length > 0) {
            player.sendRichMessage(chatColorPrefix  + "Invalid use, /chatcolors");
            return false;
        }

        ChatColorMenu.chatColorMenu(player);

        return true;
    }
}







