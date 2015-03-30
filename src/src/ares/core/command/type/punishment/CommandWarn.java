package src.ares.core.command.type.punishment;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import src.ares.core.client.Client;
import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;
import src.ares.core.punish.type.WarnPunishment;

public class CommandWarn extends CoreCommand
{
	public CommandWarn()
	{
		super("warn", new String[] {}, 1, Rank.TRIAL_MOD, "<player> <reason>");
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

		Client offender = new Client(player);

		if (offender.isStaff())
		{
			getClient().sendMessage(getModuleName(), "You cannot warn a staff member.");
			return;
		}

		WarnPunishment warn = new WarnPunishment(UtilString.build(getArgs(), 1), new OfflineClient(offender.getName()), new OfflineClient(getClient().getName()));
		warn.apply();
	}
}
