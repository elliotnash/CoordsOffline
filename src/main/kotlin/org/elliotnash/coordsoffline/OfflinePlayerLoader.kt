package org.elliotnash.coordsoffline

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.elliotnash.coordsoffline.nms.v1_17_R1
import org.elliotnash.coordsoffline.nms.v1_19_R1
import org.elliotnash.coordsoffline.nms.v1_20_R2

interface OfflinePlayerLoader {
    companion object {
        private fun warnUntestedVersion(version: MCVersion, adapter: String) {
            val log = Bukkit.getLogger()
            log.warning("Untested MC Versions '${version}', using $adapter adapter!")
            log.warning("")
            log.warning("CoordsOffline may continue to function.")
            log.warning("Please file an issue at https://github.com/elliotnash/CoordsOffline/issues and include")
            log.warning("1. Is CoordsOffline working as expected?")
            log.warning("2. The following version information:")
            log.warning("${Bukkit.getVersion()};${Bukkit.getBukkitVersion()};$version;$adapter")
        }

        val instance: OfflinePlayerLoader by lazy {
            val version = getMCVersion()

            when {
                // <1.17.0 is untested
                version < MCVersion(1, 17, 0) -> {
                    warnUntestedVersion(version, "v1_17_R1")
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
                    warnUntestedVersion(version, "v1_20_R2")
                    v1_20_R2()
                }
            }
        }
    }

    fun loadOfflinePlayer(offlinePlayer: OfflinePlayer): Player?
    fun loadPlayer(player: OfflinePlayer): Player? {
        return player.player ?: loadOfflinePlayer(player)
    }
}
