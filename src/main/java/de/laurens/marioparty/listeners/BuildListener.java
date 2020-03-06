package de.laurens.marioparty.listeners;

import de.laurens.marioparty.GameState;
import de.laurens.marioparty.MarioParty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (MarioParty.gameState != GameState.MINIGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (MarioParty.gameState != GameState.MINIGAME) {
            event.setCancelled(true);
        }
    }

}
