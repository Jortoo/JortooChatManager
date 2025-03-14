package me.jqrtox.chatmanager.events;

import lombok.Getter;
import lombok.SneakyThrows;
import me.jqrtox.chatmanager.ChatManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import poa.poalib.yml.PoaYaml;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerData {

    public static ConcurrentHashMap<UUID, PlayerData> dataMap = new ConcurrentHashMap<>();

    private static final File folder = new File(ChatManager.plugin.getDataFolder(), "playerdata");


    static {

        folder.mkdirs();

        Bukkit.getScheduler().runTaskTimer(ChatManager.plugin, () -> {
            for (PlayerData value : dataMap.values()) {
                value.save();
            }
        }, 12000L, 12000L);
    }

    public static PlayerData getPlayerData(UUID uuid, boolean createNew) {

        if (dataMap.containsKey(uuid)) {
            return dataMap.get(uuid);
        }
        if (createNew) {
            return new PlayerData(uuid);
        }
        return null;
    }

    @Getter
    private UUID uuid;
    @Getter
    private String chatColor = "<gray>";
    @Getter
    private String tag = null;

    private File file;
    private PoaYaml yml;

    @SneakyThrows
    public PlayerData(UUID uuid) {

        this.uuid = uuid;
        this.file = new File(folder, uuid + ".yml");
        file.createNewFile();

        yml = PoaYaml.loadFromFile(file);
        if (yml.isSet("chatcolor")) {
            this.chatColor = yml.getString("chatcolor");
        }
        if (yml.isSet("tag")) {
            this.tag = yml.getString("tag");
        }


        dataMap.put(uuid, this);
    }

    public void setChatColor(String chatColor) {
        this.chatColor = chatColor;
        yml.set("chatcolor", chatColor);
    }
    public void setTag(String tag) {
        this.tag = tag;
        yml.set("tag", tag);
    }

    @SneakyThrows
    public void save() {
        this.yml.save(file);
    }


    public void unload() {
        dataMap.remove(uuid);
    }

}
