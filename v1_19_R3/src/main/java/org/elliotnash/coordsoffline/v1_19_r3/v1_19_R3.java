package org.elliotnash.coordsoffline.v1_19_r3;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.elliotnash.coordsoffline.nmsinterface.NmsManager;
public class v1_19_R3 implements NmsManager {
    public Player loadOfflinePlayer(OfflinePlayer player) {
        MinecraftServer minecraftserver = MinecraftServer.getServer();
        GameProfile gameprofile = new GameProfile(player.getUniqueId(), player.getName());
        ServerPlayer entity = new ServerPlayer(
                minecraftserver,
                minecraftserver.getLevel(Level.OVERWORLD),
                gameprofile
        );

        final Player target = entity.getBukkitEntity();
        if (target != null)
            target.loadData();
        return target;
    }
}
