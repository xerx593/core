package src.ares.core.punish;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;

public class PunishmentAuth extends Module
{
	private static PunishmentAuth instance = new PunishmentAuth();

	public static PunishmentAuth getInstance()
	{
		return instance;
	}

	public PunishmentAuth()
	{
		super("Chat");
	}

	@EventHandler
	public void EventBanCheck(PlayerLoginEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);

		double banTime = client.getManager().getBanDuration();
		double currentTime = System.currentTimeMillis() / 60000;

		// Check if the player is banned permanently.

		if (banTime == -1.0)
		{
			event.setKickMessage("Banned because of " + ChatColor.YELLOW + client.getManager().getBanReason() + ChatColor.WHITE + " until " + ChatColor.AQUA + " Permanent " + ChatColor.WHITE + " by " + Chat.player(client.getManager().getBanPunisher()) + ".");
			event.setResult(Result.KICK_BANNED);
		}

		// Check if the player is banned temporarily.

		else if (banTime != 0.0)
		{
			// Unban the player if the duration ran out.

			if (banTime < currentTime)
			{
				client.getManager().resetBan();
			}

			// Disconnect the player if the duration is not ran out.

			else
			{
				event.setKickMessage("Banned because of " + ChatColor.YELLOW + client.getManager().getBanReason() + ChatColor.WHITE + " until " + ChatColor.AQUA + format(banTime) + ChatColor.WHITE + " by " + Chat.player(client.getManager().getBanPunisher()) + ".");
				event.setResult(Result.KICK_BANNED);
			}
		}
	}

	@EventHandler
	public void EventCommandCheck(PlayerCommandPreprocessEvent e)
	{
		Player player = e.getPlayer();
		Client client = new Client(player);

		double muteTime = client.getManager().getMuteDuration();

		if (muteTime != 0.0)
		{
			client.sendMessage(getModuleName(), "You cannot use commands while muted.");
			e.setCancelled(true);
			client.playSound(Sound.PISTON_RETRACT, 0.5F, 0.6F);
		}
	}

	@EventHandler
	public void EventMuteCheck(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);

		double muteTime = client.getManager().getMuteDuration();
		double currentTime = System.currentTimeMillis() / 60000;

		// Check if the player is muted permanently.

		if (muteTime == -1.0)
		{
			client.sendMessage(getModuleName(), "You have been muted permanently.");
			event.setCancelled(true);
			client.playSound(Sound.PISTON_RETRACT, 0.5F, 0.6F);
		}

		// Check if the player is muted temporarily.

		else if (muteTime != 0.0)
		{
			// Unmute the player if the duration ran out.

			if (muteTime < currentTime)
			{
				client.getManager().resetMute();
			}

			// Prevent from speaking if the duration is not ran out.

			else
			{
				client.sendMessage(getModuleName(), "You have been muted until " + Chat.tool(format(muteTime)) + ".");
				event.setCancelled(true);
				client.playSound(Sound.PISTON_RETRACT, 0.5F, 0.6F);
			}
		}
	}

	/**
	 * Returns a formatted date depending on the value given, multiplied by 60,000.
	 * 
	 * @param value The value to format in minutes.
	 * @return String
	 */
	public String format(double value)
	{
		DateFormat formatted = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatted.format(value * 60000);
	}
}
