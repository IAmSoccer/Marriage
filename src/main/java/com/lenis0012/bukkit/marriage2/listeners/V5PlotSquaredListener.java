package com.lenis0012.bukkit.marriage2.listeners;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.lenis0012.bukkit.marriage2.MData;
import com.lenis0012.bukkit.marriage2.events.PlayerDivorceEvent;
import com.lenis0012.bukkit.marriage2.events.PlayerMarryEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

public class V5PlotSquaredListener implements Listener {
    private final PlotAPI plotSquared;

    public V5PlotSquaredListener() {
        this.plotSquared = new PlotAPI();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMarry(PlayerMarryEvent event) {
        final UUID player = event.getRequesing().getUniqueId();
        final UUID partner = event.getRequested().getUniqueId();
        PlotPlayer plotPlayer = plotSquared.wrapPlayer(player);
        PlotPlayer plotPartner = plotSquared.wrapPlayer(partner);

        for(Plot plot : plotSquared.getPlayerPlots((Player) plotPlayer)) {
            if(plot.getTrusted().contains(partner)) {
                continue;
            }
            plot.addTrusted(partner);
        }

        for(Plot plot : plotSquared.getPlayerPlots((Player) plotPartner)) {
            if(plot.getTrusted().contains(player)) {
                continue;
            }
            plot.addTrusted(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDivorce(PlayerDivorceEvent event) {
        final MData marriage = event.getMarriage();
        final UUID player = marriage.getPlayer1Id();
        final UUID partner = marriage.getPllayer2Id();
        PlotPlayer plotPlayer = plotSquared.wrapPlayer(player);
        PlotPlayer plotPartner = plotSquared.wrapPlayer(partner);

        for(Plot plot : plotSquared.getPlayerPlots((Player) plotPlayer)) {
            if(!plot.getTrusted().contains(partner)) {
                continue;
            }
            plot.removeTrusted(partner);
        }

        for(Plot plot : plotSquared.getPlayerPlots((Player) plotPartner)) {
            if(!plot.getTrusted().contains(player)) {
                continue;
            }
            plot.removeTrusted(player);
        }
    }
}
