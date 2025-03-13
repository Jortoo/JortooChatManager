package me.jqrtox.chatmanager.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MessageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) {
            player.sendRichMessage("<red>You did not specify the player you want to send this message to");
            return false;
        } else if (args.length == 1){
            player.sendRichMessage("<red>You did not add a message");
            return false;
        }

        Player receiver = Bukkit.getPlayer(args[0]);

        if (receiver == null) {
            player.sendRichMessage("<red>Player is not online");
            return false;
        }
        if (receiver == player) {
            player.sendRichMessage("<red>You can't message yourself");
            return false;
        }

        String join = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        join = PlainTextComponentSerializer.plainText().serialize(MiniMessage.miniMessage().deserialize(join));
        receiver.sendRichMessage("<aqua>From " + player.getName() + ": <white>" + join);
        player.sendRichMessage("<aqua>To " + receiver.getName() + ": <white>" + join);

        return false;
    }
}
