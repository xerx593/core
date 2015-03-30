package src.ares.core.command.type.currency;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.server.ServerDatabase;

public class CommandAmbrosiaRemove extends CoreCommand
{
	public CommandAmbrosiaRemove()
	{
		super("removeambrosia", new String[] {}, 2, Rank.ADMIN, "<player> <amount>");
	}

	@Override
	public void execute()
	{
		@SuppressWarnings("deprecation")
		OfflinePlayer player = Bukkit.getOfflinePlayer(getArgs()[0]);
		OfflineClient target = new OfflineClient(player.getName());

		try
		{
			int amount = Integer.parseInt(getArgs()[1]);
			target.removeCurrency(new AmbrosiaCurrency(amount));
		}
		catch (NumberFormatException e)
		{
			getClient().sendMessage(getModuleName(), Chat.gold(getArgs()[1]) + " is not a valid number!");
		}

		target.unload();
		ServerDatabase.getInstance().update(player);
	}
}
