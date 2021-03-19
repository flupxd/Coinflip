package github.flupxd.coinflip.ui;

import github.flupxd.api.mfgui.gui.guis.Gui;
import github.flupxd.api.mfgui.gui.guis.GuiItem;
import github.flupxd.coinflip.CoinFlip;
import github.flupxd.coinflip.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.Map;

public class CoinflipList {

    private final Player player;
    private Gui gui;
    private BukkitTask task;

    public CoinflipList(Player player){
        this.player = player;
        open();
        update();
    }

    public void open(){
        gui = new Gui(5, "Coinflip");
        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.setCloseGuiAction(event -> task.cancel());
        gui.getFiller().fillTop(new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 5).build()));
        gui.setItem(Arrays.asList(36, 37, 38, 39, 41, 42, 43, 44), new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1).build()));
        gui.setItem(40, new GuiItem(new ItemBuilder(Material.PAPER, 1)
                .setDisplayName("&a&lOPRET COINFLIP").build(), event -> {
            new CoinflipCreate(player);
        }));
        gui.open(player);
    }


    public void update(){
        task = new BukkitRunnable() {
            public void run() {
                for(int x=9;x<36;x++)gui.removeItem(x);
                int i = 9;
                for(Map.Entry<Player, Double> entry : CoinFlip.getInstance().getCoinflipManager().coinflips.entrySet()){
                    if(entry.getKey() != null) {
                        if(entry.getKey().getName().equalsIgnoreCase(player.getName())){
                            gui.setItem(i, new GuiItem(new ItemBuilder(getPlayerHead(entry.getKey()))
                                    .setDisplayName("&a&l$" + entry.getValue()).setLore("&7Lavet af&8: &fDig selv", "&7&oKlik for at fjerne").build(), event -> {
                                CoinFlip.getInstance().getCoinflipManager().deleteBet(player);
                            }));
                        }else {
                            gui.setItem(i, new GuiItem(new ItemBuilder(getPlayerHead(entry.getKey()))
                                    .setDisplayName("&a&l$" + entry.getValue()).setLore("&7Lavet af&8: &f" + entry.getKey().getName()).build(), event -> {
                                CoinFlip.getInstance().getCoinflipManager().takeBet(player.getName(), entry.getKey().getName(), entry.getValue());
                            }));
                        }
                        i++;
                    }
                }
                gui.update();
            }
        }.runTaskTimer(CoinFlip.getInstance(), 0, 20);

    }

    private ItemStack getPlayerHead(Player player) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
        item.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(player.getName());
        item.setItemMeta(meta);
        return item;
    }
}
