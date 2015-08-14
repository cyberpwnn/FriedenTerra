package com.ulticraft.friedenterra;

import net.md_5.bungee.api.ChatColor;

public class Info
{
	public static final String VERSION = "1.0";
	public static final String CURRENCY = "Chronoton";
	public static final String CURRENCYS = "Chronotons";
	
	public static final String CMD_CHRONOTONS = "chronotons";
	
	public static final String PERM_CHRONOTONS = "friedenterra.chronotons";

	public static final String CHRONOTON_GOT_TITLE = ChatColor.GREEN + "You earned " + ChatColor.AQUA + "%s";
	public static final String MSG_CHRONO_GET = ChatColor.GREEN + "You have " + ChatColor.AQUA + "%s";
	public static final String MSG_CHRONO_GAVE = ChatColor.GREEN + "Gave " + ChatColor.AQUA + "%d " + CURRENCYS + ChatColor.GREEN + " to " + ChatColor.AQUA + "%s";
	public static final String MSG_PNF_CHRONO = ChatColor.RED + "Who is %s?";
	public static final String MSG_USAGE_CHRONO_GIVE = "/chrono give <player> <amt> <reason>";
	public static final String MSG_DENY_CHRONO = ChatColor.RED + "NO";
}
