package com.ulticraft.core;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.ulticraft.friedenterra.FriedenTerra;

public class PlayerUtils
{
	private FriedenTerra ft;
	
	public PlayerUtils(FriedenTerra ft)
	{
		this.ft = ft;
	}
	
	public Player findPlayer(String search)
	{
		for(Player i : ft.getServer().getOnlinePlayers())
		{
			if(i.getName().toLowerCase().contains(search.toLowerCase()))
			{
				return i;
			}
		}
		
		return null;
	}
	
	public boolean canFindPlayer(String search)
	{
		Player p = findPlayer(search);
		if(p == null)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isPlayer(CommandSender sender)
	{
		return sender instanceof Player;
	}
}
