package com.ulticraft.friedenterra;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import com.ulticraft.core.PlayerUtils;

public class FriedenTerra extends JavaPlugin
{
	private Logger logger;
	private Chronotons chronotons;
	private PlayerConfig playerConfig;
	private PlayerUtils playerUtils;
	private CommandBank commandBank;
	
	private boolean verbose;
	
	@Override
	public void onEnable()
	{
		logger = getLogger();
		verbose = true;
		
		v("Verbose logging enabled");
		
		playerConfig = new PlayerConfig(this);
		chronotons = new Chronotons(this);
		playerUtils = new PlayerUtils(this);
		commandBank = new CommandBank(this);
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return getCommandBank().fireCommand(sender, cmd.getName(), args);
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
	
	public PlayerUtils getPlayerUtils()
	{
		return playerUtils;
	}
	
	public CommandBank getCommandBank()
	{
		return commandBank;
	}

	public boolean isVerbose()
	{
		return verbose;
	}
}
