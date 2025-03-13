package me.jqrtox.chatmanager.commands;

import me.jqrtox.chatmanager.data.ChatColorData;
import me.jqrtox.chatmanager.events.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ChatColor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length < 1) {
            player.sendRichMessage("<red>/Chatcolor <argument 1>");
            return false;
        }
        if (args[0].equalsIgnoreCase("Colors") || args[0].equalsIgnoreCase("Colours")) {
            player.sendRichMessage("<dark_purple><bold>Chatcolors:<reset>");
            for (Map.Entry<String, String> entry : ChatColorData.colours.entrySet()) {
                player.sendRichMessage("<dark_gray> - " + entry.getValue() + entry.getKey());

            }
            return false;
        }

        String chatColor = args[0];

        if (!ChatColorData.colours.containsKey(chatColor)) {
            player.sendRichMessage("<red>This chat color does not exist!");
            return false;
        }

        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId(), true);
        playerData.setChatColor(ChatColorData.colours.get(args[0]));
        player.sendRichMessage("<green> Your chat color has been set to: " + playerData.getChatColor() + " This");

        return false;
    }
}
