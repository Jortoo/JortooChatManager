package me.jqrtox.chatmanager.inventories;

import me.jqrtox.chatmanager.ChatManager;
import me.jqrtox.chatmanager.data.ChatColorData;
import me.jqrtox.chatmanager.holders.ChatColorHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatColorMenu {

    public static Inventory chatColorMenu(Player player) {

        //Creating the inventory
        Inventory inv = Bukkit.createInventory(new ChatColorHolder(), 54, MiniMessage.miniMessage().deserialize("<black><u>Chatcolors"));
        //Creating the fillup material
        ItemStack fillup = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillupMeta = fillup.getItemMeta();
        fillupMeta.setHideTooltip(true);
        fillup.setItemMeta(fillupMeta);

        //Setting the black border in the gui
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, fillup);
        }
        for (int i = 45; i < 54; i++) {
            inv.setItem(i, fillup);
        }
        int i2 = 0;
        for (int i = 0; i < 46; i++){
            i2++;
            if (i2 == 9){
                inv.setItem(i, fillup);
                inv.setItem(i+1, fillup);
                i2 = 0;
            }
        }

        //Setting the chat color selection slots
        int loopIteration = 0;
        int slot = 10;
        for (Map.Entry<String, String> entry : ChatColorData.colours.entrySet()) {

            if (loopIteration == 7) {
                loopIteration = 0;
                slot = slot + 2;
            }
            loopIteration = loopIteration + 1;

            //Creating the items for the chat color selection slots
            ItemStack slotItem = new ItemStack(Material.BOOK);
            ItemMeta slotMeta = slotItem.getItemMeta();
            MiniMessage mm = MiniMessage.miniMessage();

            String hasPermission;

            if (player.hasPermission("chatcolor." + entry.getKey())) {
                hasPermission = "<green>Yes";
            }
            else{
                hasPermission = "<red>No";
            }

            List<Component> lore = new ArrayList<>();
            lore.add(mm.deserialize("<!italic><dark_gray>Chat Color"));
            lore.add(mm.deserialize(""));
            lore.add(mm.deserialize(entry.getValue() + "<!italic><bold> | </bold><white>Preview: " + player.getName() + " <gray>▸ " + entry.getValue() + "Hello!"));
            lore.add(mm.deserialize(entry.getValue() + "<!italic><bold> | </bold><white>Permission: " + hasPermission));
            lore.add(mm.deserialize(""));
            lore.add(mm.deserialize("<dark_gray>((<gray> Click to equip <dark_gray>))"));
            slotMeta.lore(lore);

            String key = entry.getKey().replaceAll("_", " ");
            String capitalizedKey = key.substring(0, 1).toUpperCase() + key.substring(1);

            slotMeta.displayName(MiniMessage.miniMessage().deserialize("<!italic>" + entry.getValue() + capitalizedKey + " <white><u>Chat color"));

            NamespacedKey chatColorName = new NamespacedKey(ChatManager.plugin, "chatcolor");
            PersistentDataContainer container = slotMeta.getPersistentDataContainer();
            container.set(chatColorName, PersistentDataType.STRING, entry.getKey());

            slotItem.setItemMeta(slotMeta);

            inv.setItem(slot, slotItem);
            slot++;

        }
        player.openInventory(inv);
        return inv;

    }

}
