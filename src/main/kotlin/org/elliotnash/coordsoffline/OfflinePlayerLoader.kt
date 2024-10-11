package org.elliotnash.coordsoffline

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.elliotnash.coordsoffline.nms.v1_17_R1
import org.elliotnash.coordsoffline.nms.v1_19_R1
import org.elliotnash.coordsoffline.nms.v1_20_R2

interface OfflinePlayerLoader {
    fun loadOfflinePlayer(offlinePlayer: OfflinePlayer): Player?
    fun loadPlayer(player: OfflinePlayer): Player? {
        return player.player ?: loadOfflinePlayer(player)
    }
}

fun getOfflinePlayerLoader(): OfflinePlayerLoader {
    val version = getMCVersion()

    return when {
        // <1.17.0 is untested
        version < MCVersion(1, 17, 0) -> {
            Bukkit.getLogger().warning("Untested MC Versions '$version', using v1_17_R1 adapter!")
            v1_17_R1()
        }
        // 1.17.0 - 1.18.2 -> v1_17_R1
        version <= MCVersion(1, 18, 2) -> v1_17_R1()
        // 1.19.0 - 1.19.2 -> v1_19_R1
        version <= MCVersion(1, 19, 2) -> v1_19_R1()
        // 1.19.3 - 1.20.1 -> v1_17_R1
        version <= MCVersion(1, 20, 1) -> v1_17_R1()
        // 1.20.2 - 1.21.1 -> v1_20_R2
        version <= MCVersion(1, 21, 1) -> v1_20_R2()
        // >1.21.1 is untested
        else -> {
            Bukkit.getLogger().warning("Untested MC Versions '$version', using v1_20_R2 adapter!")
            v1_20_R2()
        }
    }
}
