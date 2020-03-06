package de.laurens.marioparty.vote;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class VoteMap {

    public String worldName;
    public String displayName;
    public ItemStack item;
    public int slot;
    public boolean isAvailable;

    public VoteMap(String worldName, String displayName) {
        this.worldName = worldName;
        this.displayName = displayName;
        this.isAvailable = false;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
