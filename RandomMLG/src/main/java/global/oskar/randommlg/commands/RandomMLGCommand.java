package global.oskar.randommlg.commands;

import global.oskar.randommlg.RandomMLG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public class RandomMLGCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        scheduler.scheduleSyncDelayedTask(RandomMLG.getInstance(), () -> {
            // TODO: Stop timer
            // TODO: TP player up, check if they have an item they can mlg with, if not give them one, end challenge (stop timer) if they die
            // TODO: If player is in the nether / end, store coords in config, tp player to overworld, wait for mlg, and tp back
            // TODO: Resume timer

        }, 100 ); // TODO: random time

        return false;
    }
}
