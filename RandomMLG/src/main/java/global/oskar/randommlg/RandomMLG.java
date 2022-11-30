package global.oskar.randommlg;

import global.oskar.randommlg.commands.RandomMLGCommand;
import global.oskar.randommlg.config.RandomMLGConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RandomMLG extends JavaPlugin {

    private boolean vUtilsLoaded = false;
    private static final String vUtilsPluginName = "VUtils";
    private static final String vUtilsRequiredVersion = "1.2.0-SNAPSHOT";
    private static final String vUtilsPluginUrl = "https://github.com/oskardotglobal/VenatusMCSystems/releases/";

    public static final String prefix = "§7[§6RandomMLG§7] ";

    private static RandomMLG instance;

    private RandomMLGConfig mlgConfig;

    public static RandomMLG getInstance() {
        return instance;
    }

    private final List<Listener> listeners = new ArrayList<>();

    @Override
    public void onLoad() {
        Plugin vUtilsPlugin = getServer().getPluginManager().getPlugin(vUtilsPluginName);

        if(vUtilsPlugin == null) {
            getServer().getConsoleSender().sendMessage(prefix + "§cCould not find plugin '" + vUtilsPluginName + "'!");
            getServer().getConsoleSender().sendMessage(prefix + "§cYou can download VUtils here: §7" + vUtilsPluginUrl);
            return;
        } else {
            if(!vUtilsPlugin.getDescription().getVersion().equals(vUtilsRequiredVersion)) {
                getServer().getConsoleSender().sendMessage(prefix + "§cIncompatible version of '" + vUtilsPluginName + "' found - you need " + vUtilsRequiredVersion + "!");
                getServer().getConsoleSender().sendMessage(prefix + "§cYou can download all versions of VUtils here: §7" + vUtilsPluginUrl);
                return;
            }
        }
        vUtilsLoaded = true;
    }

    @Override
    public void onEnable() {
        instance = this;

        if(!vUtilsLoaded) {
            disablePlugin();
            return;
        }

        try {
            mlgConfig = new RandomMLGConfig();
        } catch (IOException e) {
            e.printStackTrace();
            disablePlugin();
            return;
        }

        registerEvents(getServer().getPluginManager());
        registerCommands();
    }

    @Override
    public void onDisable() {
        if(mlgConfig != null)
            mlgConfig.save();
    }

    private void disablePlugin() {
        System.err.println("Disabling RandomMLG due to an error!");
        Bukkit.getPluginManager().disablePlugin(this);
    }

    private void registerCommands() {
        getCommand("randommlg").setExecutor(new RandomMLGCommand());
    }

    private void registerEvents(PluginManager pluginManager) {
        //listeners.add(new PlayerExpChangeListener());

        for(Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
            System.out.println("Registered " + listener.getClass().getSimpleName());
        }
    }

    public RandomMLGConfig getMLGConfig() {
        return mlgConfig;
    }
}
