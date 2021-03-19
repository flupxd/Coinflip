package github.flupxd.coinflip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.LinkedHashMap;

public class CoinflipManager implements Listener {
    public LinkedHashMap<Player, Double> coinflips = new LinkedHashMap<>();

    public CoinflipManager(CoinFlip plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void createBet(Player player, double bet){
        coinflips.put(player, bet);
        CoinFlip.getInstance().getEconomy().withdrawPlayer(player, coinflips.get(player));
    }

    public void deleteBet(Player player){
        if(hasBet(player)){
            CoinFlip.getInstance().getEconomy().depositPlayer(player, coinflips.get(player));
            coinflips.remove(player);
        }
    }
    public boolean canAffordBet(Player player, double bet){
        return CoinFlip.getInstance().getEconomy().getBalance(player) >= bet;
    }

    public boolean hasBet(Player player){
        return coinflips.containsKey(player);
    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent event){
        deleteBet(event.getPlayer());
    }
    @EventHandler
    public void joinEvent(PlayerJoinEvent event){
        deleteBet(event.getPlayer());
    }


    public void takeBet(String takerS, String betterS, double bet){
        Player taker = Bukkit.getPlayer(takerS);
        if(taker == null) return;
        taker.closeInventory();
        if(!canAffordBet(taker, bet)) {
            taker.sendMessage(colorize("&c&lCOINFLIP &8| &7Du har ikke råd til dette."));
            return;
        }
        Player better = Bukkit.getPlayer(betterS);
        if(better == null) return;
        if(!coinflips.containsKey(better)) return;
        CoinFlip.getInstance().getEconomy().withdrawPlayer(taker, bet);

        Player winner = (Math.random() <= 0.5) ? taker : better;
        Player loser = taker == winner ? better : taker;
        winner.sendMessage(colorize("&a&lCOINFLIP &8| &7Du har vundet dit coinflip på &a$" + bet + " &7imod &a" + loser.getName()));
        loser.sendMessage(colorize("&c&lCOINFLIP &8| &7Du har tabt dit coinflip på &c$" + bet + " &7imod &c" + winner.getName()));
        CoinFlip.getInstance().getEconomy().depositPlayer(winner, bet * 2);
        coinflips.remove(better);
    }
    private String colorize(String paramString) {
        return ChatColor.translateAlternateColorCodes('&', paramString);
    }

}
