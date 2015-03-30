package src.ares.core.command.type.server;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.server.data.ServerStorage;

public class CommandSetVersion extends CoreCommand
{
	private ServerStorage serverStorage = ServerStorage.getInstance();

	public CommandSetVersion()
	{
		super("setversion", new String[] {}, 1, Rank.ADMIN);
	}

	@Override
	public void execute()
	{
		serverStorage.setServerVersion(getArgs()[0]);
		getClient().sendMessage(getModuleName(), "Server version changed to v." + Chat.tool(serverStorage.getServerVersion()) + ".");
	}
}
