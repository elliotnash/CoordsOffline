package cordsoffline.main;

import javafx.util.Pair;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getOfflinePlayers;

public class Coords implements TabExecutor {

    private static final List<String> BLANK = Collections.emptyList();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 2 ){
                List<String> playerNames = new LinkedList<>();
                for (OfflinePlayer player : getOfflinePlayers()){
                    playerNames.add(player.getName());
                }
                return playerNames;
            }
        }
        return BLANK;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==1){

            //gets uuid and offline player of target, including bedrock players
            Player playerSender = (Player) sender;

            UUID playerTargetUUID;
            OfflinePlayer playerTarget;

            Pair<Boolean, OfflinePlayer> offlinePair = getBedrockOfflinePlayer(args[0]);
            if (offlinePair.getKey()){
                playerTarget = offlinePair.getValue();
            } else {
                playerSender.sendMessage(ChatColor.RED+"This player has not played before!");
                return true;
            }

            // checks if online, if it is get online player and get coords - simple
            if (playerTarget.isOnline()){
                Location onlineLocation = playerTarget.getPlayer().getLocation();

                //sends coords to sender
                sender.spigot().sendMessage(createComponent(args[0], onlineLocation));

            } else {

            }

        }
        return false;
    }

    public static Pair<Boolean, OfflinePlayer> getBedrockOfflinePlayer(String playerName){
        OfflinePlayer[] offlinePLayers = getOfflinePlayers();
        for (OfflinePlayer player : offlinePLayers){
            if (player.getName().equals(playerName)){
                return new Pair<>(true, player);
            }
        }
        return new Pair<>(false, null);
    }

    public static TextComponent createComponent(String name, Location location){
        //Creates main text components
        TextComponent mainComponent = new TextComponent(name+" is in world "+location.getWorld().getName()+ " with location ");
        mainComponent.setColor( ChatColor.AQUA.asBungee() );

        TextComponent locationComponent = new TextComponent(location.getX()+", "+location.getY()+", "+location.getZ());
        // Add a click event to the component.
        locationComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + name));
        locationComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click to teleport!" ).color(ChatColor.GOLD.asBungee()).create() ) );
        locationComponent.setColor(ChatColor.LIGHT_PURPLE.asBungee());
        //adds location component to main
        mainComponent.addExtra(locationComponent);
        return mainComponent;
    }
}
