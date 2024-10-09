package org.elliotnash.coordsoffline.spigot;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.elliotnash.coordsoffline.nmsinterface.NmsManager;
import org.elliotnash.coordsoffline.v1_12_r1.v1_12_R1;
import org.elliotnash.coordsoffline.v1_15_r1.v1_15_R1;
import org.elliotnash.coordsoffline.v1_16_r1.v1_16_R1;
import org.elliotnash.coordsoffline.v1_16_r2.v1_16_R2;
import org.elliotnash.coordsoffline.v1_16_r3.v1_16_R3;
import org.elliotnash.coordsoffline.v1_17_r1.v1_17_R1;
import org.elliotnash.coordsoffline.v1_18_r1.v1_18_R1;
import org.elliotnash.coordsoffline.v1_18_r2.v1_18_R2;
import org.elliotnash.coordsoffline.v1_19_r1.v1_19_R1;
import org.elliotnash.coordsoffline.v1_19_r2.v1_19_R2;
import org.elliotnash.coordsoffline.v1_19_r3.v1_19_R3;
import org.elliotnash.coordsoffline.v1_20_r1.v1_20_R1;
import org.elliotnash.coordsoffline.v1_20_r2.v1_20_R2;
import org.elliotnash.coordsoffline.v1_20_r3.v1_20_R3;
import org.elliotnash.coordsoffline.v1_21_r1.v1_21_R1;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getOfflinePlayers;
import static org.bukkit.Bukkit.getOnlinePlayers;

public final class CoordsOffline extends JavaPlugin {
    private static FileConfiguration config;
    private static CoordsOffline plugin;
    private static Logger logger;
    public static String geyserPrefix;
    public static NmsManager nmsManager;
    public static boolean offlineSupport;

    //TODO don't show offline players in tab complete if running in compatibility mode

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();

        //initialize nms
        offlineSupport = getNMSVersion();

        //Command initialization
        this.getCommand("coords").setExecutor(new Coords());
        this.getCommand("otp").setExecutor(new OfflineTP());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean getNMSVersion(){
        String packageName = this.getServer().getClass().getPackage().getName();
        // Get full package string of CraftServer.
        // org.bukkit.craftbukkit.version
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
        // Get the last element of the package

        switch(version){
            case "v1_12_R1":{
                nmsManager = new v1_12_R1();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_15_R1":{
                nmsManager = new v1_15_R1();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_16_R1":{
                nmsManager = new v1_16_R1();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_16_R2":{
                nmsManager = new v1_16_R2();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_16_R3":{
                nmsManager = new v1_16_R3();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_17_R1":{
                nmsManager = new v1_17_R1();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_18_R1":{
                nmsManager = new v1_18_R1();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_18_R2":{
                nmsManager = new v1_18_R2();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_19_R1":{
                nmsManager = new v1_19_R1();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_19_R2":{
                nmsManager = new v1_19_R2();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_19_R3":{
                nmsManager = new v1_19_R3();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_20_R1":{
                nmsManager = new v1_20_R1();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_20_R2":{
                nmsManager = new v1_20_R2();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_20_R3":{
                nmsManager = new v1_20_R3();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            case "v1_21_R1":{
                nmsManager = new v1_21_R1();
                this.getLogger().info("Loading support for " + version);
                return true;
            }
            default:{
                this.getLogger().severe("This paper version is not supported yet - check for updates");
                this.getLogger().info("CordsOffline will be run in online only mode");
                this.getLogger().info("Running minecraft version "+version);
                return false;
            }
        }
    }

    public static OfflinePlayer getAllowedPlayer(String playerName){
        List<OfflinePlayer> offlinePLayers = getAllowedPlayers();
        for (OfflinePlayer player : offlinePLayers){
            if (Objects.equals(player.getName(), playerName)){
                return player;
            }
        }
        return null;
    }

    public static Player getPlayerForOfflinePlayer(OfflinePlayer p) {
        if (p == null) {
            return null;
        }
        if (p.isOnline()) {
            return (Player) p;
        } else if (offlineSupport) {
            return nmsManager.loadOfflinePlayer(p);
        }
        return null;
    }

    public static List<OfflinePlayer> getAllowedPlayers() {
        if (CoordsOffline.offlineSupport) {
            return Arrays.asList(getOfflinePlayers());
        } else {
            return new ArrayList<>(getOnlinePlayers());
        }
    }

    public static CoordsOffline getPlugin(){
        return plugin;
    }
}
