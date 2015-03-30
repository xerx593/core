package src.ares.core.command.type.general;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;

public class CommandAway extends CoreCommand
{
	private ArrayList<Player> afks_;

	public CommandAway()
	{
		super("afk", new String[] { "busy", "away" }, 0, Rank.TRIAL_MOD);

		this.afks_ = new ArrayList<>();
	}

	@Override
	public void execute()
	{
		if (!afks_.contains(getClient().getPlayer()))
		{
			afks_.add(getClient().getPlayer());

			for (Player player : getClient().getPlayer().getWorld().getPlayers())
			{
				player.playSound(player.getLocation(), Sound.SHEEP_IDLE, 1.5F, 1.0F);
			}

			Bukkit.broadcastMessage(Chat.format("Away", Chat.player(getClient().getName()) + " is currently busy."));
		}
		else
		{
			afks_.remove(getClient().getPlayer());
			Bukkit.broadcastMessage(Chat.format("Back", Chat.player(getClient().getName()) + " is no longer busy."));
		}
	}

	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event)
	{
		if (afks_.contains(event.getPlayer()))
		{
			Client client = new Client(event.getPlayer());

			Rank rank = client.getManager().getRank();
			String name = client.getName();
			String message = event.getMessage();

			event.setFormat(Chat.structure(ChatColor.GRAY + "Away", rank, name, message));
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if (afks_.contains(event.getPlayer()))
			afks_.remove(event.getPlayer());
	}
}
