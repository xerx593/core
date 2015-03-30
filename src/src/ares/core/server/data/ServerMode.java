package src.ares.core.server.data;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

public enum ServerMode
{
	CLOSED("This server is closed. Use: " + ChatColor.GOLD + "" + ChatColor.BOLD + "ares-network.net", ChatColor.GRAY), ALPHA("Alpha version is live!", ChatColor.GREEN), BETA("Beta version is live!", ChatColor.DARK_GREEN), DEVELOPMENT("Under maintenance, coming soon...", ChatColor.GOLD), NORMAL("Custom Coded PvP Server!", ChatColor.WHITE), HALLOWEEN("Happy Halloween Everyone!", ChatColor.GOLD), CHRISTMAS("Santa Claus wishes Merry Christmas!", ChatColor.YELLOW), EASTER("Happy Easter Everyone!", ChatColor.GREEN), SUMMER("Summer has arrived!", ChatColor.GREEN), MAINTENANCE("Server is being worked on. Standby!", ChatColor.RED);
	String description;
	ChatColor color;

	ServerMode(String description, ChatColor color)
	{
		this.description = description;
		this.color = color;
	}

	public ChatColor getColor()
	{
		return color;
	}

	public String getDescription()
	{
		return description;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString()
	{
		return StringUtils.capitalise(name().toLowerCase());
	}
}
