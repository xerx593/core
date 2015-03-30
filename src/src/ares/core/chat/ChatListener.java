package src.ares.core.chat;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import src.ares.core.Main;
import src.ares.core.chat.filter.Filter;
import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilLocation;
import src.ares.core.world.WorldType;

public class ChatListener extends Module
{
	private static ChatListener instance = new ChatListener();

	public static ChatListener getInstance()
	{
		return instance;
	}

	private Filter filter;
	private ArrayList<String> commands = new ArrayList<>();

	public ChatListener()
	{
		super("Chat");

		commands.add("?");
		commands.add("kill");
		commands.add("bukkit");
		commands.add("me");
		commands.add("stop");
		commands.add("icanhasbukkit");
		commands.add("about");
		commands.add("timings");
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void ChatBlockCommands(PlayerCommandPreprocessEvent event)
	{
		Client client = new Client(event.getPlayer());

		for (String blockedCmd : commands)
		{
			if (event.getMessage().startsWith("/" + blockedCmd))
			{
				if (client.isDeveloper())
					return;

				client.sendMessage(getModuleName(), "Your will is not always my command.");
				client.playSound(Sound.EXPLODE, 1.0F, 1.0F);
				Main.debug(event.getPlayer().getName() + " tried to run " + event.getMessage() + " while not allowed.");
				event.setCancelled(true);

				client.unload();
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void ChatFormatChange(AsyncPlayerChatEvent event) throws IOException
	{
		Client client = new Client(event.getPlayer());

		Rank rank = client.getManager().getRank();
		String name = client.getName();
		String message = event.getMessage();

		// Modifying Chat Format.

		event.setFormat(Chat.structure(rank, name, message));

		if (client.getCoreWorld().getWorldType() == WorldType.HUB)
		{
			if (UtilLocation.inside(client.getLocation(), new Location(client.getWorld(), -2, 47, 2), new Location(client.getWorld(), 2, 50, -2)))
			{
				client.sendMessage(getModuleName(), "Please exit spawn to use the chat.");
				event.setCancelled(true);
			}
		}

		// Logging Chat Messages.

		ChatManager.getInstance().logMessage(client, message);

		client.unload();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void ChatPercentFix(AsyncPlayerChatEvent e)
	{
		String message = e.getMessage();
		String fixed;

		if (message.contains("%"))
		{
			fixed = message.replace("%", "%%");
			e.setMessage(fixed);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void ChatToggleFilter(AsyncPlayerChatEvent event)
	{
		String message = event.getMessage().toLowerCase();
		filter = new Filter(message);

		if (!filter.validate())
		{
			event.setMessage(filter.getFiltered());
		}
	}
}
