package de.laurens.marioparty.commands;

import de.laurens.marioparty.MarioParty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetlobbyCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player.");
            return false;
        }
        Player player = (Player) commandSender;
        if (player.hasPermission("marioparty.settings.setlobby")) {
            File file = new File(MarioParty.instance.lobbyLocationFile.getPath());
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            cfg.set("Lobby.World", player.getWorld().getName());
            cfg.set("Lobby.X", player.getLocation().getX());
            cfg.set("Lobby.Y", player.getLocation().getY());
            cfg.set("Lobby.Z", player.getLocation().getZ());
            cfg.set("Lobby.Yaw", player.getLocation().getYaw());
            cfg.set("Lobby.Pitch", player.getLocation().getPitch());
            try {
                cfg.save(file);
                player.sendMessage(MarioParty.testLang.getContent("setlobbyspawnsuccess"));
            } catch (IOException ex) {
                ex.printStackTrace();
                player.sendMessage(MarioParty.testLang.getContent("savefailed"));
            }

        }

        return false;
    }
}
