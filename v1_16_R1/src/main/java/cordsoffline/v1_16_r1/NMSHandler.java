package cordsoffline.v1_16_r1;

import com.mojang.authlib.GameProfile;
import cordsoffline.nmsinterface.NmsManager;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.MinecraftServer;
import net.minecraft.server.v1_16_R1.PlayerInteractManager;
import net.minecraft.server.v1_16_R1.World;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class NMSHandler implements NmsManager {
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
