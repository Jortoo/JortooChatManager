package me.jqrtox.chatmanager.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import poa.poalib.Messages.Messages;

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MiniMessage mm = MiniMessage.miniMessage();
        if (player.hasPlayedBefore()) {

            int uniqueJoins = Bukkit.getOfflinePlayers().length;
            Component message = mm.deserialize("<dark_gray>(<green>✔<dark_gray>) <white><u>" + player.getName() + "</u> has joined the server");

            event.joinMessage(message);
            return;
        }
        Component message = mm.deserialize("<gray>(" + Messages.essentialsToMinimessage("new!") + ") <dark_gray>(<green>✔<dark_gray>) <white><u>" + player.getName() + "</u> has joined the server <gray>" );
        event.joinMessage(message);

    }

}
