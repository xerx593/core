package src.ares.core.command.type.server;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.UtilString;
import src.ares.core.server.data.ServerMode;
import src.ares.core.server.data.ServerStorage;

public class CommandSetMode extends CoreCommand
{
	private ServerStorage serverStorage = ServerStorage.getInstance();

	public CommandSetMode()
	{
		super("setmode", new String[] {}, 1, Rank.ADMIN);
	}

	@Override
	public void execute()
	{
		String mode = UtilString.enumerator(getArgs()[0]);

		try
		{
			serverStorage.setServerMode(ServerMode.valueOf(mode));

			for (Player player : Bukkit.getOnlinePlayers())
			{
				if (player.equals(getClient().getPlayer()))
					continue;

				Client client = new Client(player);
				client.kick("Server Mode Changed", "The server mode changed, please re-connect.");
				client.unload();
			}
		}
		catch (IllegalArgumentException e)
		{
			getClient().sendMessage(getModuleName(), "Available server modes:");
			getClient().sendRaw("");

			for (ServerMode modes : ServerMode.values())
			{
				getClient().sendRaw("  " + ChatColor.GOLD + modes.toString() + ": " + ChatColor.WHITE + modes.getDescription());
			}
		}
	}
}
