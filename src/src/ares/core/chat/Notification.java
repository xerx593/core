package src.ares.core.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import src.ares.core.client.Client;
import src.ares.core.common.util.Chat;

/**
 * Notification class is used to send a formatted chat message to a specific group of people.
 * 
 * It can send a <b>chat message</b> to:
 * <li>A specific player.
 * <li>To all online players.
 * <li>To all online staff.
 * <li>To all online developers.
 */
public class Notification
{
	// Default notification sound to be used when playSound() is called. //
	private static final Sound NOTIFY_SOUND = Sound.ORB_PICKUP;

	// The chat color used for the notification title. //
	private static final ChatColor HEAD_COLOR = ChatColor.BLUE;

	// The chat color used for the notification body. //
	private static final ChatColor BODY_COLOR = ChatColor.WHITE;

	/**
	 * Sends a basic chat notification to a player.
	 * 
	 * @param format Should we use the standard notification format?
	 * @param player The player to send the notification.
	 * @param head The title of the notification.
	 * @param body The body of the notification.
	 */
	public void notificationBase(boolean format, Player player, String head, String body)
	{
		if (format)
		{
			player.sendMessage("");
			player.sendMessage(" " + HEAD_COLOR + ChatColor.BOLD + head);
			player.sendMessage(" " + BODY_COLOR + body);
			player.sendMessage("");
		}
		else
		{
			player.sendMessage(Chat.format(head, body));
		}
	}

	public void playSound(Player player)
	{
		player.playSound(player.getLocation(), NOTIFY_SOUND, 0.5F, 1.5F);
	}

	public void sendToDevelopers(boolean format, String message)
	{
		sendToDevelopers(format, "Notification", message);
	}

	/**
	 * Sends a notification to all online developers.
	 * 
	 * @param format Should we use the standard notification format?
	 * @param head The title of the notification.
	 * @param body The body of the notification.
	 */
	@SuppressWarnings("deprecation")
	public void sendToDevelopers(boolean format, String head, String body)
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			Client client = new Client(player);

			if (client.isDeveloper())
			{
				notificationBase(format, player, head, body);
			}

			client.unload();
		}
	}

	/**
	 * Sends a notification to a group of players.
	 * 
	 * @param format Should we use the standard notification format?
	 * @param player The player to send the notification.
	 * @param head The title of the notification.
	 * @param body The body of the notification.
	 */
	public void sendToPlayers(boolean format, Player[] players, String head, String body)
	{
		for (Player player : players)
		{
			notificationBase(format, player, head, body);
		}
	}

	public void sendToPlayers(boolean format, String message)
	{
		sendToPlayers(format, "Notification", message);
	}

	@SuppressWarnings("deprecation")
	public void sendToPlayers(boolean format, String title, String message)
	{
		sendToPlayers(format, Bukkit.getOnlinePlayers(), title, message);
	}

	public void sendToStaff(boolean format, String message)
	{
		sendToStaff(format, "Notification", message);
	}

	/**
	 * Notifies all staff.
	 * 
	 * @param format Should we use the notification standard format?
	 * @param title The title of the message.
	 * @param message The body of the message.
	 */
	@SuppressWarnings("deprecation")
	public void sendToStaff(boolean format, String head, String body)
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			Client client = new Client(player);

			if (client.isStaff())
			{
				notificationBase(format, player, head, body);
			}

			client.unload();
		}
	}
}
