package src.ares.core.command.type.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.UtilString;

public class CommandFake extends CoreCommand
{
	public CommandFake()
	{
		super("fake", new String[] {}, 1, Rank.ADMIN, "<message> (accepts color codes)");
	}

	@Override
	public void execute()
	{
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', UtilString.build(getArgs(), 0)));
	}
}
