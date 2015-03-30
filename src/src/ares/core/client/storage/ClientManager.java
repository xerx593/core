package src.ares.core.client.storage;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import src.ares.core.Main;
import src.ares.core.battle.BattleManager;
import src.ares.core.battle.kit.Kit;
import src.ares.core.battle.upgrade.Upgrade;
import src.ares.core.battle.upgrade.UpgradeManager;
import src.ares.core.chat.Notification;
import src.ares.core.client.OfflineClient;
import src.ares.core.client.Rank;
import src.ares.core.common.ExceptionManager;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;
import src.ares.core.currency.CurrencyType;
import src.ares.core.currency.ICurrency;
import src.ares.core.settings.Setting;
import src.ares.core.settings.SettingsManager;
import src.ares.core.stats.Statistic;
import src.ares.core.stats.StatsManager;
import src.ares.core.vote.Reward;

/**
 * Creates new a storage file for a specific UUID of a player. The data is stored in YAML form inside the plugin's name default directory.
 */
public class ClientManager
{
	private Player onlinePlayer;
	private OfflinePlayer offlinePlayer;
	private FileConfiguration config;

	private File coreFolder;
	private File storageFolder;
	private File playerFile;

	private Notification notification = new Notification();

	private int[] milestones =
	{
	100, 300, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000
	};

	/**
	 * Default Constructor
	 * 
	 * @param uuid The player's UUID to create a data file.
	 */
	public ClientManager(UUID uuid)
	{
		this.onlinePlayer = Bukkit.getPlayer(uuid);
		this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);

		// Plugin Folder - /plugins/Core/
		this.coreFolder = Main.getPlugin().getDataFolder();

		// Storage Folder - /plugins/Core/storage
		this.storageFolder = new File(coreFolder.getPath() + File.separator + "storage");

		// Player Storage - /plugins/Core/storage/<uuid>.yml
		this.playerFile = new File(storageFolder, uuid + ".yml");
		this.config = YamlConfiguration.loadConfiguration(playerFile);

