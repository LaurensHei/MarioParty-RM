package de.laurens.marioparty.listeners;

import de.laurens.marioparty.GameState;
import de.laurens.marioparty.MarioParty;
import de.laurens.marioparty.skin.GameProfileBuilder;
import de.laurens.marioparty.skin.SkinManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        MarioParty.ingame.add(player);

        resetPlayer(player);
        MarioParty.instance.giveLobbyItems(player);

        player.teleport(MarioParty.instance.lobbyLocation);

        Bukkit.getOnlinePlayers().forEach(players -> {
            players.sendMessage(MarioParty.testLang.getContent("joinmessage", player));
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        MarioParty.ingame.remove(player);

        Bukkit.getOnlinePlayers().forEach(players -> {
            players.sendMessage(MarioParty.testLang.getContent("quitmessage", player));
        });
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            if (MarioParty.gameState != GameState.MINIGAME) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            if (MarioParty.gameState != GameState.MINIGAME) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if (MarioParty.gameState != GameState.MINIGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (MarioParty.gameState != GameState.MINIGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (MarioParty.gameState != GameState.MINIGAME) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (MarioParty.gameState != GameState.MINIGAME) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getClickedBlock().getType().equals(Material.TRAP_DOOR) || event.getClickedBlock().getType().equals(Material.WOOD_DOOR)) {
                    event.setCancelled(true);
                }
            }
        }
    }


    private void resetPlayer(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealthScale(20);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setExp(0);
        player.setLevel(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.2F);
        player.getActivePotionEffects().clear();
        player.setPassenger(null);
    }


}
