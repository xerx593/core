package src.ares.core.common.util;

import org.bukkit.ChatColor;

public class UtilCurrency
{
	private static String name = "Gold";
	private static ChatColor color = ChatColor.GOLD;

	public static ChatColor getColor()
	{
		return color;
	}

	public static String getTag()
	{
		return name;
	}
}
