package normal.wild.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import normal.wild.wildSK;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class boast implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String boast = ChatColor.translateAlternateColorCodes('&',  ""+wildSK.getInstance().getConfig().get("boast-message!"));
        if(sender instanceof Player) {
            Player p = (Player) sender;
            org.bukkit.inventory.ItemStack item = p.getInventory().getItemInMainHand();
            ItemStack nms = CraftItemStack.asNMSCopy(item);

            boast = boast.contains("{user}") ? boast.replace("{user}", p.getDisplayName()) : "§cERROR >> {user} is not fined";
            String a = boast.split("(\\{item})")[0];
            String b = boast.split("(\\{item})")[1];
            boast =
                    !boast.contains("{item}") ?
                            !boast.contains("{user}") ?
                                    "§cERROR >> {item}, {user} is not fined" : "§cERROR >> {item} is not fined" :
                            boast.replace("{item}", item.getItemMeta() != null ? !item.getItemMeta().getDisplayName().isEmpty() ? item.getItemMeta().getDisplayName() : item.getI18NDisplayName() : item.getI18NDisplayName());
            if (boast.contains("ERROR")) {
                p.sendMessage(boast);
                return false;
            }
            boast = boast.replace(a, "");
            boast = boast.replace(b, "");

            TextComponent component = new TextComponent(boast);

            NBTTagCompound compound = new NBTTagCompound();
            compound = nms.save(compound);
            String json = compound.toString();

            BaseComponent[] hover = new BaseComponent[]{new TextComponent(json)};

            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, hover));
            TextComponent comb = new TextComponent(b);
            comb.setHoverEvent(null);


            TextComponent result = new TextComponent(a);
            result.addExtra(component);
            result.addExtra(comb);
            Bukkit.getOnlinePlayers().forEach(s -> s.sendMessage(result));

        }
        return false;
    }
}
