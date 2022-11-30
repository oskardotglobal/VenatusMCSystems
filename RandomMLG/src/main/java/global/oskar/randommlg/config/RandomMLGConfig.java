package global.oskar.randommlg.config;

import de.venatus247.vutils.Logger;
import de.venatus247.vutils.utils.file.YmlConfigFile;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class RandomMLGConfig extends YmlConfigFile {

    private Location netherEnteredLocation;

    public RandomMLGConfig() throws IOException {
        super(new File("plugins/RandomMLG/config.yml"));
    }

    @Override
    protected void load() {
        super.load();
        netherEnteredLocation = getLocation("netherEnteredLocation");
    }

    @Override
    public boolean loadDefaults(boolean writeToFile) {
        Logger.getInstance().log("Loading default config");
        //config.addDefault("border.currentSize", 1);
        if (writeToFile) {
            config.options().copyDefaults(true);
            write();
        }

        return true;
    }

    @Override
    public boolean save() {
        set("netherEnteredLocation", netherEnteredLocation);
        return super.save();
    }

    public Location getNetherEnteredLocation() { return netherEnteredLocation; }
    public Location setNetherEnteredLocation(Location loc) { return netherEnteredLocation = loc; }
}
