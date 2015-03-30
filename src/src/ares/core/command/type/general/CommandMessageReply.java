package src.ares.core.command.type.general;

import org.bukkit.entity.Player;

import src.ares.core.client.Rank;
import src.ares.core.command.CommandManager;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.UtilString;

public class CommandMessageReply extends CoreCommand
{
	public CommandMessageReply()
	{
		super("r", new String[] { "tell" }, 1, Rank.PLAYER, "<message>");
	}

	@Override
	public void execute()
	{
		CommandMessage message = (CommandMessage) CommandManager.getInstance().getCommand("msg");

		if (message.getMessagers().containsKey(getClient().getPlayer()))
		{
			Player to = message.getMessagers().get(getClient().getPlayer());

			if (to == null)
			{
				getClient().sendMessage(getModuleName(), "Recipient is no longer online.");
				return;
			}

			message.sendMessage(getClient().getPlayer(), to, UtilString.build(getArgs(), 0));
		}
		else
		{
			getClient().sendMessage(getModuleName(), "You weren't chatting with somebody.");
		}
	}
}
