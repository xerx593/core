package src.ares.core.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.Main;
import src.ares.core.common.Module;

/**
 * FlashNotification Class sends a message to player, and cannot be reset for a certain period of time.
 * By using the send() method, you can prevent chat flood very easily.
 */
public class FlashNotification extends Module
{
	private static FlashNotification instance = new FlashNotification();

	public static FlashNotification getInstance()
	{
		return instance;
	}

	private List<Player> messages = new ArrayList<>();

	/**
	 * Default Constructor
	 * 
	 */
	public FlashNotification()
	{
		super("Flash Notification");
	}

	public boolean send(final Player player, String message)
	{
		return send(player, message, 5);
	}

	public boolean send(final Player player, String message, int delay)
	{
		if (!messages.contains(player))
		{
			messages.add(player);
			player.sendMessage(message);

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					messages.remove(player);
				}
			}, 20 * delay);

			return true;
		}

		return false;
	}

	@EventHandler
	public void unregister(PlayerQuitEvent event)
	{
		messages.remove(event.getPlayer());
	}
}
