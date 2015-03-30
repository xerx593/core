package src.ares.core.command.type.server;

import org.bukkit.ChatColor;

import src.ares.core.chat.Notification;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.UtilString;

public class CommandPurchase extends CoreCommand
{
	public CommandPurchase()
	{
		super("purchase", new String[] {}, 1, Rank.ADMIN, "<message>");
	}

	@Override
	public void prepare()
	{
		if (!isSenderPlayer())
		{
			Notification notification = new Notification();
			notification.sendToPlayers(false, "Shop", ChatColor.translateAlternateColorCodes('&', UtilString.build(getArgs(), 0)));
		}
	}

	@Override
	public void execute()
	{
		getClient().sendMessage(getModuleName(), "This command can only be run in console.");
	}
}
