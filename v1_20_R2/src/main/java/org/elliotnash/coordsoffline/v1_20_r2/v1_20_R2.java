package org.elliotnash.coordsoffline.v1_20_r2;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.elliotnash.coordsoffline.nmsinterface.NmsManager;

public class v1_20_R2 implements NmsManager {
    public Player loadOfflinePlayer(OfflinePlayer player) {
        MinecraftServer minecraftserver = MinecraftServer.getServer();
        GameProfile gameprofile = new GameProfile(player.getUniqueId(), player.getName());
        ServerPlayer entity = new ServerPlayer(
                minecraftserver,
                minecraftserver.getLevel(Level.OVERWORLD),
                gameprofile,
                ClientInformation.createDefault()
        );

        final Player target = entity.getBukkitEntity();
        if (target != null)
            target.loadData();
        return target;
    }
}
