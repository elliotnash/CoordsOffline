package org.elliotnash.coordsoffline.spigot;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.*;

interface TpArg{}

record TpCoordArg(
        TpCoord[] coords
) implements TpArg {}

record TpPlayerArg(
        String name
) implements TpArg {}

record TpCoord(
        Double val
){}

public class OfflineTP implements TabExecutor {

    private static final List<String> BLANK = Collections.emptyList();

    private TpCoord parseCoord(String coord) {
        try
        {
            if (coord.equals("~")) {
                return new TpCoord(null);
            } else {
                return new TpCoord(Double.parseDouble(coord));
            }
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
    
    List<TpArg> parseArgs(String[] args) {
        List<TpArg> out = new ArrayList<>(2);
        for (int i = 0; i < args.length; ++i) {
            if (out.size() >= 2) {
                return null;
            }
            TpCoord coord = parseCoord(args[i]);
            if (coord == null) {
                if (!out.isEmpty()) {
                    if (out.get(0) instanceof TpCoordArg) {
                        return null;
                    }
                }
                out.add(new TpPlayerArg(args[i]));
            } else {
                TpCoord[] coords = new TpCoord[]{coord, null, null};
                int c = 1;
                for (int end = Math.min(i+2, args.length); i < end; ++i) {
                    coord = parseCoord(args[i]);
                    if (coord == null) {
                        return null;
                    }
                    coords[c++] = coord;
                }
                out.add(new TpCoordArg(coords));
            }
        }
        return out;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, @NonNull String[] args) {
        if (sender instanceof Player) {
            List<TpArg> parg = parseArgs(args);
            if (parg != null){
                if (parg.isEmpty()) {
                    return CoordsOffline.getAllowedPlayers().stream().map(OfflinePlayer::getName).toList();
                } else {
                    TpArg last = parg.get(parg.size()-1);
                    if (last instanceof TpPlayerArg tpp) {
                        return CoordsOffline.getAllowedPlayers().stream()
                                .map(OfflinePlayer::getName)
                                .filter(Objects::nonNull)
                                .filter(n -> n.startsWith(tpp.name()))
                                .toList();
                    }
                }
            }
        }
        return BLANK;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, @NonNull String[] args) {
        List<TpArg> pargs = parseArgs(args);
        if (pargs == null) {
            return false;
        }
        Player source = null;
        Location target = null;
        if (pargs.size() == 1) {
            if (!(sender instanceof Player)) {
                return false;
            }
            source = (Player) sender;
            TpArg targetArg = pargs.get(0);
            if (targetArg instanceof TpPlayerArg tpp) {
                Player targetPlayer = CoordsOffline.getPlayerForOfflinePlayer(CoordsOffline.getAllowedPlayer(tpp.name()));
                if (targetPlayer == null) {
                    return false;
                }
                target = targetPlayer.getLocation();
            } else if (targetArg instanceof TpCoordArg tpc) {
                for (TpCoord c : tpc.coords()) {
                    if (c == null) {
                        return false;
                    }
                }
                target = new Location(
                        source.getWorld(),
                        tpc.coords()[0].val() == null ? source.getLocation().getX() : tpc.coords()[0].val(),
                        tpc.coords()[1].val() == null ? source.getLocation().getY() : tpc.coords()[1].val(),
                        tpc.coords()[2].val() == null ? source.getLocation().getZ() : tpc.coords()[2].val(),
                        source.getLocation().getYaw(),
                        source.getLocation().getPitch()
                );
            }
        } else {
            source = CoordsOffline.getPlayerForOfflinePlayer(CoordsOffline.getAllowedPlayer(((TpPlayerArg) pargs.get(0)).name()));
            if (source == null) {
                return false;
            }
            TpArg targetArg = pargs.get(1);
            if (targetArg instanceof TpPlayerArg tpp) {
                Player targetPlayer = CoordsOffline.getPlayerForOfflinePlayer(CoordsOffline.getAllowedPlayer(tpp.name()));
                if (targetPlayer == null) {
                    return false;
                }
                target = targetPlayer.getLocation();
            } else if (targetArg instanceof TpCoordArg tpc) {
                for (TpCoord c : tpc.coords()) {
                    if (c == null) {
                        return false;
                    }
                }
                target = new Location(
                        source.getWorld(),
                        tpc.coords()[0].val() == null ? source.getLocation().getX() : tpc.coords()[0].val(),
                        tpc.coords()[1].val() == null ? source.getLocation().getY() : tpc.coords()[1].val(),
                        tpc.coords()[2].val() == null ? source.getLocation().getZ() : tpc.coords()[2].val(),
                        source.getLocation().getYaw(),
                        source.getLocation().getPitch()
                );
            }
        }
        if (target == null) {
            return false;
        }

        source.teleport(target);
        return true;
    }
}
