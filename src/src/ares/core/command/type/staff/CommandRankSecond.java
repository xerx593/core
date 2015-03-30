package src.ares.core.command.type.staff;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import src.ares.core.Main;
import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;
import src.ares.core.server.ServerDatabase;

public class CommandRankSecond extends CoreCommand
{
	public CommandRankSecond()
	{
		super("secondrank", new String[] {}, 2, Rank.ADMIN, "<player> <rank>");
	}

	@Override
	protected void prepare()
	{
		if (!isSenderPlayer())
		{
			@SuppressWarnings("deprecation")
			OfflinePlayer player = Bukkit.getOfflinePlayer(getArgs()[0]);
			OfflineClient target = new OfflineClient(player.getName());
			String rank = UtilString.enumerator(getArgs()[1]);

			try
			{
				Rank updateRank = Rank.valueOf(rank);
				target.getManager().setSecondRank(updateRank);
			}
			catch (IllegalArgumentException invalid)
			{
				Main.error(getClass().getSimpleName(), "Second rank could not be converted: " + rank + ".");
			}
			
			target.unload();
			ServerDatabase.getInstance().update(player);
		}
	}

	@Override
	public void execute()
	{
		@SuppressWarnings("deprecation")
		OfflinePlayer player = Bukkit.getOfflinePlayer(getArgs()[0]);
		OfflineClient target = new OfflineClient(player.getName());
		String rank = UtilString.enumerator(getArgs()[1]);

		if (target.getManager().getRank() == Rank.OWNER && !getClient().getManager().getRank().equals(Rank.OWNER))
		{
			getClient().sendMessage(getModuleName(), "You cannot update an owner's rank.");
			return;
		}

		try
		{
			Rank updateRank = Rank.valueOf(rank);
			target.getManager().setSecondRank(updateRank);
		}
		catch (IllegalArgumentException invalid)
		{
			getClient().sendMessage(getModuleName(), Chat.rank(rank) + " is not a valid rank.");
			return;
		}
		
		target.unload();
		ServerDatabase.getInstance().update(player);
	}
}
