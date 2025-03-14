package me.jqrtox.chatmanager.commands;

import me.jqrtox.chatmanager.events.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EraseTag implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        String getTag = PlayerData.getPlayerData(player.getUniqueId(), true).getTag();
        if (getTag == null) {
            player.sendRichMessage("<red>You do no have a custom tag active!");
            return false;
        }

        PlayerData.getPlayerData(player.getUniqueId(), true).setTag(null);
        player.sendRichMessage("<red>You have deleted your tag");

        return true;
    }
}
