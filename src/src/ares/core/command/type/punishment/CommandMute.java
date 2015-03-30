package src.ares.core.command.type.punishment;

import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;
import src.ares.core.punish.type.MutePunishment;

public class CommandMute extends CoreCommand
{
	public CommandMute()
	{
		super("mute", new String[] {}, 3, Rank.TRIAL_MOD, "<player> <duration> <reason>");
	}

	@Override
	public void execute()
	{
		OfflineClient offenderClient = null;
		String punishShortcutTime = getArgs()[1];
		double punishTime;
		String reason = UtilString.build(getArgs(), 2);
		MutePunishment mute = null;

		offenderClient = new OfflineClient(getArgs()[0]);

		if (offenderClient.isStaff())
		{
			getClient().sendMessage(getModuleName(), "You cannot mute a staff member.");
			return;
		}

		if (punishShortcutTime.contains("permanent"))
		{
			mute = new MutePunishment(reason, offenderClient, new OfflineClient(getClient().getName()), -1);
			mute.apply();
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

			mute = new MutePunishment(reason, offenderClient, new OfflineClient(getClient().getName()), punishTime);
			mute.apply();
		}
		catch (NumberFormatException e)
		{
			getClient().sendMessage("Error", Chat.gold(getArgs()[1]) + " is not formatted properly.");
			return;
		}
	}
}
