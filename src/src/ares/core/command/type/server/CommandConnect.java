package src.ares.core.command.type.server;

import org.bukkit.entity.Player;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.server.BungeeCord;

public class CommandConnect extends CoreCommand
{
	public CommandConnect()
	{
		super("connect", new String[] {}, 1, Rank.BUILDER);
	}

	@Override
	public void execute()
	{
		Player player = getClient().getPlayer();
		String server = getArgs()[0];

		if (player instanceof Player)
		{
			BungeeCord.send(player, server);
		}
	}
}
