package org.elliotnash.coordsoffline.nms

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.elliotnash.coordsoffline.OfflinePlayerLoader
import java.util.UUID

class v1_20_R2 : OfflinePlayerLoader {
    // ResourceKey class
    val resourceKeyClass = Class.forName("net.minecraft.resources.ResourceKey")

    // MinecraftServer.getServer() method
    val minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer")
    val minecraftServerGetServerMethod = minecraftServerClass.getMethod("getServer")
    val minecraftServerGetLevelMethod = minecraftServerClass.getMethod("getLevel", resourceKeyClass)

    // GameProfile(id: UUID, name: String) constructor
    val gameProfileClass = Class.forName("com.mojang.authlib.GameProfile")
    val gameProfileConstructor = gameProfileClass.getConstructor(UUID::class.java, String::class.java)

    // Level class
    val levelClass = Class.forName("net.minecraft.world.level.Level")
    val levelOVERWORLDField = levelClass.getField("OVERWORLD")

    // ServerLevel class
    val serverLevelClass = Class.forName("net.minecraft.server.level.ServerLevel")

    // ServerPlayer class
    val serverPlayerClass = Class.forName("net.minecraft.server.level.ServerPlayer")
    val serverPlayerGetBukkitEntity = serverPlayerClass.getMethod("getBukkitEntity")

    // Get nms MinecraftServer instance
    val minecraftServerInstance = minecraftServerGetServerMethod.invoke(null)

    // ClientInformation.createDefault() method
    private val clientInformationClass = Class.forName("net.minecraft.server.level.ClientInformation")
    private val clientInformationCreateDefaultMethod = clientInformationClass.getMethod("createDefault")

    // ServerPlayer(minecraftServer: MinecraftServer, worldServer: ServerLevel, gameProfile: GameProfile, clientInformation: ClientInformation)
    private val serverPlayerConstructor = serverPlayerClass.getConstructor(minecraftServerClass, serverLevelClass, gameProfileClass, clientInformationClass)

    override fun loadOfflinePlayer(offlinePlayer: OfflinePlayer): Player? {
        // new GameProfile(offlinePlayer.uniqueId, offlinePlayer.name)
        val gameProfile = gameProfileConstructor.newInstance(offlinePlayer.uniqueId, offlinePlayer.name)
        // new ServerPlayer(server, level, gameProfile, clientInformation)
        val serverPlayer = serverPlayerConstructor.newInstance(
            minecraftServerInstance,
            // minecraftServerInstance.getLevel(Level.OVERWORLD)
            minecraftServerGetLevelMethod.invoke(minecraftServerInstance, levelOVERWORLDField.get(null)),
            gameProfile,
            clientInformationCreateDefaultMethod.invoke(null)
        )

        // String test = "HI";
        val test = 3

        // serverPlayer.getBukkitEntity()
        val player = serverPlayerGetBukkitEntity.invoke(serverPlayer) as Player?
        player?.loadData()
        return player
    }
}
