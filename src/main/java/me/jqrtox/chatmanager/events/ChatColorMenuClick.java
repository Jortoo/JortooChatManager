package me.jqrtox.chatmanager.events;

import me.jqrtox.chatmanager.ChatManager;
import me.jqrtox.chatmanager.commands.ChatColor;
import me.jqrtox.chatmanager.data.ChatColorData;
import me.jqrtox.chatmanager.holders.ChatColorHolder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ChatColorMenuClick implements Listener {

    @EventHandler
    public void chatColorMenuClick(InventoryClickEvent event) {

        if (event.getInventory().getHolder() instanceof ChatColorHolder) {

            event.setCancelled(true);

            ItemStack clickItem = event.getCurrentItem();
            if (clickItem == null || clickItem.getType() == Material.AIR) {
                return;
            }

            PersistentDataContainer data = clickItem.getItemMeta().getPersistentDataContainer();
            String chatColor = data.get(new NamespacedKey(ChatManager.plugin, "chatcolor"), PersistentDataType.STRING);

            Player player = (Player) event.getWhoClicked();

            if (!player.hasPermission("chatcolor." + chatColor)) {
                player.sendRichMessage(ChatColor.chatColorPrefix + "You do not have the required permission!");
                return;
            }
            PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId(), true);
            playerData.setChatColor(ChatColorData.colours.get(chatColor));
            player.sendRichMessage(ChatColor.chatColorPrefix + "Your chat color has been set to:" + playerData.getChatColor() + " This");

            player.playSound(player, Sound.UI_BUTTON_CLICK, 1L, 1.4f);
            player.closeInventory();


        }

    }

}
