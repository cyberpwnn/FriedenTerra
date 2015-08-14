package com.ulticraft.friedenterra;

import java.util.ArrayList;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBank
{
	private CommandInstance commandChronotons;

	private ArrayList<CommandInstance> commands;

	public CommandBank(final FriedenTerra ft)
	{
		ft.v("Registering Commands");
		commandChronotons = new CommandInstance(Info.CMD_CHRONOTONS, new RunnableCommand()
		{
			@Override
			public void run()
			{
				if(getArgCount() == 0)
				{
					if(getSender() instanceof Player)
					{
						int ch = ft.getChronotons().getChronotons((Player) getSender());
						String ms = ch + " ";

						if(ch == 1)
						{
							ms = ms + Info.CURRENCY;
						}

						else
						{
							ms = ms + Info.CURRENCYS;
						}

						getSender().sendMessage(String.format(Info.MSG_CHRONO_GET, ms));
					}

					else
					{

					}
				}

				else
				{
					if(getArgs()[0].equalsIgnoreCase("give"))
					{
						if(getSender().hasPermission(Info.PERM_CHRONOTONS))
						{
							if(getArgCount() == 3)
							{
								if(ft.getPlayerUtils().canFindPlayer(getArgs()[1]))
								{
									Player p = ft.getPlayerUtils().findPlayer(getArgs()[1]);
									ft.getChronotons().addChronotons(p, Integer.parseInt(getArgs()[2]), "heh heh");
									getSender().sendMessage(String.format(Info.MSG_CHRONO_GAVE, Integer.parseInt(getArgs()[2]), p.getName()));
								}

								else
								{
									getSender().sendMessage(String.format(Info.MSG_PNF_CHRONO, getArgs()[1]));
								}
							}

							else if(getArgCount() > 3)
							{
								if(ft.getPlayerUtils().canFindPlayer(getArgs()[1]))
								{
									String reason = "";

									for(int i = 3; i < getArgCount(); i++)
									{
										reason = reason + " " + getArgs()[i];
									}

									reason = reason.substring(1);

									Player p = ft.getPlayerUtils().findPlayer(getArgs()[1]);
									ft.getChronotons().addChronotons(p, Integer.parseInt(getArgs()[2]), reason);
									getSender().sendMessage(String.format(Info.MSG_CHRONO_GAVE, Integer.parseInt(getArgs()[2]), p.getName()));
								}

								else
								{
									getSender().sendMessage(String.format(Info.MSG_PNF_CHRONO, getArgs()[1]));
								}
							}

							else
							{
								getSender().sendMessage(Info.MSG_USAGE_CHRONO_GIVE);
							}
						}

						else
						{
							getSender().sendMessage(Info.MSG_DENY_CHRONO);
						}
					}
				}
			}
		});

		commands = new ArrayList<CommandInstance>();
		commands.add(commandChronotons);

		for(CommandInstance i : commands)
		{
			ft.v("  > Command: " + i.getName());
		}
	}

	public boolean fireCommand(CommandSender sender, String name, String[] args)
	{
		for(CommandInstance i : commands)
		{
			if(i.getName().equalsIgnoreCase(name))
			{
				i.fire(sender, args);
				return true;
			}
		}

		return false;
	}
}
