package github.flupxd.coinflip;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;

public class ItemBuilder {
    private final ItemStack item;

    public ItemBuilder(ItemStack paramItemStack) {
        this.item = paramItemStack.clone();
    }

    public ItemBuilder(Material paramMaterial, int paramInt, short paramShort) {
        this.item = new ItemStack(paramMaterial);
        this.item.setAmount(paramInt);
        this.item.setDurability(paramShort);
    }
    public ItemBuilder(Material paramMaterial, int paramInt) {
        this.item = new ItemStack(paramMaterial);
        this.item.setAmount(paramInt);
    }


    public ItemStack build() {
        return this.item;
    }

    public ItemBuilder setAmount(int paramInt) {
        this.item.setAmount(paramInt);
        return this;
    }

    public ItemBuilder setDurability(short paramShort) {
        this.item.setDurability(paramShort);
        return this;
    }

    public ItemBuilder setDisplayName(String paramString) {
        ItemMeta itemMeta = this.item.getItemMeta();
        itemMeta.setDisplayName(colorize(paramString));
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder replaceDisplayName(String paramString1, String paramString2) {
        ItemMeta itemMeta = this.item.getItemMeta();
        if (itemMeta.hasDisplayName()) {
            itemMeta.setDisplayName(itemMeta.getDisplayName().replace(paramString1, colorize(paramString2)));
            this.item.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder setLore(Collection<String> paramCollection) {
        ItemMeta itemMeta = this.item.getItemMeta();
        ArrayList<String> arrayList = new ArrayList();
        for (String str : paramCollection)
            arrayList.add(colorize(str));
        itemMeta.setLore(arrayList);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(String... paramVarArgs) {
        ItemMeta itemMeta = this.item.getItemMeta();
        ArrayList<String> arrayList = new ArrayList();
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = paramVarArgs).length, b = 0; b < i; ) {
            String str = arrayOfString[b];
            arrayList.add(colorize(str));
            b++;
        }
        itemMeta.setLore(arrayList);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    private String colorize(String paramString) {
        return ChatColor.translateAlternateColorCodes('&', paramString);
    }
}
