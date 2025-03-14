package me.jqrtox.chatmanager.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.jqrtox.chatmanager.commands.StaffChat;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import poa.poalib.LuckPerms.LuckPerm;
import poa.poalib.Messages.Messages;

import java.util.HashMap;
import java.util.UUID;

public class ChatFormat implements Listener {

    private static HashMap<UUID, Long> chatCooldowns = new HashMap<>();

    @EventHandler
    public void onChat(AsyncChatEvent event) {

        Player player = event.getPlayer();
        String prefix = LuckPerm.getPrefix(player);
        String tag = null;

        if (StaffChat.staffChatToggled.contains(player.getUniqueId())) {

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("staff.chat")) {
                    onlinePlayer.sendRichMessage(StaffChat.staffChatPrefix + "<yellow>" + player.getName() + ": " + "<white>" + MiniMessage.miniMessage().serialize(event.message()));
                }
            }
            event.setCancelled(true);
            return;

        }
        if (chatCooldowns.containsKey(player.getUniqueId())) {

            long timeNow = System.currentTimeMillis();
            long lastChatTime = chatCooldowns.get(player.getUniqueId());
            double difference = timeNow - lastChatTime;
            double differenceSeconds = difference / 1000;

            if (differenceSeconds < 1){
                player.sendRichMessage("<dark_gray>[<yellow><bold>ChatCooldown</bold><dark_gray>] <gray>▸ <red>You have to wait before chatting again! <gray>(" + String.format("%.2f", 1 - differenceSeconds) + "s)");
                event.setCancelled(true);
                return;
            }

        }
        if (!player.hasPermission("staff.cooldown.bypass")) {

            long timeNow = System.currentTimeMillis();

            chatCooldowns.put(player.getUniqueId(), timeNow);

        }
        if (prefix == null) {
            prefix = "";
        }

        String plainTextPrefix = PlainTextComponentSerializer.plainText().serialize(event.message());
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId(), true);
        String chatColor = playerData.getChatColor();

        if (playerData.getTag() != null) {

            String getTag = playerData.getTag();

            tag = "<dark_gray>[" + Messages.essentialsToMinimessage(getTag) + "<reset><dark_gray>]";
            event.message(MiniMessage.miniMessage().deserialize(prefix + "<white>" + player.getName() + " " + tag + " <gray>▸ " + chatColor + "<message>", Placeholder.unparsed("message", plainTextPrefix)));

        }
        else {
            event.message(MiniMessage.miniMessage().deserialize(prefix + "<white>" + player.getName() + " <gray>▸ " + chatColor + "<message>", Placeholder.unparsed("message", plainTextPrefix)));
        }
        event.renderer((sender, displayName, inputMessage, viewingPlayer) -> event.message());
    }

}
