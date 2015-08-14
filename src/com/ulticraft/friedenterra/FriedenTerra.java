package com.ulticraft.friedenterra;

import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class FriedenTerra extends JavaPlugin
{
	private Logger logger;
	private Chronotons chronotons;
	private PlayerConfig playerConfig;
	
	private boolean verbose;
	
	@Override
	public void onEnable()
	{
		logger = getLogger();
		verbose = true;
		
		v("Verbose logging enabled");
		
		playerConfig = new PlayerConfig(this);
		chronotons = new Chronotons(this);
	}
	
	@Override
	public void onDisable()
	{
		if(playerConfig.shouldSave())
		{
			v("Saving ALL Playerdata!");
			playerConfig.saveAll();
		}
	}
	
	public void info(String msg)
	{
		logger.info(msg);
	}
	
	public void v(String msg)
	{
		if(verbose)
		{
			getServer().getConsoleSender().sendMessage(ChatColor.GRAY + msg);
		}
	}
	
	public void fatality(String msg)
	{
		getServer().getConsoleSender().sendMessage(ChatColor.RED + msg);
	}

	public Chronotons getChronotons()
	{
		return chronotons;
	}

	public PlayerConfig getPlayerConfig()
	{
		return playerConfig;
	}

	public boolean isVerbose()
	{
		return verbose;
	}
}
