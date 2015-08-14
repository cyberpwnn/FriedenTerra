package com.ulticraft.friedenterra;

import org.bukkit.command.CommandSender;

public class CommandInstance
{
	private String name;
	private RunnableCommand execute;
	
	public CommandInstance(String name, RunnableCommand execute)
	{
		this.name = name;
		this.execute = execute;
	}
	
	public void fire(CommandSender sender, String[] arguments)
	{
		execute.run(sender, arguments);
	}

	public String getName()
	{
		return name;
	}
	
	public Runnable getExecute()
	{
		return execute;
	}
}
