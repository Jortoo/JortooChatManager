package me.jqrtox.chatmanager;

import me.jqrtox.chatmanager.commands.ChatColor;
import me.jqrtox.chatmanager.commands.MessageCommand;
import me.jqrtox.chatmanager.data.ChatColorData;
import me.jqrtox.chatmanager.events.ChatFormat;
import me.jqrtox.chatmanager.events.Join;
import me.jqrtox.chatmanager.events.Leave;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ChatManager extends JavaPlugin {

    public static ChatManager plugin;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        Logger logger = Logger.getLogger("ChatManager");
        logger.info("The plugin loaded successfully");

        getCommand("msg").setExecutor(new MessageCommand());
        getCommand("chatcolor").setExecutor(new ChatColor());

        getServer().getPluginManager().registerEvents(new ChatFormat(), this);
        getServer().getPluginManager().registerEvents(new Leave(), this);
        getServer().getPluginManager().registerEvents(new Join(), this);

        ChatColorData.loadColours();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
