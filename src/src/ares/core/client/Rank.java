package src.ares.core.client;

import org.bukkit.ChatColor;

/**
 * Stores all available ranks of the server.
 * 
 * @see {@link Client}<br>{@link OfflineClient}<br>{@link ClientManager}
 */
public enum Rank
{
	PLAYER("Player", ChatColor.YELLOW), SUPPORTER("Supporter", ChatColor.BLUE), ELITE("Elite", ChatColor.BLUE), OLYMPIAN("Olympian", ChatColor.GREEN), TITAN("Titan", ChatColor.GOLD), YOUTUBER("YouTuber", ChatColor.DARK_RED), BUILDER("Builder", ChatColor.GREEN), TRIAL_MOD("Trial-Mod", ChatColor.LIGHT_PURPLE), MOD("Mod", ChatColor.DARK_PURPLE), SR_MOD("Sr-Mod", ChatColor.DARK_PURPLE), ADMIN("Admin", ChatColor.RED), DEV("Dev", ChatColor.DARK_AQUA), OWNER("Owner", ChatColor.DARK_RED);

	private String name;
	private ChatColor color;

	/**
	 * Default Constructor
	 * 
	 * @param name The name of the rank.
	 * @param color The color of the rank.
	 */
	Rank(String name, ChatColor color)
	{
		this.name = name;
		this.color = color;
	}

	public ChatColor getColor()
	{
		return color;
	}

	public String getName()
	{
		return name;
	}
}
