package me.jqrtox.chatmanager.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class StaffChat implements CommandExecutor {

    public static List<UUID> staffChatToggled = new ArrayList<>();
    public static String staffChatPrefix = "<dark_gray>[<dark_purple><bold>STAFF<light_purple>CHAT</bold><dark_gray>] <gray>▸<white> ";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) {


            UUID uniqueId = player.getUniqueId();
            if (staffChatToggled.contains(uniqueId)) {
                staffChatToggled.remove(uniqueId);
                player.sendRichMessage(staffChatPrefix + "You have <red>Disabled <white>staff chat!");
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1L, 1L);
                return false;
            }
            else {
                if (AdminChat.adminChatToggled.contains(uniqueId)) {
                    AdminChat.adminChatToggled.remove(uniqueId);
                }
                staffChatToggled.add(uniqueId);
                player.sendRichMessage(staffChatPrefix + "You have <green>Enabled <white>staff chat!");
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1L, 1L);
                return false;
            }
        }
        else {

            String join = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
            MiniMessage.miniMessage().deserialize(join);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                if (onlinePlayer.hasPermission("staff.chat")) {

                    onlinePlayer.sendRichMessage(staffChatPrefix + "<yellow>" + player.getName() + ": " + "<white>" + join);

                }

            }

        }

        return true;
    }
}
