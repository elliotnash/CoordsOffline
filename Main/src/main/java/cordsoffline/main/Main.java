package cordsoffline.main;

import cordsoffline.nmsinterface.NmsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    private static FileConfiguration config;
    private static Main plugin;
    private static Logger logger;
    public static String geyserPrefix;
    public NmsManager nmsManager;

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();

        this.saveDefaultConfig();
        config = this.getConfig();

        //initialize nms



        //Command initialization
        this.getCommand("coords").setExecutor(new Coords());

        if (config.getBoolean("geyserSupport"))
            geyserPrefix = config.getString("geyserPrefix");
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

        try {
            final Class<?> clazz = Class.forName("org.kitteh.example.plugin.nms." + version + ".NMSHandler");
            // Check if we have a NMSHandler class at that location.
            if (NmsManager.class.isAssignableFrom(clazz)) { // Make sure it actually implements NMS
                this.nmsManager = (NmsManager) clazz.getConstructor().newInstance(); // Set our handler
            }
        } catch (final Exception e) {
            e.printStackTrace();
            this.getLogger().severe("This paper version is not supported yet");
            this.getLogger().info("CordsOffline will be run in online only mode");
            return false;
        }
        this.getLogger().info("Loading support for " + version);
        return true;
    }

    public static Main getPlugin(){
        return plugin;
    }
}
