package com.ulticraft.friedenterra;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import com.ulticraft.core.Title;

public class Chronotons
{
	private PlayerConfig config;
	
	public Chronotons(FriedenTerra ft)
	{
		this.config = ft.getPlayerConfig();
	}
	
	public int getChronotons(Player player)
	{
		return config.pgetChronotons(player);
	}
	
	public void setChronotons(Player player, int chronotons)
	{
		if(chronotons > 0)
		{
			config.psetChronotons(player, chronotons);
		}
		
		else
		{
			
		}
	}
	
	public boolean canSpend(Player player, int chronotons)
	{
		return getChronotons(player) >= chronotons;
	}
	
	public void addChronotons(Player player, int chronotons, String reason)
	{
		setChronotons(player, getChronotons(player) + Math.abs(chronotons));
		
		String given = chronotons + " ";
		if(chronotons == 1)
		{
			given = given + Info.CURRENCY;
		}
		
		else
		{
			given = given + Info.CURRENCYS;
		}
		
		Title title = new Title(String.format(Info.CHRONOTON_GOT_TITLE, given), reason, 5, 30, 50);
		title.send(player);
		
		player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 2.4f, 0.5f);
		player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 3.0f, 3.0f);
		player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 3.0f, 4.0f);
	}
	
	public void takeChronotons(Player player, int chronotons, String reason)
	{
		if(canSpend(player, chronotons))
		{
			setChronotons(player, getChronotons(player) - Math.abs(chronotons));
		}
	}
}
