package src.ares.core.command.type.teleport;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;

public class CommandTeleportHere extends CoreCommand
{
	public CommandTeleportHere()
	{
		super("tphere", new String[] {}, 1, Rank.MOD, "<player>");
	}

	@Override
	public void execute()
	{
		Player player = Bukkit.getPlayer(getArgs()[0]);

		if (player == null)
		{
			getClient().sendMessage(getModuleName(), Chat.player(getArgs()[0]) + " is not online.");
			return;
		}

		if (player.equals(getClient().getPlayer()))
		{
			getClient().sendMessage(getModuleName(), "You cannot teleport youself.");
			return;
		}

		player.teleport(getClient().getPlayer().getLocation().add(0, 2, 0));
		getClient().sendMessage(getModuleName(), "You have teleported " + Chat.player(player.getName()) + " to your location.");
	}
}
