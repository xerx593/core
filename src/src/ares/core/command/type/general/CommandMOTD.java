package src.ares.core.command.type.general;

import org.bukkit.ChatColor;

import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.UtilString;
import src.ares.core.server.DescriptionManager;

public class CommandMOTD extends CoreCommand
{
	private String newMotd;

	public CommandMOTD()
	{
		super("motd", new String[] {}, 1, Rank.ADMIN, "<description>");
	}

	@Override
	public void execute()
	{
		this.newMotd = UtilString.build(getArgs(), 0);

		getClient().sendMessage(getModuleName(), "Server description updated to: ");
		getClient().sendRaw(ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', newMotd));

		DescriptionManager.getInstance().setMotd(newMotd);
	}
}
