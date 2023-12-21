package org.elliotnash.coordsoffline.spigot;

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
import org.bukkit.util.StringUtil;

import java.util.*;

import static org.bukkit.Bukkit.getOfflinePlayers;

public class Coords implements TabExecutor {

    private static final List<String> BLANK = Collections.emptyList();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 2 ){
                if (CoordsOffline.offlineSupport) {
                    List<String> playerNames = new LinkedList<>();
                    for (OfflinePlayer player : CoordsOffline.getAllowedPlayers()) {
                        playerNames.add(player.getName());
                    }
                    return StringUtil.copyPartialMatches(args[0], playerNames, new ArrayList<>());
                } else {
                    return BLANK;
                }
            }
        }
        return BLANK;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==1){

            //gets uuid and offline player of target, including bedrock players
            OfflinePlayer playerTarget = CoordsOffline.getAllowedPlayer(args[0]);
            if (playerTarget == null) {
                sender.sendMessage(ChatColor.RED+"This player has not played before!");
                return true;
            }

            // checks if online, if it is get online player and get coords - simple
            if (playerTarget.isOnline()){
                Location onlineLocation = playerTarget.getPlayer().getLocation();

                //sends coords to sender
                sender.spigot().sendMessage(createComponent(args[0], onlineLocation));
                return true;

            } else if (CoordsOffline.offlineSupport){
                Player player = CoordsOffline.nmsManager.loadOfflinePlayer(playerTarget);
                if (player!=null){
                    Location onlineLocation = player.getLocation();

                    //sends coords to sender
                    sender.spigot().sendMessage(createComponent(args[0], onlineLocation));
                    return true;
                }else{
                    sender.sendMessage("Target is null");
                }

            } else {
                sender.sendMessage("This version of paper doesn't support offline coordinates.");
                return true;
            }

        }
        return false;
    }

    public static TextComponent createComponent(String name, Location location){
        //Creates main text components
        TextComponent mainComponent = new TextComponent(name+" is in world "+location.getWorld().getName()+ " with location ");
        mainComponent.setColor( ChatColor.AQUA.asBungee() );

        String worldName = switch(location.getWorld().getName()) {
            case "world" -> "overworld";
            case "world_nether" -> "the_nether";
            case "world_the_end" -> "the_end";
            default -> location.getWorld().getName();
        };

        TextComponent locationComponent = new TextComponent(location.getX()+", "+location.getY()+", "+location.getZ());
        // Add a click event to the component.
        locationComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/otp " + name));
        locationComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click to teleport!" ).color(ChatColor.GOLD.asBungee()).create() ) );
        locationComponent.setColor(ChatColor.LIGHT_PURPLE.asBungee());
        //adds location component to main
        mainComponent.addExtra(locationComponent);
        return mainComponent;
    }
}
