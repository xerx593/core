package src.ares.core.client;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import src.ares.core.client.storage.ClientManager;
import src.ares.core.common.util.UtilString;
import src.ares.core.currency.CurrencyType;
import src.ares.core.currency.ICurrency;

/**
 * Creates an offline player wrapper for nessesary data access from the plugin.
 * 
 * @see {@link Client}<br>{@link ClinetManager}
 */
public class OfflineClient
{
	private OfflinePlayer player;
	private ClientManager manager;

	/**
	 * Default Constructor
	 * 
	 * @param player The player to create a wrapper with.
	 */
	@SuppressWarnings("deprecation")
	public OfflineClient(String player)
	{
		this.player = Bukkit.getOfflinePlayer(player);
		this.manager = new ClientManager(this.player.getUniqueId());
	}

	/**
	 * Increases a player's currency with the value provided.
	 * 
	 * @param currency The currency type to increase.
	 */
	public void addCurrency(ICurrency currency)
	{
		currency.setAmount(manager.getCurrency(CurrencyType.valueOf(UtilString.enumerator(currency.getName()))) + currency.getAmount());
		manager.updateCurrency(currency, true);
	}

	/**
	 * Deducts a player's currency with the value provided.
	 *
	 * @param currency The currency type to decrease.
	 */
	public void removeCurrency(ICurrency currency)
	{
		currency.setAmount(manager.getCurrency(CurrencyType.valueOf(UtilString.enumerator(currency.getName()))) - currency.getAmount());
		manager.updateCurrency(currency, true);
	}

	/**
	 * Compares a specific rank with the player's rank.
	 * 
	 * @param rank The rank to compare with.
	 * @return If the player passed that check.
	 */
	public boolean compareWith(Rank rank)
	{
		return manager.getRank().ordinal() >= rank.ordinal();
	}

	/**
	 * Compares a specific rank with the player's second rank.
	 * 
	 * @param rank The rank to compare with.
	 * @return If the player passed the check.
	 */
	public boolean compareSecondWith(Rank rank)
	{
		return manager.getSecondRank().ordinal() >= rank.ordinal();
	}

	/**
	 * Returns the Manager of the player name.
	 * 
	 * @return ClientManager
	 */
	public ClientManager getManager()
	{
		return manager;
	}

	/**
	 * Returns the name of the offline player.
	 * 
	 * @return String
	 */
	public String getName()
	{
		return player.getName();
	}

	/**
	 * Returns the Offline version of the player name.
	 * 
	 * @return OfflinePlayer
	 */
	public OfflinePlayer getPlayer()
	{
		return player;
	}

	/**
	 * Returns the rank of the player
	 * @return
	 */
	public Rank getRank()
	{
		return manager.getRank();
	}

	/**
	 * Returns the UUID of the offline player.
	 * 
	 * @return UUID
	 */
	public UUID getUUID()
	{
		return player.getUniqueId();
	}

	/**
	 * Checks if the player is a staff member
	 * 
	 * @return boolean
	 */
	public boolean isStaff()
	{
		return compareWith(Rank.TRIAL_MOD);
	}

	/**
	 * Unloads all fields from the Client instance.
	 */
	public void unload()
	{
		this.player = null;
		this.manager = null;
	}
}
