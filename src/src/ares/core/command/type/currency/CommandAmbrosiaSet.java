package src.ares.core.command.type.currency;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.currency.type.AmbrosiaCurrency;
import src.ares.core.server.ServerDatabase;

public class CommandAmbrosiaSet extends CoreCommand
{
	public CommandAmbrosiaSet()
	{
		super("setambrosia", new String[] {}, 2, Rank.ADMIN, "<player> <amount>");
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
			target.getManager().updateCurrency(new AmbrosiaCurrency(amount), true);
		}
		catch (NumberFormatException invalid)
		{
			getClient().sendMessage(getModuleName(), Chat.gold(getArgs()[1]) + " is not a number.");
			return;
		}

		target.unload();
		ServerDatabase.getInstance().update(player);
	}
}
