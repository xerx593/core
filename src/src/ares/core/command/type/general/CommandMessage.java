package src.ares.core.command.type.general;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.command.CoreCommand;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;
import src.ares.core.settings.Setting;

public class CommandMessage extends CoreCommand
{
	private HashMap<Player, Player> messagers = new HashMap<>();

	public CommandMessage()
	{
		super("msg", new String[] {}, 2, Rank.PLAYER, "<player> <message>");
	}

	@Override
	public void execute()
	{
		Player from = getClient().getPlayer();
		Player to = Bukkit.getPlayer(getArgs()[0]);

		if (to == null)
		{
			getClient().sendMessage(getModuleName(), Chat.player(getArgs()[0]) + " is not online.");
			return;
		}

		if (from == to)
		{
			getClient().sendMessage(getModuleName(), "You cannot send a message to yourself.");
			return;
		}
		
		Client fromClient = new Client(from);
		Client toClient = new Client(to);
		Setting setting = toClient.getManager().getSetting("Private Messages");
		
		if (!fromClient.getManager().hasEnabledSetting(setting))
		{
			getClient().sendMessage(setting.getName(), "Disabled, open the settings menu to re-enable it.");
			return;
		}

		if (!toClient.getManager().hasEnabledSetting(setting))
		{
			getClient().sendMessage("Settings", Chat.player(to.getName()) + " has disabled private messages.");
			return;
		}

		String message = UtilString.build(getArgs(), 1);
		sendMessage(from, to, message);
		
		fromClient.unload();
		toClient.unload();
	}

	public HashMap<Player, Player> getMessagers()
	{
		return messagers;
	}

	public void sendMessage(Player from, Player to, String message)
	{
		Client target = new Client(to);

		from.sendMessage(" " + Chat.player("You") + " sent a message to " + Chat.player(to.getName()) + ":");
		from.sendMessage(" " + ChatColor.GRAY + message);

		to.sendMessage(" " + Chat.player(from.getName()) + " sent a message to " + Chat.player("You") + ": ");
		to.sendMessage(" " + ChatColor.GRAY + message);

		messagers.put(to, from);
		messagers.put(from, to);

		target.unload();
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		
		if (messagers.containsKey(player))
			messagers.remove(player);
	}
}
