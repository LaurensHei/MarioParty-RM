package de.laurens.marioparty.jumpandrun;

import de.laurens.marioparty.MarioParty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class JumpListener implements Listener {

    public HashMap<Player, Long> inJump = new HashMap<>();
    Location jumpStart = MarioParty.instance.jumpStartLocation;
    Location jumpEnd = MarioParty.instance.jumpEndLocation;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getName() == jumpStart.getWorld().getName()) {
            if (!inJump.containsKey(player)) {
                if (player.getLocation().distance(jumpStart) <= 1) {
                    inJump.put(player, System.currentTimeMillis());
                    player.sendMessage(MarioParty.testLang.getContent("jump.startmessage"));
                    player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                    giveJumpItems(player);
                }
            } else {
                if (player.getLocation().distance(jumpEnd) <= 1) {
                    Float time = Float.valueOf(System.currentTimeMillis() - inJump.get(player));
                    time = time / 1000;
                    inJump.remove(player);
                    player.sendMessage(MarioParty.testLang.getContent("jump.endmessage", time));
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                    MarioParty.instance.giveLobbyItems(player);
                } else {

                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand() == null) return;
        if (!player.getItemInHand().hasItemMeta()) return;
        if (player.getItemInHand().getItemMeta().getDisplayName().equals(MarioParty.testLang.getContent("jumpitem.reset"))) {
            event.setCancelled(true);
            inJump.remove(player);
            player.teleport(MarioParty.instance.jumpStartLocation);
            inJump.put(player, System.currentTimeMillis());
            giveJumpItems(player);
            player.sendMessage(MarioParty.testLang.getContent("jump.restartmessage"));
        } else if (player.getItemInHand().getItemMeta().getDisplayName().equals(MarioParty.testLang.getContent("jumpitem.leave"))) {
            inJump.remove(player);
            MarioParty.instance.giveLobbyItems(player);
            //player.teleport(MarioParty.instance.lobbyLocation);
            player.sendMessage(MarioParty.testLang.getContent("jump.leavemessage"));
        }
    }

    public void giveJumpItems(Player player) {
        player.getInventory().clear();

        ItemStack reset = new ItemStack(Material.REDSTONE);
        ItemMeta resetmeta = reset.getItemMeta();
        resetmeta.setDisplayName(MarioParty.testLang.getContent("jumpitem.reset"));
        reset.setItemMeta(resetmeta);
        player.getInventory().setItem(1, reset);

        ItemStack leave = new ItemStack(Material.IRON_INGOT);
        ItemMeta leavemeta = leave.getItemMeta();
        leavemeta.setDisplayName(MarioParty.testLang.getContent("jumpitem.leave"));
        leave.setItemMeta(leavemeta);
        player.getInventory().setItem(8, leave);
    }

}
