package org.elliotnash.coordsoffline.v1_15_r1;

import com.mojang.authlib.GameProfile;
import org.elliotnash.coordsoffline.nmsinterface.NmsManager;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class v1_15_R1 implements NmsManager {
    public Player loadOfflinePlayer(OfflinePlayer player) {
        MinecraftServer minecraftserver = MinecraftServer.getServer();
        GameProfile gameprofile = new GameProfile(player.getUniqueId(), player.getName());
        EntityPlayer entity = new EntityPlayer(minecraftserver,
                minecraftserver.getWorldServer(DimensionManager.OVERWORLD),
                gameprofile,
                new PlayerInteractManager(minecraftserver.getWorldServer(DimensionManager.OVERWORLD)));

        final Player target = entity.getBukkitEntity();
        if (target != null)
            target.loadData();
        return target;
    }
}
