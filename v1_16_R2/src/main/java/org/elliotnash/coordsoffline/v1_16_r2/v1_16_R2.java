package org.elliotnash.coordsoffline.v1_16_r2;

import com.mojang.authlib.GameProfile;
import org.elliotnash.coordsoffline.nmsinterface.NmsManager;
import net.minecraft.server.v1_16_R2.EntityPlayer;
import net.minecraft.server.v1_16_R2.MinecraftServer;
import net.minecraft.server.v1_16_R2.PlayerInteractManager;
import net.minecraft.server.v1_16_R2.World;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class v1_16_R2 implements NmsManager {
    public Player loadOfflinePlayer(OfflinePlayer player) {
        MinecraftServer minecraftserver = MinecraftServer.getServer();
        GameProfile gameprofile = new GameProfile(player.getUniqueId(), player.getName());
        EntityPlayer entity = new EntityPlayer(minecraftserver,
                minecraftserver.getWorldServer(World.OVERWORLD),
                gameprofile,
                new PlayerInteractManager(minecraftserver.getWorldServer(World.OVERWORLD)));

        final Player target = entity.getBukkitEntity();
        if (target != null)
            target.loadData();
        return target;
    }
}

