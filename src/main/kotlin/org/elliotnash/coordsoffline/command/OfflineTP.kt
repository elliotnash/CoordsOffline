package org.elliotnash.coordsoffline.command

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.elliotnash.coordsoffline.OfflinePlayerLoader
import java.util.*
import kotlin.math.min

interface TpArg

data class TpCoordArg(
    val coords: Array<Double?>
) : TpArg {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TpCoordArg
        return coords.contentEquals(other.coords)
    }
    override fun hashCode(): Int {
        return coords.contentHashCode()
    }
}

data class TpPlayerArg(
    val name: String
) : TpArg

class OfflineTP(private val offlinePlayerLoader: OfflinePlayerLoader) : TabExecutor {
    private fun parseCoord(coord: String): Double? {
        return try {
            if (coord == "~") {
                null
            } else {
                coord.toDouble()
            }
        } catch (_: NumberFormatException) {
            null
        }
    }

    private fun parseArgs(args: Array<String>): List<TpArg>? {
        val out: MutableList<TpArg> = ArrayList<TpArg>(2)
        var i = 0
        while (i < args.size) {
            if (out.size >= 2) {
                return null
            }
            var coord = parseCoord(args[i])
            if (coord == null) {
                if (!out.isEmpty()) {
                    if (out[0] is TpCoordArg) {
                        return null
                    }
                }
                out.add(TpPlayerArg(args[i]))
            } else {
                val coords = arrayOf<Double?>(coord, null, null)
                var c = 1
                val end = min((i + 2).toDouble(), args.size.toDouble()).toInt()
                while (i < end) {
                    coord = parseCoord(args[i])
                    if (coord == null) {
                        return null
                    }
                    coords[c++] = coord
                    ++i
                }
                out.add(TpCoordArg(coords))
            }
            ++i
        }
        return out
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String> {
        if (sender is Player) {
            val parg = parseArgs(args)
            if (parg != null) {
                if (parg.isEmpty()) {
                    return Bukkit.getOfflinePlayers().map{ it.name }.filterNotNull()
                } else {
                    val last = parg[parg.size - 1]
                    if (last is TpPlayerArg) {
                        return Bukkit.getOfflinePlayers().map{ it.name }.filterNotNull().filter{ it.startsWith(last.name ?: "")}
                    }
                }
            }
        }
        return emptyList()
    }

    override fun onCommand(sender: CommandSender, command: Command, alias: String, args: Array<String>): Boolean {
        val pargs = parseArgs(args)
        if (pargs == null) {
            return false
        }
        var source: Player? = null
        var target: Location? = null
        if (pargs.size == 1) {
            if (sender !is Player) {
                return false
            }
            source = sender
            val targetArg = pargs[0]
            if (targetArg is TpPlayerArg) {
                val targetPlayer = offlinePlayerLoader.loadPlayer(Bukkit.getOfflinePlayer(targetArg.name))
                if (targetPlayer == null) {
                    return false
                }
                target = targetPlayer.getLocation()
            } else if (targetArg is TpCoordArg) {
                for (c in targetArg.coords) {
                    if (c == null) {
                        return false
                    }
                }
                target = Location(
                    source.world,
                    (targetArg.coords[0] ?: source.location.x),
                    (targetArg.coords[1] ?: source.location.y),
                    (targetArg.coords[2] ?: source.location.z),
                    source.getLocation().yaw,
                    source.getLocation().pitch
                )
            }
        } else {
            source = offlinePlayerLoader.loadPlayer(Bukkit.getOfflinePlayer((pargs[0] as TpPlayerArg).name))
            if (source == null) {
                return false
            }
            val targetArg = pargs[1]
            if (targetArg is TpPlayerArg) {
                val targetPlayer = offlinePlayerLoader.loadPlayer(Bukkit.getOfflinePlayer(targetArg.name))
                if (targetPlayer == null) {
                    return false
                }
                target = targetPlayer.location
            } else if (targetArg is TpCoordArg) {
                for (c in targetArg.coords) {
                    if (c == null) {
                        return false
                    }
                }
                target = Location(
                    source.world,
                    (targetArg.coords[0] ?: source.location.x),
                    (targetArg.coords[1] ?: source.location.y),
                    (targetArg.coords[2] ?: source.location.z),
                    source.getLocation().yaw,
                    source.getLocation().pitch
                )
            }
        }
        if (target == null) {
            return false
        }

        source.teleport(target)
        return true
    }
}
