package de.laurens.marioparty.commands;

import de.laurens.marioparty.MarioParty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetspawnCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;

        player.sendMessage("Your World: " + player.getWorld().getName() + " JumpWorld: " + MarioParty.instance.jumpStartLocation.getWorld());
        return false;
    }
}
