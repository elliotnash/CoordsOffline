package org.elliotnash.coordsoffline.main;

import org.elliotnash.coordsoffline.nmsinterface.NmsManager;
import org.elliotnash.coordsoffline.v1_15_r1.v1_15_R1;
import org.elliotnash.coordsoffline.v1_16_r1.v1_16_R1;
import org.elliotnash.coordsoffline.v1_16_r2.v1_16_R2;
import org.elliotnash.coordsoffline.v1_16_r3.v1_16_R3;
import org.elliotnash.coordsoffline.v1_17_r1.v1_17_R1;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    private static FileConfiguration config;
    private static Main plugin;
    private static Logger logger;
    public static String geyserPrefix;
    public static NmsManager nmsManager;
    public static boolean offlineSupport;

    //TODO don't show offline players in tabcomplete if running in compatibility mode

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();

        //initialize nms
        offlineSupport = getNMSVersion();
        if (!offlineSupport){
            this.setEnabled(false);
            return;
        }


        //Command initialization
        this.getCommand("coords").setExecutor(new Coords());

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
            default:{
                this.getLogger().severe("This paper version is not supported yet - check for updates");
                this.getLogger().info("CordsOffline will be run in online only mode");
                this.getLogger().info("Running paper version "+version);
                return false;
            }
        }
    }

    public static Main getPlugin(){
        return plugin;
    }
}
