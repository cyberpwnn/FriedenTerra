package com.ulticraft.friedenterra;

import org.bukkit.command.CommandSender;

public abstract class RunnableCommand implements Runnable
{
	CommandSender mSender;
	String[] mArgs;

	public RunnableCommand()
	{
		
	}

	public void run(CommandSender sender, String[] args)
	{
		setArgs(args);
		setSender(sender);
		run();
	}

	public void setArgs(String[] args)
	{
		mArgs = args;
	}
	
	public void setSender(CommandSender sender)
	{
		mSender = sender;
	}
	
	public CommandSender getSender()
	{
		return mSender;
	}

	public int getArgCount()
	{
		return mArgs == null ? 0 : mArgs.length;
	}

	public String[] getArgs()
	{
		return mArgs;
	}
}
