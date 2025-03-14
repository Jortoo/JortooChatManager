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

public class ChatFormat implements Listener {


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
        if (prefix == null) {
            prefix = "";
        }

        String serialize = PlainTextComponentSerializer.plainText().serialize(event.message());
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId(), true);
        String chatColor = playerData.getChatColor();

        if (playerData.getTag() != null) {
            String getTag = playerData.getTag();
            tag = "<dark_gray>[" + Messages.essentialsToMinimessage(getTag) + "<reset><dark_gray>]";
            event.message(MiniMessage.miniMessage().deserialize(prefix + "<white>" + player.getName() + " " + tag + " <dark_gray>> " + chatColor + "<message>", Placeholder.unparsed("message", serialize)));

        }
        else {
            event.message(MiniMessage.miniMessage().deserialize(prefix + "<white>" + player.getName() + " <dark_gray>> " + chatColor + "<message>", Placeholder.unparsed("message", serialize)));
        }

        event.renderer((sender, displayName, inputMessage, viewingPlayer) -> event.message());
    }

}
