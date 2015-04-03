package src.ares.core.server;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.PluginDisableEvent;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.server.data.ServerStorage;
import src.ares.core.world.WorldSelector;

public class ServerListener extends Module
{
	private static ServerListener instance = new ServerListener();

	public static ServerListener getInstance()
	{
		return instance;
	}

	private ServerStorage storage = ServerStorage.getInstance();

	public ServerListener()
	{
		super("Server");
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);

		if (client.getCoreWorld() == null)
		{
			WorldSelector.selectHub(player);
			client.sendMessage(getModuleName(), "That world is not registered?");
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void reload(PluginDisableEvent event)
	{
		for (Player players : Bukkit.getOnlinePlayers())
		{
			Client client = new Client(players);
			client.kick("Established connection was aborted", "Server either updated, restarted or went down.\nPlease try re-connecting again.");
		}
	}

	/**
	 * Checks for maintenance or development mode, called when a player tries to login.
	 */
	@EventHandler
	public void authenticate(PlayerLoginEvent event)
	{
		Client client = new Client(event.getPlayer());

		if (!client.isStaff())
		{
			if (storage.isMaintenanceMode())
			{
				Main.debug(client.getName() + " tried to join during a maintenance.");

				event.setKickMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "We're working onto something, try again later.\n Only staff is allowed to join.");
				event.setResult(Result.KICK_OTHER);
			}
			else if (storage.isClosedMode())
			{
				Main.debug(client.getName() + " tried to join while server is closed.");

				event.setKickMessage(storage.getServerName() + "\n\n" + ChatColor.WHITE + "This server is now closed, use the new IP:\n" + ChatColor.GOLD + "ares-network.net");
				event.setResult(Result.KICK_OTHER);
			}
		}
	}

	@EventHandler
	public void messages(PlayerKickEvent event)
	{
		if (event.getLeaveMessage() == "Server Closed")
		{
			event.setLeaveMessage(ChatColor.YELLOW + "Server went down or just restarting, please try again.");
		}
	}
}
