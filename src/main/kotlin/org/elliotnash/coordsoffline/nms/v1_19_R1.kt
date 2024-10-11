package org.elliotnash.coordsoffline.nms

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.elliotnash.coordsoffline.OfflinePlayerLoader

class v1_19_R1 : OfflinePlayerLoader, v1_17_R1_v1_21_R1_Base() {
    // ProfilePublicKey
    private val profilePublicKeyClass = Class.forName("net.minecraft.world.entity.player.ProfilePublicKey")

    // ServerPlayer(minecraftServer: MinecraftServer, worldServer: ServerLevel, gameProfile: GameProfile, profilePublicKey: ProfilePublicKey)
    private val serverPlayerConstructor = serverPlayerClass.getConstructor(minecraftServerClass, serverLevelClass, gameProfileClass, profilePublicKeyClass)

    override fun loadOfflinePlayer(offlinePlayer: OfflinePlayer): Player? {
        // new GameProfile(offlinePlayer.uniqueId, offlinePlayer.name)
        val gameProfile = gameProfileConstructor.newInstance(offlinePlayer.uniqueId, offlinePlayer.name)
        // new ServerPlayer(server, level, gameProfile, null)
        val serverPlayer = serverPlayerConstructor.newInstance(
            minecraftServerInstance,
            // minecraftServerInstance.getLevel(Level.OVERWORLD)
            minecraftServerGetLevelMethod.invoke(minecraftServerInstance, levelOVERWORLDField.get(null)),
            gameProfile,
            null
        )

        // serverPlayer.getBukkitEntity()
        val player = serverPlayerGetBukkitEntity.invoke(serverPlayer) as Player?
        player?.loadData()
        return player
    }
}
