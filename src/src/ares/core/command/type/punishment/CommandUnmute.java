package src.ares.core.command.type.punishment;

import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;

public class CommandUnmute extends CoreCommand
{
	public CommandUnmute()
	{
		super("unmute", new String[] {}, 1, Rank.MOD, "<player>");
	}

	@Override
	public void execute()
	{
		OfflineClient target = new OfflineClient(getArgs()[0]);

		if (!target.getPlayer().hasPlayedBefore())
		{
			getClient().sendMessage(getModuleName(), Chat.player(getArgs()[0]) + " never played before.");
			return;
		}

		target.getManager().resetMute();
		getClient().sendMessage(getModuleName(), Chat.player(target.getName()) + " mute punishments removed.");
	}
}
