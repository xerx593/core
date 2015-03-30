package src.ares.core.server;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

import src.ares.core.common.Module;
import src.ares.core.server.data.ServerStorage;

public class DescriptionManager extends Module
{
	private static DescriptionManager instance = new DescriptionManager();

	public static DescriptionManager getInstance()
	{
		return instance;
	}

	private String motd;
	private ServerStorage config = ServerStorage.getInstance();

	public DescriptionManager()
	{
		super("Description");
	}

	/**
	 * Modifies the current server motd, changed when a server list ping event is triggered.
	 * 
	 * @param motd The new server motd.
	 */
	public void setMotd(String motd)
	{
		this.motd = motd;
	}

	@EventHandler
	public void updateMotd(ServerListPingEvent event)
	{
		if (motd == null)
		{
			event.setMotd(config.getDefaultMotd());
		}
		else
		{
			event.setMotd(config.getMotdHeader() + ChatColor.translateAlternateColorCodes('&', motd));
		}
	}
}
