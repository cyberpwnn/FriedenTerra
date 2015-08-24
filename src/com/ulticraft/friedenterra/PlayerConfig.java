package com.ulticraft.friedenterra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import com.ulticraft.core.ListUtil;

public class PlayerConfig implements Listener
{
	public static final String PLUGIN_SCHEMA = "plugin.schema";
	public static final String IDENTITY_UUID = "identity.uuid";
	public static final String IDENTITY_NAME = "identity.name";
	public static final String ECONOMY_CHRONOTONS = "economy.chronotons";
	public static final String ECONOMY_CHRONOTONS_KNOWN = "economy.chronotons-known";

	private FriedenTerra plugin;
	private HashMap<Player, FileConfiguration> cache;

	public PlayerConfig(FriedenTerra plugin)
	{
		this.plugin = plugin;
		cache = new HashMap<Player, FileConfiguration>();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		File dataFolder = plugin.getDataFolder();
		if(!dataFolder.exists())
		{
			plugin.v("Creating /Ulticraft/");
			dataFolder.mkdir();
		}

		File userfiles;
		File muserfiles;
		try
		{
			userfiles = new File(plugin.getDataFolder() + File.separator + "playerdata");
			if(!userfiles.exists())
			{
				plugin.v("Creating /Ulticraft/playerdata/");
				userfiles.mkdirs();
			}
			
			muserfiles = new File(plugin.getDataFolder() + File.separator + "resources");
			if(!muserfiles.exists())
			{
				plugin.v("Creating /Ulticraft/resources/");
				muserfiles.mkdirs();
			}
		}
		
		catch(SecurityException e)
		{
			userfiles = null;
		}

		if(userfiles == null)
		{

		}
	}
	
	public boolean shouldSave()
	{
		return cache.size() != 0;
	}

	private Collection<FileConfiguration> getCachedPlayerConfigurations()
	{
		return cache.values();
	}

	public ArrayList<FileConfiguration> getPlayerConfigurations()
	{
		File folder = new File(plugin.getDataFolder() + File.separator + "playerdata");
		File[] listOfFiles = folder.listFiles();
		ArrayList<FileConfiguration> fcs = new ArrayList<FileConfiguration>();

		for(int i = 0; i < listOfFiles.length; i++)
		{
			if(listOfFiles[i].isFile())
			{
				String uuid = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length() - 4);
				File pdata = new File(plugin.getDataFolder() + File.separator + "playerdata" + File.separator + uuid + ".yml");
				FileConfiguration fc = new YamlConfiguration();

				try
				{
					fc.load(pdata);
					fcs.add(fc);
				}

				catch(FileNotFoundException e)
				{
					e.printStackTrace();
				}

				catch(IOException e)
				{
					e.printStackTrace();
				}

				catch(InvalidConfigurationException e)
				{
					e.printStackTrace();
				}
			}
		}

