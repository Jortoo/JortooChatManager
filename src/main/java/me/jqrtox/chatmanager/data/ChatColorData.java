package me.jqrtox.chatmanager.data;

import me.jqrtox.chatmanager.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ChatColorData {

    public static Map<String, String> colours = new HashMap<>();

    public static void loadColours() {

        FileConfiguration config = ChatManager.plugin.getConfig();
        if (config.isConfigurationSection("chatcolors")) {
            for (String key : config.getConfigurationSection("chatcolors").getKeys(false)) {
                colours.put(key, config.getString("chatcolors." + key));

            }
        }

    }

}
