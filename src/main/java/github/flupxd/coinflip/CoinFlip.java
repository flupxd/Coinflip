package github.flupxd.coinflip;

import github.flupxd.coinflip.commands.CoinflipCMD;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CoinFlip extends JavaPlugin {

    private static CoinFlip instance;
    private Economy economy;
    private CoinflipManager coinflipManager;
    public void onEnable() {
        instance = this;
        getCommand("coinflip").setExecutor(new CoinflipCMD());
        economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        coinflipManager = new CoinflipManager(this);
    }


    public void onDisable() {

    }

    public static CoinFlip getInstance() {
        return instance;
    }
    public Economy getEconomy(){
        return economy;
    }
    public CoinflipManager getCoinflipManager(){
        return coinflipManager;
    }

}
