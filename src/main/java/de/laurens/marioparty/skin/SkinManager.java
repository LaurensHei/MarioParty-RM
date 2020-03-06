package de.laurens.marioparty.skin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.laurens.marioparty.MarioParty;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SkinManager {

    public void changeSkin(CraftPlayer cp, String name) {
        EntityPlayer entityplayer = ((CraftPlayer) cp).getHandle();
        GameProfile skinProfile = cp.getProfile();
        try {
            skinProfile = GameProfileBuilder.fetch(UUIDFetcher.getUUID(name));
        } catch (IOException e) {
            cp.sendMessage(MarioParty.testLang.getContent("skin.load.failed"));
            e.printStackTrace();
            return;
        }
        Collection<Property> properties = skinProfile.getProperties().get("textures");
        cp.getProfile().getProperties().removeAll("textures");
        cp.getProfile().getProperties().putAll("textures", properties);
        cp.sendMessage("§cSkin changed!");
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(cp.getEntityId());
        sendePacket(destroy);
        PacketPlayOutPlayerInfo tabremove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle());
        sendePacket(tabremove);

        new BukkitRunnable() {
            @Override
            public void run() {
                PacketPlayOutPlayerInfo tabadd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle());
                sendePacket(tabremove);

                PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(cp.getHandle());
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!all.getName().equals(cp.getName()))
                        ((CraftPlayer) all).getHandle().playerConnection.sendPacket(spawn);
                }
            }
        }.runTaskLater(MarioParty.instance, 4);
    }

    public void sendePacket(Packet packet) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public Inventory getSkinMenu(Inventory inv) {
        inv = Bukkit.createInventory(null, 9, "§eSkins");
        List<String> skins = Arrays.asList("Poishii", "rewinside", "Minimichecker", "HerrKabel");
        for (String skin : skins) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwner(skin);
            skullMeta.setDisplayName("§e§l" + skin);
            skull.setItemMeta(skullMeta);
            inv.addItem(skull);
        }
        return inv;
    }

}
