package src.ares.core.command.type.staff;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;

public class CommandUUID extends CoreCommand
{
	public CommandUUID()
	{
		super("uuid", new String[] {}, 1, Rank.TRIAL_MOD, "<player>");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute()
	{
		OfflinePlayer player = Bukkit.getOfflinePlayer(getArgs()[0]);

		if (player == null)
		{
			getClient().sendMessage(getModuleName(), Chat.player(getArgs()[0]) + " could not be found.");
			return;
		}

		getClient().sendMessage(getModuleName(), Chat.player(getArgs()[0]) + " UUID is:");
		getClient().sendRaw(player.getUniqueId().toString());
	}
}
