package src.ares.core.chat;

import java.util.LinkedHashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommunityLinks
{
	private static CommunityLinks instance = new CommunityLinks();

	public static CommunityLinks getInstance()
	{
		return instance;
	}

	private LinkedHashMap<String, String> links;

	public void createLinks()
	{
		links = new LinkedHashMap<>();

		links.put("Website", "www.ares-network.net");
		links.put("Teamspeak", "ts3.ares-network.net");
		links.put("Twitter", "www.twitter.com/aresnetworkmc");
		links.put("Music", "www.plug.dj/ares-network-official");
	}

	public void printCollection(Player player)
	{
		for (String title : links.keySet())
		{
			player.sendMessage(" " + ChatColor.AQUA + "" + ChatColor.BOLD + title);
			player.sendMessage(" " + ChatColor.GRAY + links.get(title));
		}
	}

	public LinkedHashMap<String, String> getCollection()
	{
		return links;
	}
}