		return fcs;
	}

	private boolean isCached(Player p)
	{
		for(FileConfiguration i : getCachedPlayerConfigurations())
		{
			if(cgetUUID(i).equals(p.getUniqueId()))
			{
				return true;
			}
		}

		return false;
	}

	public void setPlayerConfiguration(Player p, FileConfiguration fc)
	{
		if(isCached(p))
		{
			cache.put(p, fc);
		}

		else
		{
			cachePlayer(p);
			cache.put(p, fc);
		}
	}

	public void setPlayerConfiguration(String p, FileConfiguration fc)
	{
		saveConfigurationFile(fc);
	}

	public FileConfiguration getPlayerConfiguration(Player p)
	{
		if(isCached(p))
		{
			return cache.get(p);
		}

		else
		{
			for(FileConfiguration i : getPlayerConfigurations())
			{
				if(cgetUUID(i).equals(p.getUniqueId()))
				{
					return i;
				}
			}
		}

		return null;
	}

	public FileConfiguration getPlayerConfiguration(String p)
	{
		for(FileConfiguration i : getPlayerConfigurations())
		{
			if(cgetName(i).equals(p))
			{
				return i;
			}
		}

		return null;
	}

	public FileConfiguration getPlayerConfiguration(UUID p)
	{
		for(FileConfiguration i : getPlayerConfigurations())
		{
			if(cgetUUID(i).equals(p))
			{
				return i;
			}
		}

		return null;
	}

	private FileConfiguration createConfiguration(Player p)
	{
		if(hasConfiguration(p))
		{
			return getPlayerConfiguration(p);
		}

		try
		{
			plugin.v("Creating new Configuration file for " + p.getUniqueId());
			FileConfiguration fc = new YamlConfiguration();
			File pdata = new File(plugin.getDataFolder() + File.separator + "playerdata" + File.separator + p.getUniqueId().toString() + ".yml");

			fc.set(PLUGIN_SCHEMA, Info.VERSION);
			fc.set(IDENTITY_UUID, p.getUniqueId().toString());
			fc.set(IDENTITY_NAME, p.getName());
			fc.set(ECONOMY_CHRONOTONS, 0);
			fc.set(ECONOMY_CHRONOTONS_KNOWN, 0);
			
			fc.save(pdata);

			return fc;
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public boolean hasConfiguration(Player p)
	{
		if(getPlayerConfiguration(p) == null)
		{
			return false;
		}

		return true;
	}

	public boolean hasConfiguration(String p)
	{
		if(getPlayerConfiguration(p) == null)
		{
			return false;
		}

		return true;
	}

	public void saveConfigurationFile(FileConfiguration fc)
	{
		try
		{
			File pdata = new File(plugin.getDataFolder() + File.separator + "playerdata" + File.separator + cgetUUID(fc).toString() + ".yml");
			fc.save(pdata);
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void set(Player p, String k, Object v)
	{
		if(!isCached(p))
		{
			cachePlayer(p);
		}

		cache.get(p).set(k, v);
	}

	private void set(String p, String k, Object v)
	{
		FileConfiguration fc = getPlayerConfiguration(p);
		fc.set(k, v);
		saveConfigurationFile(fc);
	}

	private void set(UUID p, String k, Object v)
	{
		FileConfiguration fc = getPlayerConfiguration(p);
		fc.set(k, v);
		saveConfigurationFile(fc);
	}

	private void cachePlayer(Player p)
	{
		if(!hasConfiguration(p))
		{
			cache.put(p, createConfiguration(p));
		}

		else
		{
			cache.put(p, getPlayerConfiguration(p));
			plugin.v("Caching " + p.getUniqueId().toString());
		}
	}

	private void saveCachedPlayer(Player p)
	{
		try
		{
			File pdata = new File(plugin.getDataFolder() + File.separator + "playerdata" + File.separator + p.getUniqueId().toString() + ".yml");
			cache.get(p).save(pdata);
			plugin.v("Saved " + pdata.getPath());
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void removeCachedPlayer(Player p)
	{
		cache.remove(p);
	}

	private void saveCache()
	{
		for(FileConfiguration i : getCachedPlayerConfigurations())
		{
			try
			{
				File pdata = new File(plugin.getDataFolder() + File.separator + "playerdata" + File.separator + cgetUUID(i).toString() + ".yml");
				i.save(pdata);
				plugin.v("Saved " + pdata.getPath());
			}

			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void clearCache()
	{
		cache = new HashMap<Player, FileConfiguration>();
	}

	private String cgetName(FileConfiguration fc)
	{
		return fc.getString(IDENTITY_NAME);
	}

	private UUID cgetUUID(FileConfiguration fc)
	{
		return UUID.fromString(fc.getString(IDENTITY_UUID));
	}

	public void saveAll()
	{
		saveCache();
		clearCache();
	}
	
	public ArrayList<String> pgetList(Player p, String k)
	{
		FileConfiguration fc = getPlayerConfiguration(p);
		return ListUtil.toArrayListString(fc.getList(k));
	}
	
	public void psetList(Player p, String k, ArrayList<String> v)
	{
		set(p, k, v);
	}
	
	public void paddList(Player p, String k, String v)
	{
		ArrayList<String> m = pgetList(p, k);
		m.add(v);
		psetList(p, k, m);
	}
	
	public void pdelList(Player p, String k, String v)
	{
		ArrayList<String> m = pgetList(p, k);
		m.remove(v);
		psetList(p, k, m);
	}
	
	public boolean isInList(Player p, String k, String s)
	{
		ArrayList<String> m = pgetList(p, k);
		return m.contains(s);
	}

	// Get Configuration Data
	public int pgetChronotons(Player p)
	{
		return getPlayerConfiguration(p).getInt(ECONOMY_CHRONOTONS);
	}
	
	public int pgetChronotonsKnown(Player p)
	{
		return getPlayerConfiguration(p).getInt(ECONOMY_CHRONOTONS_KNOWN);
	}
	
	public int pgetChronotons(String p)
	{
		return getPlayerConfiguration(p).getInt(ECONOMY_CHRONOTONS);
	}
	
	public int pgetChronotonsKnown(String p)
	{
		return getPlayerConfiguration(p).getInt(ECONOMY_CHRONOTONS_KNOWN);
	}
	
	public String pgetPluginSchema(Player p)
	{
		return getPlayerConfiguration(p).getString(PLUGIN_SCHEMA);
	}

	public String pgetName(Player p)
	{
		return getPlayerConfiguration(p).getString(IDENTITY_NAME);
	}

	public UUID pgetUUID(Player p)
	{
		return UUID.fromString(getPlayerConfiguration(p).getString(IDENTITY_UUID));
	}

	public String pgetPluginSchema(String p)
	{
		return getPlayerConfiguration(p).getString(PLUGIN_SCHEMA);
	}

	public String pgetName(String p)
	{
		return getPlayerConfiguration(p).getString(IDENTITY_NAME);
	}

	public UUID pgetUUID(String p)
	{
		return UUID.fromString(getPlayerConfiguration(p).getString(IDENTITY_UUID));
	}
	
	public String pgetPluginSchema(UUID p)
	{
		return getPlayerConfiguration(p).getString(PLUGIN_SCHEMA);
	}

	public String pgetName(UUID p)
	{
		return getPlayerConfiguration(p).getString(IDENTITY_NAME);
	}

	public UUID pgetUUID(UUID p)
	{
		return UUID.fromString(getPlayerConfiguration(p).getString(IDENTITY_UUID));
	}
	
	// Set Configuration Data
	public void psetChronotons(Player p, int v)
	{
		set(p, ECONOMY_CHRONOTONS, v);
	}
	
	public void psetChronotonsKnown(Player p, int v)
	{
		set(p, ECONOMY_CHRONOTONS_KNOWN, v);
	}
	
	public void psetChronotons(String p, int v)
	{
		set(p, ECONOMY_CHRONOTONS, v);
	}
	
	public void psetChronotonsKnown(String p, int v)
	{
		set(p, ECONOMY_CHRONOTONS_KNOWN, v);
	}
	
	public void psetPluginSchema(Player p, String v)
	{
		set(p, PLUGIN_SCHEMA, v);
	}

	public void psetName(Player p, String v)
	{
		set(p, IDENTITY_NAME, v);
	}

	public void psetUUID(Player p, String v)
	{
		set(p, IDENTITY_UUID, v);
	}
	
	public void psetPluginSchema(String p, String v)
	{
		set(p, PLUGIN_SCHEMA, v);
	}

	public void psetName(String p, String v)
	{
		set(p, IDENTITY_NAME, v);
	}

	public void psetUUID(String p, String v)
	{
		set(p, IDENTITY_UUID, v);
	}

	public void psetPluginSchema(UUID p, String v)
	{
		set(p, PLUGIN_SCHEMA, v);
	}

	public void psetName(UUID p, String v)
	{
		set(p, IDENTITY_NAME, v);
	}

	public void psetUUID(UUID p, String v)
	{
		set(p, IDENTITY_UUID, v);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		cachePlayer(e.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		saveCachedPlayer(e.getPlayer());
		removeCachedPlayer(e.getPlayer());
	}
}
