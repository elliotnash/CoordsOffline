package org.elliotnash.coordsoffline

import org.bukkit.plugin.java.JavaPlugin
import org.elliotnash.coordsoffline.command.Coords
import org.elliotnash.coordsoffline.command.OfflineTP

class CoordsOfflinePlugin : JavaPlugin() {
    override fun onEnable() {
        getCommand("coords")?.setExecutor(Coords())
        getCommand("otp")?.setExecutor(OfflineTP())
    }
}
