package de.laurens.marioparty.listeners;

import de.laurens.marioparty.MarioParty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (player.getItemInHand() == null) return;
        if (!player.getItemInHand().hasItemMeta()) return;

        if (player.getItemInHand().getItemMeta().getDisplayName().equals(MarioParty.testLang.getContent("skinitem"))) {
            event.setCancelled(true);
            Inventory inv = null;
            inv = MarioParty.instance.skinManager.getSkinMenu(inv);
            player.openInventory(inv);
        }
        if (player.getItemInHand().getItemMeta().getDisplayName().equals(MarioParty.testLang.getContent("voteitem"))) {
            event.setCancelled(true);
            Inventory inv = null;
            inv = MarioParty.instance.voteManager.getVoteInventory(inv);
            player.openInventory(inv);
        }
    }
}
