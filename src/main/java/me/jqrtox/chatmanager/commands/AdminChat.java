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

public class AdminChat implements CommandExecutor {

    public static String adminChatPrefix = "<dark_gray>[<blue><bold>ADMIN<aqua>CHAT</bold><dark_gray>] <gray>▸ <white>";
    public static List<UUID> adminChatToggled = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) {
            UUID uniqueId = player.getUniqueId();
            if (adminChatToggled.contains(uniqueId)) {
                adminChatToggled.remove(uniqueId);
                player.sendRichMessage(adminChatPrefix + "You have <red>Disabled <white>Admin chat");
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1L, 1L);
                return false;
            }else{
                if (StaffChat.staffChatToggled.contains(uniqueId)) {
                    StaffChat.staffChatToggled.remove(uniqueId);
                }
                adminChatToggled.add(uniqueId);
                player.sendRichMessage(adminChatPrefix + "You have <green>Enabled <white>Admin chat");
                player.playSound(player, Sound.UI_BUTTON_CLICK, 1L, 1L);
                return false;
            }
        }

        String join = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
        MiniMessage.miniMessage().deserialize(join);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("admin.chat")) {
                onlinePlayer.sendRichMessage(adminChatPrefix + "<yellow>" + player.getName() + ": <white>" + join);
            }
        }

        return true;
    }
}
