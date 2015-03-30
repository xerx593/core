package src.ares.core.command.type.staff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.vote.Reward;

public class CommandReward extends CoreCommand
{
	public CommandReward()
	{
		super("reward", new String[] {}, 1, Rank.ADMIN);
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
			getClient().sendMessage(getModuleName(), "You cannot reward yourself.");
			return;
		}

		new Reward(new Client(player)).randomReward();
	}
}
