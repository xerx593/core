package src.ares.core.command.type.general;

import org.bukkit.ChatColor;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;

public class CommandPing extends CoreCommand
{
	public CommandPing()
	{
		super("ping", new String[] {}, 0, Rank.PLAYER);
	}

	@Override
	public void execute()
	{
		String response = "OK";
		int ping = getClient().getEntityPlayer().ping;

		if (ping <= 1500) // 1.5 second
		{
			response = ChatColor.GREEN + "" + ChatColor.BOLD + "Good";
		}
		else if (ping >= 3000) // 3 seconds
		{
			response = ChatColor.GOLD + "" + ChatColor.BOLD + "Medium";
		}
		else if (ping >= 10000) // 10 seconds
		{
			response = ChatColor.RED + "" + ChatColor.BOLD + "Weak";
		}

		getClient().sendRaw(Chat.seperator());
		getClient().sendRaw("&9Ping: &f" + ping + " ms");
		getClient().sendRaw("&9Connection: &f" + response);
		getClient().sendRaw(Chat.seperator());
	}
}
