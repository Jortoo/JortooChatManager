package me.jqrtox.chatmanager.commands;

import me.jqrtox.chatmanager.events.PlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import poa.poalib.Messages.Messages;

import java.util.Arrays;

public class CustomTag implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length < 1) {
            player.sendRichMessage("<red>You did not specify your tag!");
            return false;
        }

        String tag = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
        String coloredTag = Messages.essentialsToMinimessage(tag);
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId(), true);

        if (tag.contains(" ")) {
            player.sendRichMessage("<red>You can not use spaces in your tag!");
            return false;
        }
        if (tag.length() > 12) {
            player.sendRichMessage("<red>Your tag can not be more than 12 characters");
            return false;
        }

        playerData.setTag(coloredTag);
        player.sendRichMessage(coloredTag);

        return false;
    }
}
