package src.ares.core.command.type.staff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;

public class CommandSetGameMode extends CoreCommand
{

	public CommandSetGameMode()
	{
		super("setgamemode", new String[] {}, 2, Rank.ADMIN, "<player> <0 | 1 | 2>");
	}

	@Override
	public void execute()
	{

		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(getArgs()[0]);

		if (player == null) // If player is not online
		{
			getClient().sendMessage("Error", Chat.player(getArgs()[0]) + " is not online.");
			return;
		}
		Client target = new Client(player);
		Client sender = getClient();
		// Player sender1 = getClient().getPlayer(); // Get the bukkit version of player

		if (getArgs()[1].equals("1"))
		{
			if (target.getGameMode().equals(GameMode.CREATIVE))
			{
				sender.sendMessage("Error", target.getName() + " is already in creative!");
			}
			else
			{
				target.setGameMode(GameMode.CREATIVE);
				sender.sendMessage("Gamemode", target.getName() + "'s gamemode has been changed to Creative!");
			}
		}
		else if (getArgs()[1].equals("0"))
		{
			if (target.getGameMode().equals(GameMode.SURVIVAL))
			{
				sender.sendMessage("Error", target.getName() + " is already in Survival!");
			}
			else
			{
				target.setGameMode(GameMode.SURVIVAL);
				sender.sendMessage("Gamemode", target.getName() + "'s gamemode has been changed to Survival!");
			}
		}
		else
		{
			sender.sendMessage("Error", "That gamemode is invalid!");
		}
	}

}
