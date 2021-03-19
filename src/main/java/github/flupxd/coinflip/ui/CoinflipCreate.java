package github.flupxd.coinflip.ui;

import github.flupxd.api.mfgui.gui.guis.Gui;
import github.flupxd.api.mfgui.gui.guis.GuiItem;
import github.flupxd.coinflip.CoinFlip;
import github.flupxd.coinflip.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class CoinflipCreate {

    public CoinflipCreate(Player player){
        Gui gui = new Gui(5, "Coinflip - Opret");
        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.getFiller().fillTop(new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).build()));
        gui.getFiller().fillBottom(new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1).build()));


        LinkedHashMap<Material, Double> bets = new LinkedHashMap<Material, Double>()
        {{
            put(Material.IRON_INGOT, 100d);
            put(Material.GOLD_INGOT, 500d);
            put(Material.DIAMOND, 1000d);
            put(Material.EMERALD, 2500d);
            put(Material.NETHER_STAR, 5000d);
        }};
        int i = 20;
        for(Map.Entry<Material, Double> entry : bets.entrySet()) {
            gui.setItem(i, new GuiItem(new ItemBuilder(entry.getKey(), 1).setDisplayName("&a&l$" + entry.getValue()).build(), event -> {
                if(CoinFlip.getInstance().getCoinflipManager().hasBet((Player) event.getWhoClicked())) {
                    event.getWhoClicked().sendMessage(colorize("&c&lCOINFLIP &8| &7Du har allerede et aktivt coinflip."));
                    event.getWhoClicked().closeInventory();
                } else if (!CoinFlip.getInstance().getCoinflipManager().canAffordBet((Player) event.getWhoClicked(), entry.getValue())){
                    event.getWhoClicked().sendMessage(colorize("&c&lCOINFLIP &8| &7Du har ikke råd til dette."));
                    event.getWhoClicked().closeInventory();
                } else {
                    new CoinflipList((Player) event.getWhoClicked());
                    CoinFlip.getInstance().getCoinflipManager().createBet((Player) event.getWhoClicked(), entry.getValue());
                }
            }));
            i++;
        }
        gui.open(player);
    }


    private String colorize(String paramString) {
        return ChatColor.translateAlternateColorCodes('&', paramString);
    }
}