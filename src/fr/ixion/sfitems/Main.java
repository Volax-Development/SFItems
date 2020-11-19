package fr.ixion.sfitems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.ixion.sfitems.command.SFItemsCommand;
import fr.ixion.sfitems.items.SFItem;
import fr.ixion.sfitems.listener.ArmorListener;
import fr.ixion.sfitems.listener.BlockListener;
import fr.ixion.sfitems.listener.CraftListener;
import fr.ixion.sfitems.listener.EntityListener;
import fr.ixion.sfitems.listener.InteractListener;
import fr.ixion.sfitems.listener.InventoryListener;
import fr.ixion.sfitems.util.Utils;
import net.milkbowl.vault.economy.Economy;

public class    Main extends JavaPlugin {

	private static final Logger log = Logger.getLogger("Minecraft");
	private static Main instance;

	private YamlConfiguration conf;

	public HashMap<Location, String> sellChest = new HashMap<Location, String>();
	public List<Location> farmHopper = new ArrayList<Location>();

	public static Economy getEcon() {
		return econ;
	}

	private static Economy econ = null;

	public static int tics;

    public void onEnable() {
		instance = this;
		saveDefaultConfig();
		conf = getyml(new File(getDataFolder(), "config.yml"));
		if (!setupEconomy()) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!",
					new Object[] { getDescription().getName() }));
			getServer().getPluginManager().disablePlugin((Plugin) this);
			return;
		}
		if (new File(getDataFolder(), "sellChest.yml").exists()) {
			sellChest = (Utils.restoreLocationsAndUUID(new File(getDataFolder(), "sellChest.yml")));
		}
		if (new File(getDataFolder(), "hopperFarm.yml").exists()) {
			farmHopper = (Utils.restoreLocations(new File(getDataFolder(), "hopperFarm.yml")));
		}
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		Bukkit.getPluginManager().registerEvents(new CraftListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new hkr.ArmorEquipEventFiles.ArmorListener(), this);
		Bukkit.getPluginManager().registerEvents(new ArmorListener(), this);
		getCommand("sfitems").setExecutor(new SFItemsCommand());
		SFItem.generate();
	}

	public void onDisable() {
		try {
			Utils.saveLocationsAndUUID(sellChest, new File(getDataFolder(), "sellChest.yml"));
			Utils.saveLocationList(farmHopper, new File(getDataFolder(), "hopperFarm.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public YamlConfiguration getyml(File file) {
		YamlConfiguration config = new YamlConfiguration();
		FileInputStream fileinputstream;

		try {
			fileinputstream = new FileInputStream(file);
			config.load(new InputStreamReader(fileinputstream, Charset.forName("UTF-8")));
		} catch (FileNotFoundException e) {
			System.out.print("File not found!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return config;
	}

	public YamlConfiguration getConfig() {
		return conf;
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null)
			return false;
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
			return false;
		econ = (Economy) rsp.getProvider();
		return (econ != null);
	}

    public static Main getInstance() {
        return instance;
    }
}
