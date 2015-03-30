package src.ares.core.command.type.general;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.condition.type.BuildCondition;

public class CommandBuildMode extends CoreCommand
{
	private BuildCondition buildmode = BuildCondition.getCondition();
	private Player player;
	private Client client;

	public CommandBuildMode()
	{
		super("buildmode", new String[] {}, 0, Rank.BUILDER);
		
		setDisabled(true);
	}

	@Override
	public void execute()
	{
		player = getClient().getPlayer();
		client = getClient();

		if (client.getManager().getRank() == Rank.BUILDER || client.getManager().getSecondRank() == Rank.BUILDER || client.compareWith(Rank.ADMIN))
		{
			if (buildmode.hasItem(player))
			{
				buildmode.removeItem(player);
				client.setGameMode(GameMode.SURVIVAL);
				client.sendMessage(buildmode.getType(), "You have exited buildmode.");
			}
			else
			{
				buildmode.addItem(player);
				client.setGameMode(GameMode.CREATIVE);
				client.sendMessage(buildmode.getType(), "You have entered buildmode.");
			}
		}
		else
		{
			client.sendMessage(getModuleName(), "You cannot toggle that mode.");
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();

		if (buildmode.hasItem(player))
			buildmode.removeItem(player);
	}
}
