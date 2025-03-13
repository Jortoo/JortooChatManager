package me.jqrtox.chatmanager.events;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import poa.poalib.LuckPerms.LuckPerm;

public class ChatFormat implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {

        Player player = event.getPlayer();
        String prefix = LuckPerm.getPrefix(player);
        if (prefix == null) {
            prefix = "";
        } ;
        String serialize = PlainTextComponentSerializer.plainText().serialize(event.message());
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId(), true);
        String chatColor = playerData.getChatColor();
        event.message(MiniMessage.miniMessage().deserialize(prefix + "<white>" + player.getName() + " <dark_gray>> " + chatColor + "<message>", Placeholder.unparsed("message", serialize)));
        event.renderer((sender, displayName, inputMessage, viewingPlayer) -> event.message());
    }

}
