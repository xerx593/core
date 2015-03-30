package src.ares.core.chat.announcement;

import java.util.ArrayList;
import java.util.List;

import src.ares.core.common.util.Chat;

public class AnnouncementManager
{
	private static AnnouncementManager instance = new AnnouncementManager();

	public static AnnouncementManager getInstance()
	{
		return instance;
	}

	private List<String> announcements;

	public void createAnnouncements()
	{
		announcements = new ArrayList<>();

		announcements.add("Ares Network is a completely custom coded server.");
		announcements.add("You can join with both 1.7 and 1.8 minecraft versions.");
		announcements.add("Visit our website at " + Chat.link("www.ares-network.net") + ".");
		announcements.add("Visit our twitter at " + Chat.link("www.twitter.com/aresnetworkmc") + ".");
		announcements.add("Use " + Chat.command("/stream") + " to invite players when streaming.");
		announcements.add("Use " + Chat.command("/gold") + " or " + Chat.command("/ambrosia") + " to directly check your balances.");
		announcements.add("Premium ranks can use " + Chat.command("/spectate") + " to spectate pvp battles.");
		announcements.add("Modify your settings by clicking the " + Chat.tool("Redstone Torch") + " in a hub.");
		announcements.add("Use fun gadgets by clicking the " + Chat.tool("Chest") + " in a hub.");
		announcements.add("Each kit is unique, master it's abilities to become the best.");
		announcements.add("When killing a player, remember to collect the loot.");
		announcements.add("Upgrade your Kits by reaching statistic milestones.");
		announcements.add("Kit Level has a massive impact on Ability performance.");
		announcements.add("You must wait " + Chat.time("5 seconds") + " before leaving a battle.");
		announcements.add("Found a hacker? Gather evidence and open a forum report.");
		announcements.add("Forums are a great place to find new friends.");
	}

	public List<String> getAnnouncements()
	{
		return announcements;
	}
}
