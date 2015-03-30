package src.ares.core.command.type.punishment;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.client.storage.ClientSection;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;
import src.ares.core.currency.CurrencyType;
import src.ares.core.server.ServerDatabase;

public class CommandLookup extends CoreCommand
{
	public CommandLookup()
	{
		super("lookup", new String[]
		{
			"search"
		}, 1, Rank.TRIAL_MOD, "<player>");
	}

	public void execute()
	{
		@SuppressWarnings("deprecation")
		OfflinePlayer player = Bukkit.getOfflinePlayer(getArgs()[0]);

		if (player == null)
		{
			getClient().sendMessage(getModuleName(), Chat.player(getArgs()[0]) + " could not be found.");
			return;
		}

		OfflineClient client = new OfflineClient(player.getName());
		ConfigurationSection history = client.getManager().getConfig().getConfigurationSection(ClientSection.HISTORY.toString());

		getClient().sendRaw(Chat.seperator());

		getClient().sendRaw("Lookup results: " + Chat.player(client.getName()));
		getClient().sendRaw("&eRank: &f" + client.getManager().getRank().getName() + "   " + "&e" + CurrencyType.GOLD.toString() + ": &f" + client.getManager().getCurrency(CurrencyType.GOLD) + "   " + "&e" + CurrencyType.AMBROSIA.toString() + ": &f" + client.getManager().getCurrency(CurrencyType.AMBROSIA));
		long hours = ServerDatabase.getInstance().getPlayerTime(player.getUniqueId());
		getClient().sendRaw("&aOnline for " + (((hours / 1000) / 60) / 60) + " hours");
		getClient().sendRaw("");

		if (client.getManager().getPunishmentHistory().isEmpty())
		{
			getClient().sendRaw("&7No additional data to display.");
			return;
		}

		for (String string : client.getManager().getPunishmentHistory())
		{
			ConfigurationSection specific = history.getConfigurationSection(string);
			getClient().sendRaw(ChatColor.BLUE + UtilString.format(string) + ":");

			for (String info : specific.getKeys(false))
			{
				getClient().sendRaw(ChatColor.GRAY + "  " + UtilString.format(info) + ": " + ChatColor.WHITE + specific.getString(info));
			}
		}

		getClient().sendRaw(Chat.seperator());
	}
}
