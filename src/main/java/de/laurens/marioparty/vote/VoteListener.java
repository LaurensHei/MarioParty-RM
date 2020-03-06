package de.laurens.marioparty.vote;

import de.laurens.marioparty.MarioParty;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class VoteListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (!event.getClickedInventory().getName().equals(MarioParty.testLang.getContent("vote.inventory.title")))
            return;
        event.setCancelled(true);
        player.sendMessage("§cDas Votesystem ist noch in Arbeit.");
    }
}
