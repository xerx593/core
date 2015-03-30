package src.ares.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import src.ares.core.battle.BattleManager;
import src.ares.core.battle.killstreak.KillstreakListener;
import src.ares.core.battle.listener.BattleListener;
import src.ares.core.battle.listener.CombatLogListener;
import src.ares.core.battle.listener.ProtectionListener;
import src.ares.core.battle.upgrade.UpgradeManager;
import src.ares.core.chat.ChatListener;
import src.ares.core.chat.CommunityLinks;
import src.ares.core.chat.FlashNotification;
import src.ares.core.chat.Notification;
import src.ares.core.chat.announcement.Announcement;
import src.ares.core.chat.announcement.AnnouncementManager;
import src.ares.core.client.ClientListener;
import src.ares.core.client.StaffListener;
import src.ares.core.command.CommandManager;
import src.ares.core.command.RankPermissions;
import src.ares.core.common.SoupAddon;
import src.ares.core.common.SpectateListener;
import src.ares.core.common.util.Chat;
import src.ares.core.currency.CurrencyListener;
import src.ares.core.gadget.GadgetManager;
import src.ares.core.menu.MenuManager;
import src.ares.core.parkour.ParkourListener;
import src.ares.core.portal.PortalManager;
import src.ares.core.punish.PunishmentAuth;
import src.ares.core.server.DescriptionManager;
import src.ares.core.server.ServerDatabase;
import src.ares.core.server.ServerListener;
import src.ares.core.server.data.ServerMode;
import src.ares.core.server.data.ServerStorage;
import src.ares.core.settings.SettingsListener;
import src.ares.core.settings.SettingsManager;
import src.ares.core.stats.StatsManager;
import src.ares.core.updater.Updater;
import src.ares.core.vote.VoteListener;
import src.ares.core.world.WorldManager;

public class Main extends JavaPlugin
{
	private static Logger logger = Logger.getLogger("AresNetwork");

	public void onEnable()
	{
		try
		{
			ServerStorage.getInstance().createFile();
			ServerStorage.getInstance().addConfigData();
			ServerListener.getInstance().registerEvents();
			WorldManager.getInstance().createWorlds();
			BattleManager.getInstance().createKits();
			AnnouncementManager.getInstance().createAnnouncements();
			UpgradeManager.getInstance().createUpgrades();
			StatsManager.getInstance().createStats();
			CommandManager.getInstance().createCommands();
			MenuManager.getInstance().createMenus();
			GadgetManager.getInstance().createGadgets();
			PortalManager.getInstance().createPortals();
			// DetectionManager.getInstance().createDetections();
			SettingsManager.getInstance().createSettings();
			CommunityLinks.getInstance().createLinks();

			ServerDatabase.getInstance().openConnection();
			ServerDatabase.getInstance().createDatabase();
			ServerDatabase.getInstance().registerEvents();

			ClientListener.getInstance().registerEvents();
			ChatListener.getInstance().registerEvents();
			RankPermissions.getInstance().registerEvents();
			CombatLogListener.getInstance().registerEvents();
			KillstreakListener.getInstance().registerEvents();
			PunishmentAuth.getInstance().registerEvents();
			FlashNotification.getInstance().registerEvents();
			SpectateListener.getInstance().registerEvents();
			StaffListener.getInstance().registerEvents();
			SettingsListener.getInstance().registerEvents();

			BattleListener.getInstance().registerEvents();
			ProtectionListener.getInstance().registerEvents();
			CombatLogListener.getInstance().registerEvents();
			// MonstersListener.getInstance().registerEvents();

			DescriptionManager.getInstance().registerEvents();
			GadgetManager.getInstance().registerEvents();
			CurrencyListener.getInstance().registerEvents();
			SoupAddon.getInstance().registerEvents();
			// EliteListener.getInstance().registerEvents();
			Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

			if (Bukkit.getPluginManager().isPluginEnabled("Votifier"))
			{
				new VoteListener().registerEvents();
			}

			new ParkourListener().registerEvents();
			new Updater();
			new Announcement().runTaskTimer(this, 6000L, 6000L); // 5 minutes interval.

			showGreeting();
		}
		catch (Exception e)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Server Startup Exception:");
			e.printStackTrace();
		}
	}

	public void onDisable()
	{
		ServerDatabase.getInstance().closeConnection();
	}

	public static Plugin getPlugin()
	{
		return Bukkit.getPluginManager().getPlugin("Core");
	}

	public static void log(String message, Level level)
	{
		logger.log(level, "[" + getPlugin().getDescription().getName() + "] " + message);
	}

	public static void debug(String message)
	{
		Notification notification = new Notification();
		ServerMode mode = ServerStorage.getInstance().getServerMode();

		if (mode == ServerMode.DEVELOPMENT || mode == ServerMode.MAINTENANCE)
		{
			Bukkit.getConsoleSender().sendMessage(Chat.format("Debug", message));
			notification.sendToDevelopers(false, "Psst", ChatColor.GRAY + message);
		}
	}

	public static void error(String className, String message)
	{
		logger.log(Level.SEVERE, "[" + className + "] Error: " + message);
	}

	private void showGreeting()
	{
		Main.debug("------------------------------");
		Main.debug("Welcome to " + ServerStorage.getInstance().getServerName());
		Main.debug("Server Mode: " + ServerStorage.getInstance().getServerMode());
		Main.debug("Server Version: " + ServerStorage.getInstance().getServerVersion());
		Main.debug("------------------------------");
	}
}
