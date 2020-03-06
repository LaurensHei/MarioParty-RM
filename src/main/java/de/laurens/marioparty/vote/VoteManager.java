package de.laurens.marioparty.vote;

import de.laurens.marioparty.MarioParty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class VoteManager {

    public ArrayList<VoteMap> maps = new ArrayList<>();

    public VoteManager() {
        getRandomMaps();
        checkAvailability();
    }

    public void getRandomMaps() {
        //Choice one
        VoteMap c1 = new VoteMap("WarteLobby", "WarteLobby");
        c1.setItem(new ItemStack(Material.BEACON));
        c1.setSlot(2);
        maps.add(c1);


        //Choice two
        VoteMap c2 = new VoteMap("Zauberwald", "Zauberwald");
        c2.setItem(new ItemStack(Material.ENCHANTMENT_TABLE));
        c2.setSlot(4);
        maps.add(c2);

        //Choice three
        VoteMap c3 = new VoteMap("Teich", "Teich");
        c3.setItem(new ItemStack(Material.WATER_LILY));
        c3.setSlot(6);
        maps.add(c3);
    }

    public void checkAvailability() {
        for (VoteMap map : maps) {
            for (World world : Bukkit.getServer().getWorlds()) {
                if (world.getName().equals(map.getWorldName())) {
                    map.isAvailable = true;
                }
            }
        }
    }

    public Inventory getVoteInventory(Inventory inventory) {

        inventory = Bukkit.createInventory(null, 9, MarioParty.testLang.getContent("vote.inventory.title"));

        for (VoteMap map : maps) {
            ItemStack stack = map.getItem();
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(map.getDisplayName());
            meta.setLore(Arrays.asList(new String[]{map.isAvailable ? MarioParty.testLang.getContent("vote.available") : MarioParty.testLang.getContent("vote.notavailable")}));
            stack.setItemMeta(meta);
            inventory.setItem(map.getSlot(), stack);
        }

        return inventory;
    }

}
