package src.ares.core.command.type.general;

import org.bukkit.Bukkit;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.cooldown.Cooldown;
import src.ares.core.common.util.Chat;

public class CommandBroadcast extends CoreCommand
{
	public CommandBroadcast()
	{
		super("broadcast", new String[]
		{
		"live", "livestream", "stream", "streaming"
		}, 1, Rank.PLAYER, "www.twitch.tv/channel");
	}

	@Override
	public void execute()
	{
		String link = getArgs()[0];

		if (!link.contains("www."))
		{
			getClient().sendMessage(getModuleName(), "Please provide a valid link.");
			return;
		}

		if (link.length() <= 5)
		{
			getClient().sendMessage(getModuleName(), "Link must contain more than 5 letters.");
			return;
		}

		if (!Cooldown.create(getClient().getPlayer(), 60.0, "/" + getName(), true))
		{
			Bukkit.broadcastMessage(Chat.format("Live", Chat.player(getClient().getName()) + " is having a livestream:"));
			Bukkit.broadcastMessage(Chat.link("- " + link));
		}
	}
}
