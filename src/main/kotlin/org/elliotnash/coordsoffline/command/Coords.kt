package org.elliotnash.coordsoffline.command

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import org.elliotnash.coordsoffline.OfflinePlayerLoader

class Coords : TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String> {
        if (sender is Player) {
            if (args.size < 2) {
                val playerNames = mutableListOf<String>()
                for (player in Bukkit.getOfflinePlayers()) {
                    if (player.name != null) {
                        playerNames.add(player.name!!)
                    }
                }
                return StringUtil.copyPartialMatches<ArrayList<String>>(
                    args[0],
                    playerNames,
                    ArrayList<String>()
                )
            }
        }
        return emptyList()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.size == 1) {
            // gets uuid and offline player of target, including bedrock players

            val offlinePlayer = Bukkit.getOfflinePlayer(args[0])
            if (!offlinePlayer.hasPlayedBefore()) {
                sender.sendMessage(ChatColor.RED.toString() + "This player has not played before!")
                return true
            }

            // Loads player if offline and get coords
            val player = OfflinePlayerLoader.instance.loadPlayer(offlinePlayer)
            if (player != null) {
                val onlineLocation = player.getLocation()

                //sends coords to sender
                sender.spigot().sendMessage(createComponent(args[0], onlineLocation))
                return true
            } else {
                sender.sendMessage("Target is null")
            }
        }
        return false
    }

    companion object {
        fun createComponent(name: String?, location: Location): TextComponent {
            //Creates main text components
            val mainComponent =
                TextComponent(name + " is in world " + location.getWorld()!!.name + " with location ")
            mainComponent.color = ChatColor.AQUA.asBungee()

            val locationComponent =
                TextComponent(location.x.toString() + ", " + location.y + ", " + location.z)
            // Add a click event to the component.
            locationComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/otp $name")
            locationComponent.hoverEvent = HoverEvent(
                HoverEvent.Action.SHOW_TEXT, ComponentBuilder("Click to teleport!").color(
                    ChatColor.GOLD.asBungee()
                ).create()
            )
            locationComponent.color = ChatColor.LIGHT_PURPLE.asBungee()
            //adds location component to main
            mainComponent.addExtra(locationComponent)
            return mainComponent
        }
    }
}
