package me.jqrtox.chatmanager.commands;

import me.jqrtox.chatmanager.data.ChatColorData;
import me.jqrtox.chatmanager.events.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import poa.poalib.Messages.Messages;
import poa.poalib.TabComplete.EasyTabComplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatColor implements CommandExecutor, TabCompleter {

    public static String chatColorPrefix = "<dark_gray>[<dark_purple><bold>CHATCOLORS</bold><dark_gray>] <gray>▸<white> ";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length < 1) {
            player.sendRichMessage(chatColorPrefix + "/chatcolor <color>");
            return false;
        }

        MiniMessage mm = MiniMessage.miniMessage();

        if (args[0].equalsIgnoreCase("Colors") || args[0].equalsIgnoreCase("Colours")) {

            player.sendRichMessage(" <dark_gray>[<dark_purple><bold>Chatcolors:<reset><dark_gray>]\n");

            for (Map.Entry<String, String> entry : ChatColorData.colours.entrySet()) {

                String colorCode = entry.getValue();
                String colorName = entry.getKey();
                String permissionSymbols;

                if (player.hasPermission("chatcolor." + entry.getKey())) {
                    permissionSymbols = "<green> ● ";
                }
                else {
                    permissionSymbols = "<red> ● ";
                }

                Component component = mm.deserialize("<dark_gray> ▸" + permissionSymbols + " " + colorCode + colorName + " <gray>(" + Messages.essentialsToMinimessage("click!") + ")")
                        .clickEvent(ClickEvent.runCommand("/chatcolor " + colorName))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to select this chat color")));

                player.sendMessage(component);

            }

            player.sendRichMessage(" ");

            return false;
        }

        String chatColor = args[0];

        if (!ChatColorData.colours.containsKey(chatColor)) {
            player.sendRichMessage(chatColorPrefix + "This chat color does not exist!");
            return false;
        }
        if (!player.hasPermission("chatcolor." + args[0])) {
            player.sendRichMessage(chatColorPrefix + "You do not have the required permission");
            return false;
        }

        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId(), true);
        playerData.setChatColor(ChatColorData.colours.get(args[0]));
        player.sendRichMessage(chatColorPrefix + "Your chat color has been set to:" + playerData.getChatColor() + " This");

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 1) {

            List<String> list = new ArrayList<>();
            list.addAll(ChatColorData.colours.keySet());
            list.add("colors");

            return EasyTabComplete.correctTabComplete(list, args[0]);
        }

        return List.of();
    }

    //@Override
    //public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
//
    //    List<String> rt = new ArrayList<>();
    //    List<String> list = new ArrayList<>();
    //    if (args.length == 1){
    //        list.addAll(ChatColorData.colours.keySet());
    //        list.add("colors");
    //    }
    //    for (String string : list) {
    //        if (string.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
    //            rt.add(string);
    //        }
    //    }
    //    return rt;
    //}



}
