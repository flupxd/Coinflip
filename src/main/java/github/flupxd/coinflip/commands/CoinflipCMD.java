package github.flupxd.coinflip.commands;

import github.flupxd.api.mfgui.gui.guis.BaseGui;
import github.flupxd.coinflip.ui.CoinflipList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinflipCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Denne kommando kan kun bruges af spillere.");
        } else{
            if(!(((Player) sender).getInventory().getHolder() instanceof BaseGui))
                new CoinflipList((Player) sender);
        }
        return true;
    }
}
