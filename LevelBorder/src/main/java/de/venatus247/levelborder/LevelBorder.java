package de.venatus247.levelborder;

import de.venatus247.levelborder.commands.LevelBorderCommand;
import de.venatus247.levelborder.config.LevelBorderConfig;
import de.venatus247.levelborder.controllers.WorldBorderController;
import de.venatus247.levelborder.listeners.EnchantItemListener;
import de.venatus247.levelborder.listeners.PlayerExpChangeListener;
import de.venatus247.levelborder.listeners.PlayerJoinListener;
import de.venatus247.levelborder.listeners.PlayerChangedWorldListener;
import de.venatus247.vutils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelBorder extends JavaPlugin {

    private boolean vUtilsLoaded = false;
    private static final String vUtilsPluginName = "VUtils";
    private static final String vUtilsRequiredVersion = "1.2.0-SNAPSHOT";
    private static final String vUtilsPluginUrl = "https://github.com/oskardotglobal/VenatusMCSystems/releases/";

    public static final String prefix = "§7[§6LevelBorder§7] ";

    private static LevelBorder instance;

    private LevelBorderConfig borderConfig;

    public static LevelBorder getInstance() {
        return instance;
    }

    private final List<Listener> listeners = new ArrayList<>();

    WorldBorderController worldBorderController;

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
            borderConfig = new LevelBorderConfig();
        } catch (IOException e) {
            e.printStackTrace();
            disablePlugin();
            return;
        }

        worldBorderController = new WorldBorderController();

        registerEvents(getServer().getPluginManager());
        registerCommands();

        prepareWorlds();
        updateLevelAndExp(getLevel(), getExp());
    }

    @Override
    public void onDisable() {
        if(borderConfig != null)
            borderConfig.save();
    }

    private void disablePlugin() {
        System.err.println("Disabling LevelBorder due to an error!");
        Bukkit.getPluginManager().disablePlugin(this);
    }

    private void registerCommands() {
        getCommand("levelborder").setExecutor(new LevelBorderCommand());
    }

    private void registerEvents(PluginManager pluginManager) {
        listeners.add(new PlayerExpChangeListener());
        listeners.add(new EnchantItemListener());
        listeners.add(new PlayerJoinListener());
        listeners.add(new PlayerChangedWorldListener());

        for(Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
            System.out.println("Registered " + listener.getClass().getSimpleName());
        }
    }

    private void prepareWorlds() {
        for(World world : Bukkit.getWorlds()) {
            prepareWorld(world);
        }
    }

    private void prepareWorld(World world) {
        setWorldSpawnLocation(world, world.getSpawnLocation());
    }

    private void printWorldNotFoundErrorAndDisable(String worldName) {
        Logger.getInstance().log(prefix + "§cWorld '" + borderConfig.getOverWorldName() + "' could not be found");
        disablePlugin();
    }

    public void setWorldSpawnLocation(World world, Location location) {
        world.setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 1.0f);
        Logger.getInstance().log("Setting world spawn of world '" + world.getName() + "' to " + location.getBlockX() + " " + location.getBlockZ());
    }

    protected void updateBorder(int size) {
        borderConfig.setCurrentBorderSize(size);
        worldBorderController.updateSizeToAll(borderConfig.getCurrentBorderSize(), borderConfig.getBorderExpandTime());
    }

    public int getLevel() {
        return borderConfig.getLevel();
    }
    public float getExp() {
        return borderConfig.getExp();
    }

    public void updateLevelAndExp(int level, float exp) {
        for(Player players : Bukkit.getOnlinePlayers()) {
            players.setExp(exp);
            players.setLevel(level);
        }

        int newSize = switch (level) {
            case 0 -> 1;
            default -> borderConfig.getBorderLevelMultiplier() == 1 ? level*borderConfig.getBorderLevelMultiplier() + 1 : level*borderConfig.getBorderLevelMultiplier();
        };

        if(newSize != borderConfig.getCurrentBorderSize()) {
            updateBorder(newSize);
        }

        borderConfig.updateLevelAndExp(level, exp);
    }

    public void setPlayerBorder(Player player) {
        worldBorderController.setPlayerBorder(player, borderConfig.getCurrentBorderSize());
    }

    public WorldBorderController getWorldBorderController() {
        return worldBorderController;
    }

    public LevelBorderConfig getBorderConfig() {
        return borderConfig;
    }
}
