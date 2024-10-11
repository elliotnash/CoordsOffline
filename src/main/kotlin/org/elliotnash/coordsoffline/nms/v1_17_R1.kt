package org.elliotnash.coordsoffline.nms

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.elliotnash.coordsoffline.OfflinePlayerLoader

class v1_17_R1 : OfflinePlayerLoader, v1_17_R1_v1_21_R1_Base() {
    // ServerPlayer(minecraftServer: MinecraftServer, worldServer: ServerLevel, gameProfile: GameProfile)
    private val serverPlayerConstructor = serverPlayerClass.getConstructor(minecraftServerClass, serverLevelClass, gameProfileClass)

    override fun loadOfflinePlayer(offlinePlayer: OfflinePlayer): Player? {
        // new GameProfile(offlinePlayer.uniqueId, offlinePlayer.name)
        val gameProfile = gameProfileConstructor.newInstance(offlinePlayer.uniqueId, offlinePlayer.name)
        // new ServerPlayer(server, level, gameProfile)
        val serverPlayer = serverPlayerConstructor.newInstance(
            minecraftServerInstance,
            // minecraftServerInstance.getLevel(Level.OVERWORLD)
            minecraftServerGetLevelMethod.invoke(minecraftServerInstance, levelOVERWORLDField.get(null)),
            gameProfile
        )

        // serverPlayer.getBukkitEntity()
        val player = serverPlayerGetBukkitEntity.invoke(serverPlayer) as Player?
        player?.loadData()
        return player
    }
}
