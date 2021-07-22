package org.elliotnash.coordsoffline.v1_17_r1;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.world.level.World;
import org.elliotnash.coordsoffline.nmsinterface.NmsManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class v1_17_R1 implements NmsManager {
    public Player loadOfflinePlayer(OfflinePlayer player) {
        MinecraftServer minecraftserver = MinecraftServer.getServer();
        GameProfile gameprofile = new GameProfile(player.getUniqueId(), player.getName());
        EntityPlayer entity = new EntityPlayer(
                minecraftserver,
                minecraftserver.getWorldServer(World.f),
                gameprofile
        );

        final Player target = entity.getBukkitEntity();
        if (target != null)
            target.loadData();
        return target;
    }
}

