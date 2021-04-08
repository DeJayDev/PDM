package dev.dejay.privatedeath;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public ArrayList<String> pdmtoggle = new ArrayList<>();

    public File killLogging;
    public FileConfiguration logger;

    public Logger log;

    public boolean toggled = true;

    @Override
    public void onEnable() {
        getCommand("pdm").setExecutor(new CmdExecutor(this));
        log = getLogger();
        log.info("Enabled!");

		getServer().getPluginManager().registerEvents(new Fights(this), this);
		getServer().getPluginManager().registerEvents(new ListenersClass(this), this);

        loadConfiguration();
        killLogging = new File(getDataFolder(), "killLogging.yml");
        logger = YamlConfiguration.loadConfiguration(killLogging);
    }

    @Override
    public void onDisable() {
        saveLog();
        log.info("Disabled!");
    }

    public void loadConfiguration() {
        final FileConfiguration config = this.getConfig();
        config.options().header(
            "PrivateDeathMessage configuration file! \nFor more info go to 'http://dev.bukkit.org/bukkit-plugins/privatedeath/pages/config/'");
        config.addDefault("Prefix.text", "[PrivateDeath]");
        config.addDefault("Prefix.colour", "&4");
        config.addDefault("Colour.killed", "&a");
        config.addDefault("Colour.killer", "&c");
        config.addDefault("Colour.by", "&b");
        config.options().copyDefaults(true);
        saveConfig();
    }

    public void saveLog() {
        try {
            logger.save(killLogging);
        } catch (IOException e) {
            log.severe("Error while saving kill log!");
        }
    }

}