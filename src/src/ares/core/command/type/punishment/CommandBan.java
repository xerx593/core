package src.ares.core.command.type.punishment;

import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;
import src.ares.core.punish.type.BanPunishment;

public class CommandBan extends CoreCommand
{
	public CommandBan()
	{
		super("ban", new String[] {}, 3, Rank.TRIAL_MOD, "<player> <duration> <reason>");
	}

	@Override
	public void execute()
	{
		OfflineClient offenderClient = null;
		String punishShortcutTime = getArgs()[1];
		double punishTime;
		String reason = UtilString.build(getArgs(), 2);
		BanPunishment ban = null;

		offenderClient = new OfflineClient(getArgs()[0]);

		if (offenderClient.isStaff())
		{
			getClient().sendMessage(getModuleName(), "You cannot ban a staff member.");
			return;
		}

		if (punishShortcutTime.contains("permanent"))
		{
			ban = new BanPunishment(reason, offenderClient, getClient(), -1);
			ban.apply();
			return;
		}

		try
		{
			punishTime = Double.parseDouble(getArgs()[1]);

			if (punishTime < 0)
			{
				getClient().sendMessage(getModuleName(), "You cannot enter a negative duration.");
				return;
			}

			ban = new BanPunishment(reason, offenderClient, getClient(), punishTime);
			ban.apply();

			getClient().sendMessage(getModuleName(), "Offender " + Chat.player(offenderClient.getName()) + " was banned accordingly.");
		}
		catch (NumberFormatException e)
		{
			getClient().sendMessage("Error", Chat.gold(getArgs()[1]) + " is not formatted properly.");
			return;
		}
	}
}
