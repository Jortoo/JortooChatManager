package me.jqrtox.chatmanager.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        PlayerData playerData = PlayerData.getPlayerData(event.getPlayer().getUniqueId(), false);
        MiniMessage mm = MiniMessage.miniMessage();
        Component msg = mm.deserialize("<dark_gray>(<red>❌<dark_gray>) <white><u>" + event.getPlayer().getName() + "</u> has left the server");
        event.quitMessage(msg);

        if (playerData == null) return;

        playerData.save();
        playerData.unload();

    }

}
