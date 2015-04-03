package src.ares.core.command.type.staff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.punish.type.KickPunishment;

public class CommandKickAll extends CoreCommand
{
	public CommandKickAll()
	{
		super("kickall", new String[] {}, 0, Rank.ADMIN, null);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void execute()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			KickPunishment kick = new KickPunishment("Kicked for updates, please re-connect.", new OfflineClient(player.getName()), getClient());
			kick.apply();
		}
	}
}
