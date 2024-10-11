package org.elliotnash.coordsoffline.nms

import java.util.UUID

abstract class v1_17_R1_v1_21_R1_Base {
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
}
