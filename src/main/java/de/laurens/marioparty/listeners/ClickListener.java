package de.laurens.marioparty.listeners;

import de.laurens.marioparty.MarioParty;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (!event.getCurrentItem().getItemMeta().hasDisplayName()) return;
        if (event.getInventory().getName().equals("§eSkins")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                MarioParty.instance.skinManager.changeSkin((CraftPlayer) player, event.getCurrentItem().getItemMeta().getDisplayName().replace("§e§l", ""));
            }
        }
    }
}
