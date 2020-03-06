package de.laurens.marioparty.jumpandrun;

import de.laurens.marioparty.MarioParty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class JnrCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player.");
            return false;
        }

        Player player = (Player) commandSender;
        if (player.hasPermission("marioparty.settings.jump")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("start")) {
                    File file = new File(MarioParty.instance.jumpFile.getPath());
                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    cfg.set("Jump.Start.World", player.getLocation().getWorld().getName());
                    cfg.set("Jump.Start.X", player.getLocation().getX());
                    cfg.set("Jump.Start.Y", player.getLocation().getY());
                    cfg.set("Jump.Start.Z", player.getLocation().getZ());
                    cfg.set("Jump.Start.Yaw", player.getLocation().getYaw());
                    cfg.set("Jump.Start.Pitch", player.getLocation().getPitch());
                    try {
                        cfg.save(file);
                        player.sendMessage(MarioParty.testLang.getContent("jump.start.savesuccess"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        player.sendMessage(MarioParty.testLang.getContent("savefailed"));
                    }
                } else if (args[0].equalsIgnoreCase("end")) {
                    File file = new File(MarioParty.instance.jumpFile.getPath());
                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    cfg.set("Jump.End.World", player.getLocation().getWorld().getName());
                    cfg.set("Jump.End.X", player.getLocation().getX());
                    cfg.set("Jump.End.Y", player.getLocation().getY());
                    cfg.set("Jump.End.Z", player.getLocation().getZ());
                    cfg.set("Jump.End.Yaw", player.getLocation().getYaw());
                    cfg.set("Jump.End.Pitch", player.getLocation().getPitch());
                    try {
                        cfg.save(file);
                        player.sendMessage(MarioParty.testLang.getContent("jump.end.savesuccess"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        player.sendMessage(MarioParty.testLang.getContent("savefailed"));
                    }
                } else {
                    player.sendMessage(MarioParty.testLang.getContent("jump.commandusage"));
                }
            }
        }


        return false;
    }
}
