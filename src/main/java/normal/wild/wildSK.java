package normal.wild;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import normal.wild.commands.boast;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class wildSK extends JavaPlugin {

    public static wildSK instance;
    SkriptAddon addon;
    public static wildSK getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;
        getServer().getPluginCommand("boast").setExecutor(new boast());
        addon = Skript.registerAddon(this);
        try {
            //This will register all our syntax for us. Explained below
            addon.loadClasses("normal.wild", "elements");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
