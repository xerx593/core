package src.ares.core.client;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.Main;
import src.ares.core.battle.BattleManager;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.greeting.Greeting;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilPlayer;
import src.ares.core.nms.TabTitle;
import src.ares.core.nms.Title;
import src.ares.core.panel.PlayerPanel;
import src.ares.core.server.data.ServerStorage;
import src.ares.core.world.WorldSelector;

public class ClientListener extends Module
{
	private static ClientListener instance = new ClientListener();

	public static ClientListener getInstance()
	{
		return instance;
	}

	private Greeting greeting = new Greeting();
	private PlayerPanel panel;
	private ServerStorage storage = ServerStorage.getInstance();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		Client client = new Client(event.getPlayer());
		client.getManager().createDataFile();
		client.getManager().setUsername(client.getName());

		Main.debug(client.getName() + " connected from " + event.getAddress().getHostAddress() + ".");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		if (event.getPlayer() instanceof Player)
		{
			Client client = new Client(event.getPlayer());
			final Player player = event.getPlayer();

			panel = new PlayerPanel(event.getPlayer());
			panel.assignPrefix();
			panel.registerEvents();

			// Resetting player's inventory.

			UtilPlayer.reset(player);

			// Teleport and adding Hub items.

			WorldSelector.selectHub(player).addHubItems(player);

			// Add a default kit to the player.

			BattleManager battleManager = BattleManager.getInstance();
			Kit defaultKit = battleManager.getKitByName("Ares Kit");
			battleManager.selectKit(defaultKit, player, false);

			// Showing a greeting to the player.

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.5F);

					if (ServerStorage.getInstance().isNormalMode())
						showNormalGreeting(player);
					else
						showGreeting(player);

					Title title = new Title("&b" + storage.getServerName(), storage.getServerMode().getDescription(), 2, 3, 2);
					title.send(player);
				}
			}, 80L);

			// Setup a join message if the player is an owner.

			if (client.getManager().getRank() == Rank.OWNER)
				event.setJoinMessage(ChatColor.GOLD + "" + ChatColor.BOLD + event.getPlayer().getName() + " joined the server.");
			else
				event.setJoinMessage(Chat.raw("&a&l+&7 " + player.getName()));

			client.unload();
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		Client client = new Client(player);

		// If the Owner does not have the rank, set it again.

		if (client.getName().contains("xTheOnlyOreOx") && client.getManager().getRank() != Rank.OWNER)
		{
			client.getManager().setRank(Rank.OWNER);
		}

		// If an Owner goes offline, setup a leave message.

		if (client.getManager().getRank() == Rank.OWNER)
		{
			event.setQuitMessage(ChatColor.RED + "" + ChatColor.BOLD + client.getName() + " left the server.");

			client.unload();
		}
		else
		{
			event.setQuitMessage(Chat.raw("&4&l-&7 " + player.getName()));
		}
	}

	@EventHandler
	public void sendAdditionals(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		TabTitle.sendTablist(player, ChatColor.AQUA + "" + ChatColor.BOLD + storage.getServerName(), ChatColor.GOLD + "   www.ares-network.net   ");
	}

	private void showGreeting(Player player)
	{
		player.sendMessage(Chat.seperator());
		player.sendMessage("");
		player.sendMessage(ChatColor.WHITE + "  Welcome back to Ares Network;");
		player.sendMessage("  " + storage.getServerMode().getColor() + storage.getServerMode().getDescription());
		player.sendMessage("");
		player.sendMessage(Chat.seperator());
	}

	private void showNormalGreeting(Player player)
	{
		String randomGreeting = greeting.chooseGreeting();

		player.sendMessage(Chat.seperator());
		player.sendMessage("");
		player.sendMessage(ChatColor.WHITE + "  Welcome back to Ares Network;");
		player.sendMessage(ChatColor.GRAY + "  " + randomGreeting);
		player.sendMessage("");
		player.sendMessage(Chat.seperator());

		Title title = new Title("&b" + storage.getServerName(), randomGreeting, 2, 3, 2);
		title.send(player);
	}
}