		if (!playerFile.exists())
		{
			createDataFile();
		}
	}

	/**
	 * Populates the configuration file of the player's UUID provided in the constructor.
	 */
	private void addDefaults()
	{
		config.addDefault(ClientSection.USERNAME.toString(), "None");

		// Rank Data
		config.addDefault(ClientSection.RANK.toString(), Rank.PLAYER.toString());
		config.addDefault(ClientSection.SECOND_RANK.toString(), Rank.PLAYER.toString());

		config.addDefault(ClientSection.COMBAT_LOGGED.toString(), 0);
		config.addDefault(ClientSection.KILLSTREAK.toString(), 0);

		AddConfigSettings();

		// Currency Data

		for (CurrencyType type : CurrencyType.values())
		{
			config.addDefault(type.toString(), 0);
		}

		// Banned Data
		config.addDefault(ClientSection.BANNED.toString() + ".duration", 0.0);
		config.addDefault(ClientSection.BANNED.toString() + ".reason", "none");
		config.addDefault(ClientSection.BANNED.toString() + ".punisher", "none");

		// Muted Data
		config.addDefault(ClientSection.MUTED.toString() + ".duration", 0.0);
		config.addDefault(ClientSection.MUTED.toString() + ".reason", "none");
		config.addDefault(ClientSection.MUTED.toString() + ".punisher", "none");

		// Kit Data

		AddConfigKits();

		// Statistics Data

		AddConfigStatistics();

		// Armor Upgrades

		AddConfigUpgrades();

		if (config.getConfigurationSection(ClientSection.HISTORY.toString()) == null)
			config.createSection(ClientSection.HISTORY.toString());

		config.addDefault(ClientSection.CHALLENGES.toString() + ".parkour-hub", false);

		config.options().copyDefaults(true);
		save();
	}

	/**
	 * Creates a new data file with specific player data.
	 */
	public void createDataFile()
	{
		try
		{
			coreFolder.mkdir();
			storageFolder.mkdir();

			if (!playerFile.exists())
			{
				playerFile.createNewFile();
			}
		}
		catch (IOException e)
		{
			ExceptionManager.handle(e);
		}

		addDefaults();
	}

	private void AddConfigSettings()
	{
		for (Setting setting : SettingsManager.getInstance().getSettingBag())
		{
			String section = UtilString.slug(setting.getName());
			config.addDefault(ClientSection.SETTINGS.toString() + "." + section, true);
		}
	}

	private void AddConfigKits()
	{
		for (Kit kit : BattleManager.getInstance().getKitBag())
		{
			String section = UtilString.slug(kit.getName());

			if (kit.isFree())
				config.addDefault(ClientSection.KITS.toString() + "." + section + ".owned", true);
			else
				config.addDefault(ClientSection.KITS.toString() + "." + section + ".owned", false);

			if (isKitOwned(kit))
				config.addDefault(ClientSection.KITS.toString() + "." + section + ".level", 1);
			else
				config.addDefault(ClientSection.KITS.toString() + "." + section + ".level", 0);
		}

	}

	private void AddConfigStatistics()
	{
		for (Statistic stat : StatsManager.getInstance().getStatisticBag())
		{
			String sectionName = UtilString.slug(stat.getTitle());

			config.addDefault(ClientSection.STATS.toString() + "." + sectionName, 0);
		}
	}

	private void AddConfigUpgrades()
	{
		UpgradeManager manager = UpgradeManager.getInstance();

		for (Upgrade upgrade : manager.getUpgrades())
		{
			String section = UtilString.slug(upgrade.getName());

			if (upgrade.getName().contains("Armor Type Upgrade"))
			{
				config.addDefault(ClientSection.UPGRADES.toString() + "." + section + ".level", 1);
				continue;
			}
			config.addDefault(ClientSection.UPGRADES.toString() + "." + section + ".level", 0);
		}
	}

	/**
	 * Updates a specific currency value of the player.
	 * 
	 * @param currency The currency name to update.
	 * @param amount The new balance of the player.
	 * @param inform Wether or not to display a failure message.
	 * @return Boolean
	 */
	public boolean updateCurrency(String currency, int amount, boolean inform)
	{
		if (amount < 0)
		{
			if (inform && onlinePlayer != null)
			{
				onlinePlayer.sendMessage(Chat.format("Error", "You do not have sufficient funds."));
			}

			return false;
		}
		else
		{
			config.set(currency, amount);
			Main.debug("Updating " + offlinePlayer.getName() + " " + currency + " to " + amount + ".");
			save();
			return true;
		}
	}

	/**
	 * Updates a specific currency value of the player.
	 * 
	 * @param currency The currency object.
	 * @param inform Wether or not to display a failure message.
	 * @return Boolean
	 */
	public boolean updateCurrency(ICurrency currency, boolean inform)
	{
		return updateCurrency(currency.getName(), currency.getAmount(), inform);
	}

	public int getCurrency(CurrencyType type)
	{
		return config.getInt(type.toString());
	}

	public Set<String> getPunishmentHistory()
	{
		return config.getConfigurationSection(ClientSection.HISTORY.toString()).getKeys(false);
	}

	public Setting getSetting(String name)
	{
		for (Setting setting : SettingsManager.getInstance().getSettingBag())
		{
			if (setting.getName().contains(name))
				return setting;
		}

		return null;
	}

	@SuppressWarnings("unused")
	public void addWarnHistory(String punisher, String offender, String reason)
	{
		int count = 0;

		for (String keys : config.getConfigurationSection(ClientSection.HISTORY.toString()).getKeys(false))
		{
			count++;
		}

		String identifier = ClientSection.HISTORY.toString() + ".warning-" + count;

		config.set(identifier + ".punisher", punisher);
		config.set(identifier + ".offender", offender);
		config.set(identifier + ".reason", reason);

		save();
	}

	/**
	 * Adds a ban history entry to the player.
	 * 
	 * @param punisher The punisher's name.
	 * @param offender The offender's name.
	 * @param duration The duration of the punishment.
	 * @param reason The reason for the punishment.
	 */
	@SuppressWarnings("unused")
	public void addBanHistory(String punisher, String offender, String duration, String reason)
	{
		int count = 0;

		for (String keys : config.getConfigurationSection(ClientSection.HISTORY.toString()).getKeys(false))
		{
			count++;
		}

		String identifier = ClientSection.HISTORY.toString() + ".ban-" + count;

		config.set(identifier + ".punisher", punisher);
		config.set(identifier + ".offender", offender);
		config.set(identifier + ".duration", duration);
		config.set(identifier + ".reason", reason);

		save();
	}

	/**
	 * Adds a mute history entry to the player.
	 * 
	 * @param punisher The punisher's name.
	 * @param offender The offender's name.
	 * @param duration The duration of the punishment.
	 * @param reason The reason for the punishment.
	 */
	@SuppressWarnings("unused")
	public void addMuteHistory(String punisher, String offender, String duration, String reason)
	{
		int count = 0;

		for (String keys : config.getConfigurationSection(ClientSection.HISTORY.toString()).getKeys(false))
		{
			count++;
		}

		String identifier = ClientSection.HISTORY.toString() + ".mute-" + count;

		config.set(identifier + ".punisher", punisher);
		config.set(identifier + ".offender", offender);
		config.set(identifier + ".duration", duration);
		config.set(identifier + ".reason", reason);

		save();
	}

	/**
	 * Returns the ban duration.
	 * 
	 * @return Double
	 */
	public double getBanDuration()
	{
		double duration = config.getDouble(ClientSection.BANNED.toString() + ".duration");
		return duration;
	}

	/**
	 * Returns the ban punisher.
	 * 
	 * @return String
	 */
	public String getBanPunisher()
	{
		String punisher = config.getString(ClientSection.BANNED.toString() + ".punisher");
		return punisher;
	}

	/**
	 * Returns the ban reason.
	 * 
	 * @return String
	 */
	public String getBanReason()
	{
		String reason = config.getString(ClientSection.BANNED.toString() + ".reason");
		return reason;
	}

	/**
	 * Returns the amount of combat logs.
	 * 
	 * @return Integer
	 */
	public int getCombatLog()
	{
		return config.getInt(ClientSection.COMBAT_LOGGED.toString());
	}

	public FileConfiguration getConfig()
	{
		return config;
	}

	public File getFile()
	{
		return playerFile;
	}

	/**
	 * Returns the highest killstreak.
	 * 
	 * @return Integer
	 */
	public int getKillstreak()
	{
		return config.getInt(ClientSection.KILLSTREAK.toString());
	}

	/**
	 * Returns the mute duration.
	 * 
	 * @return Double
	 */
	public double getMuteDuration()
	{
		double duration = config.getDouble(ClientSection.MUTED.toString() + ".duration");
		return duration;
	}

	/**
	 * Returns the mute punisher.
	 * 
	 * @return String
	 */
	public String getMutePunisher()
	{
		String punisher = config.getString(ClientSection.MUTED.toString() + ".punisher");
		return punisher;
	}

	/**
	 * Returns the mute reason.
	 * 
	 * @return String
	 */
	public String getMuteReason()
	{
		String reason = config.getString(ClientSection.MUTED.toString() + ".reason");
		return reason;
	}

	/**
	 * Returns the Rank of the player.
	 * 
	 * @return Rank.
	 */
	public Rank getRank()
	{
		String rankValue = config.getString(ClientSection.RANK.toString());

		try
		{
			Rank rank = Rank.valueOf(UtilString.enumerator(rankValue));
			return rank;
		}
		catch (IllegalArgumentException e)
		{
			return Rank.PLAYER;
		}
	}

	/**
	 * Returns the second rank of the player.
	 * 
	 * @return Rank
	 */
	public Rank getSecondRank()
	{
		String rankValue = config.getString(ClientSection.SECOND_RANK.toString());

		try
		{
			Rank rank = Rank.valueOf(UtilString.enumerator(rankValue));
			return rank;
		}
		catch (IllegalArgumentException e)
		{
			Main.log("Failed to fetch second rank", Level.SEVERE);
			return Rank.PLAYER;
		}
	}

	/**
	 * Returns the statistic number.
	 * 
	 * @param title
	 *            The name of the statistic.
	 * @return Integer
	 */
	public int getStatistic(String title)
	{
		String path = title.toLowerCase().replace(' ', '-');

		return config.getInt(ClientSection.STATS.toString() + "." + path);
	}

	/**
	 * Returns the level of a specific upgrade.
	 * 
	 * @param upgrade
	 *            The upgrade to get.
	 * @return Integer
	 */
	public int getUpgradeLevel(Upgrade upgrade)
	{
		return config.getInt(ClientSection.UPGRADES.toString() + "." + UtilString.slug(upgrade.getName()) + ".level");
	}

	public String getUsername()
	{
		return config.getString(ClientSection.USERNAME.toString());
	}

	/**
	 * Checks if the Kit is owned by the player.
	 * 
	 * @param kit The Kit to search.
	 * @return Boolean
	 */
	public boolean isKitOwned(Kit kit)
	{
		return config.getBoolean(ClientSection.KITS.toString() + "." + UtilString.slug(kit.getName()) + ".owned");
	}

	/**
	 * Returns the level of the Kit.
	 * 
	 * @param kit The Kit to search.
	 * @return Integer
	 */
	public int getKitLevel(Kit kit)
	{
		return config.getInt(ClientSection.KITS.toString() + "." + UtilString.slug(kit.getName()) + ".level");
	}

	public String getKitLevelFormat(Kit kit)
	{
		int level = getKitLevel(kit);

		if (level == 0)
			return ChatColor.WHITE + "" + ChatColor.BOLD + "Level " + level + ChatColor.RESET;
		else if (level == 1)
			return ChatColor.YELLOW + "" + ChatColor.BOLD + "Level " + level + ChatColor.RESET;
		else if (level == 2)
			return ChatColor.GOLD + "" + ChatColor.BOLD + "Level " + level + ChatColor.RESET;
		else if (level == 3)
			return ChatColor.RED + "" + ChatColor.BOLD + "Level " + level + ChatColor.RESET;
		else
			return ChatColor.WHITE + "" + ChatColor.BOLD + "No Level";
	}

	/**
	 * Plays an ender dragon sound when the player reaches a specific amount of
	 * score.
	 */
	@SuppressWarnings("deprecation")
	private void playStatisticSound()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			player.playSound(player.getLocation(), ClientConst.STATISTIC_MILESTONE, 1.0F, 1.5F);
		}
	}

	/**
	 * Resets the ban punishment.
	 */
	public void resetBan()
	{
		config.set(ClientSection.BANNED.toString() + ".duration", 0);
		config.set(ClientSection.BANNED.toString() + ".reason", "none");
		config.set(ClientSection.BANNED.toString() + ".punisher", "none");
		save();
	}

	/**
	 * Resets the mute punishment.
	 */
	public void resetMute()
	{
		config.set(ClientSection.MUTED.toString() + ".duration", 0);
		config.set(ClientSection.MUTED.toString() + ".reason", "none");
		config.set(ClientSection.MUTED.toString() + ".punisher", "none");
		save();
	}

	/**
	 * Saves the configuration file.
	 */
	private void save()
	{
		try
		{
			config.save(playerFile);
		}
		catch (Exception e)
		{
			Main.error(getClass().getSimpleName(), "Could not save configuration file.");
			e.printStackTrace();
		}
	}

	/**
	 * Sets a new ban punishment.
	 * 
	 * @param duration
	 *            The new duration.
	 */
	public void setBanned(double duration, String reason, String punisher)
	{
		config.set(ClientSection.BANNED.toString() + ".duration", duration);
		config.set(ClientSection.BANNED.toString() + ".reason", reason);
		config.set(ClientSection.BANNED.toString() + ".punisher", punisher);
		save();
	}

	/**
	 * Sets the combat logged times.
	 * 
	 * @param amount
	 *            The amount of combat logs.
	 */
	public void setCombatLog(int amount)
	{
		config.set(ClientSection.COMBAT_LOGGED.toString(), amount);
		save();
	}

	/**
	 * Sets a new killstreak.
	 * 
	 * @param amount
	 *            The highest killstreak.
	 */
	public void setKillstreak(int amount)
	{
		config.set(ClientSection.KILLSTREAK.toString(), amount);
		save();
	}

	public void upgradeKitLevel(Kit kit)
	{
		config.set(ClientSection.KITS.toString() + "." + UtilString.slug(kit.getName()) + ".level", getKitLevel(kit) + 1);

		if (getKitLevel(kit) > ClientConst.MAX_KIT_LEVEL)
		{
			config.set(ClientSection.KITS.toString() + "." + UtilString.slug(kit.getName()) + ".level", ClientConst.MAX_KIT_LEVEL);
		}

		if (onlinePlayer != null)
			onlinePlayer.sendMessage(Chat.format("Kit", "You have upgraded " + Chat.kit(kit.getName()) + " to " + getKitLevelFormat(kit) + "."));

		save();
	}

	/**
	 * Sets a specific kit as owned for the player.
	 * 
	 * @param kit
	 *            The Kit to select.
	 * @param owned
	 *            Set if owned or not.
	 */
	public void setKitOwned(Kit kit, boolean owned)
	{
		config.set(ClientSection.KITS.toString() + "." + UtilString.slug(kit.getName()) + ".owned", owned);
		config.set(ClientSection.KITS.toString() + "." + UtilString.slug(kit.getName()) + ".level", 1);

		if (onlinePlayer != null)
		{
			onlinePlayer.sendMessage(Chat.format("Kit", "You have unlocked the " + kit.getName() + "."));
		}

		save();
	}

	/**
	 * Sets the Mute duration for the player.
	 * 
	 * @param duration
	 *            The new duration.
	 */
	public void setMute(double duration, String reason, String punisher)
	{
		config.set(ClientSection.MUTED.toString() + ".duration", duration);
		config.set(ClientSection.MUTED.toString() + ".reason", reason);
		config.set(ClientSection.MUTED.toString() + ".punisher", punisher);
		save();
	}

	public void setRank(Rank rank)
	{
		setRank(rank, false);
	}

	/**
	 * Sets the Rank of the player.
	 * 
	 * @param rank
	 *            The new Rank.
	 */
	public void setRank(Rank rank, boolean inform)
	{
		config.set(ClientSection.RANK.toString(), rank.getName());

		if (offlinePlayer != null)
		{
			if (inform)
				notification.sendToPlayers(false, "Rank", Chat.player(offlinePlayer.getName()) + " rank was set to " + Chat.rank(rank.getName()) + ".");
		}

		save();
	}

	/**
	 * Sets the second Rank of the player.
	 * 
	 * @param rank
	 *            The new Rank.
	 */
	public void setSecondRank(Rank rank)
	{
		config.set(ClientSection.SECOND_RANK.toString(), rank.getName());

		if (offlinePlayer != null)
			notification.sendToPlayers(false, "Rank", Chat.player(offlinePlayer.getName()) + " second rank was set to " + Chat.rank(rank.getName()) + ".");

		save();
	}

	public void setSetting(String setting, boolean Boolean)
	{
		config.set(ClientSection.SETTINGS.toString() + "." + setting, Boolean);
	}

	/**
	 * Modify the score of a statistic.
	 * 
	 * @param title
	 *            The name of the statistic.
	 * @param amount
	 *            The score of the statistic.
	 */
	public void setStatistic(String title, int amount)
	{
		String path = title.toLowerCase().replace(' ', '-');

		for (int numbers : milestones)
		{
			if (amount == numbers)
			{
				playStatisticSound();
				notification.sendToPlayers(false, "Stats", Chat.player(offlinePlayer.getName()) + " has reached " + Chat.tool(title) + " to " + Chat.tool(amount + "") + "!");
				new Reward(new OfflineClient(offlinePlayer.getName())).randomReward();
			}
		}

		config.set(ClientSection.STATS.toString() + "." + path, amount);
		save();
	}

	/**
	 * Sets a new upgrade level.
	 * 
	 * @param upgrade
	 *            The upgrade to level up.
	 * @param level
	 *            The specific level.
	 */
	public void setUpgradeLevel(Upgrade upgrade, int level)
	{
		String section = ClientSection.UPGRADES.toString() + "." + UtilString.slug(upgrade.getName()) + ".level";
		config.set(section, level);

		if (onlinePlayer != null)
		{
			onlinePlayer.sendMessage(Chat.format("Upgrade", "Your " + Chat.upgrade(upgrade.getName()) + " was upgraded to " + Chat.tool("Level " + level) + "."));
		}

		save();
	}

	/**
	 * Sets the last player username.
	 * 
	 * @param username The username to save.
	 */
	public void setUsername(String username)
	{
		config.set(ClientSection.USERNAME.toString(), username);
		save();
	}

	public boolean hasEnabledSetting(Setting setting)
	{
		return config.getBoolean(ClientSection.SETTINGS.toString() + "." + UtilString.slug(setting.getName()));
	}

	public void modifySetting(Setting setting, boolean option)
	{
		config.set(ClientSection.SETTINGS.toString() + "." + UtilString.slug(setting.getName()), option);

		if (onlinePlayer != null)
		{
			if (option)
				onlinePlayer.sendMessage(Chat.format("Setting", "You have enabled " + Chat.tool(setting.getName()) + "."));
			else
				onlinePlayer.sendMessage(Chat.format("Setting", "You have disabled " + Chat.tool(setting.getName()) + "."));
		}

		save();
	}

	public boolean hasCompletedHubParkour()
	{
		return config.getBoolean(ClientSection.CHALLENGES.toString() + ".parkour-hub");
	}

	public void setHubParkour(boolean completed)
	{
		config.set(ClientSection.CHALLENGES.toString() + ".parkour-hub", completed);
		save();
	}
}
