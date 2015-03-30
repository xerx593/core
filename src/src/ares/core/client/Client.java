package src.ares.core.client;

import java.util.UUID;

import net.minecraft.server.v1_8_R1.EntityPlayer;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import src.ares.core.client.storage.ClientManager;
import src.ares.core.common.util.Chat;
import src.ares.core.common.util.UtilString;
import src.ares.core.currency.CurrencyType;
import src.ares.core.currency.ICurrency;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;

/**
 * Creates an online player wrapper for nessesary data access from the plugin.
 * 
 * @see {@link OfflineClient}<br>{@link ClinetManager}
 */
public class Client
{
	private ClientManager manager;
	private Player player;

	/**
	 * Default Constructor
	 * 
	 * @param player The player to create a wrapper with.
	 */
	public Client(Player player)
	{
		this.player = player;
		this.manager = new ClientManager(player.getUniqueId());
	}

	/**
	 * Increases a player's currency with the value provided.
	 * 
	 * @param currency The currency type to increase.
	 * @param inform Wether or not a message should be displayed about that action.
	 */
	public void addCurrency(ICurrency currency, boolean inform)
	{
		int fixedAmount = manager.getCurrency(CurrencyType.valueOf(UtilString.enumerator(currency.getName()))) + currency.getAmount();
		
		if (manager.updateCurrency(currency.getName(), fixedAmount, inform))
		{
			if (inform)
			{
				player.sendMessage(Chat.format("Account", "You have collected " + Chat.gold(currency.getFormatted()) + "."));
			}
		}
	}

	/**
	 * Deducts a player's currency with the value provided.
	 * 
	 * @param currency The currency type to decrease.
	 * @param inform Wether or not a message should be displayed about that action.
	 * @return If the removal was successfull.
	 */
	public boolean removeCurrency(ICurrency currency, boolean inform)
	{
		int fixedAmount = manager.getCurrency(CurrencyType.valueOf(UtilString.enumerator(currency.getName()))) - currency.getAmount();

		if (manager.updateCurrency(currency.getName(), fixedAmount, inform))
		{
			if (inform)
			{
				player.sendMessage(Chat.format("Account", "You have lost " + Chat.gold(currency.getFormatted()) + "."));
			}
			
			return true;
		}

		return false;
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
	 * Returns the username of the Player.
	 * 
	 * @return String.
	 */
	public String getName()
	{
		return player.getName();
	}

	/**
	 * Returns the Player version of the Client.
	 * 
	 * @return Player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Returns the UUID of the Player.
	 * 
	 * @return UUID
	 */
	public UUID getUUID()
	{
		return player.getUniqueId();
	}

	/**
	 * Returns the World of the Player.
	 * 
	 * @return World
	 */
	public World getWorld()
	{
		return player.getWorld();
	}

	public CoreWorld getCoreWorld()
	{
		return WorldManager.getInstance().getWorld(player.getWorld().getName());
	}

	/**
	 * Returns the CraftPlayer version of the Player.
	 * 
	 * @return CraftPlayer
	 */
	public CraftPlayer getCraftPlayer()
	{
		CraftPlayer craftPlayer = (CraftPlayer) player;
		return craftPlayer;
	}

	/**
	 * Returns the EntityPlayer version of the Player.
	 * In other words, the <b>NMS</b> class directly from Minecraft.
	 * @return
	 */
	public EntityPlayer getEntityPlayer()
	{
		return getCraftPlayer().getHandle();
	}

	/**
	 * Returns the Gamemode of the Player.
	 * 
	 * @return GameMode
	 */

	public GameMode getGameMode()
	{
		return player.getGameMode();
	}

	/**
	 * Returns the Location of the Player.
	 * 
	 * @return Location
	 */
	public Location getLocation()
	{
		return player.getLocation();
	}

	/**
	 * Returns the ClientManager.
	 * 
	 * @return ClientManager
	 */
	public ClientManager getManager()
	{
		return manager;
	}

	/**
	 * Checks if the player is a developer.
	 * 
	 * @return Boolean
	 */
	public boolean isDeveloper()
	{
		return manager.getRank().equals(Rank.DEV);
	}

	/**
	 * Checks if the player is Elite and above.
	 * @return
	 */
	public boolean isElite()
	{
		return compareWith(Rank.ELITE);
	}

	/**
	 * Checks if the player is a staff member.
	 * 
	 * @return Boolean
	 */
	public boolean isStaff()
	{
		return compareWith(Rank.BUILDER) || compareWith(Rank.BUILDER);
	}

	public void kick(String head, String body)
	{
		player.kickPlayer(UtilString.disconnect(head, body));
	}

	/**
	 * Plays a sound to all nearby players relative to his location.
	 * 
	 * @param sound The type of the sound.
	 * @param volume The volume of the sound.
	 * @param pitch The pitch of the sound.
	 */
	public void playLocationSound(Sound sound, float volume, float pitch)
	{
		player.getWorld().playSound(player.getLocation(), sound, volume, pitch);
	}

	/**
	 * Plays a sound to the player relative to his location.
	 * 
	 * @param sound The type of the sound.
	 * @param volume The volume of the sound.
	 * @param pitch The pitch of the sound.
	 */
	public void playSound(Sound sound, float volume, float pitch)
	{
		player.playSound(player.getLocation(), sound, volume, pitch);
	}

	/**
	 * Sends a formatted message to the Player.
	 * 
	 * @param head The message title.
	 * @param body The message body.
	 */
	public void sendMessage(String head, String body)
	{
		player.sendMessage(Chat.format(head, body));
	}

	/**
	 * Sends a non-formatted message to the Player.
	 * 
	 * @param body The message body.
	 */
	public void sendRaw(String body)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', body));
	}

	/**
	 * Sets the Player's GameMode.
	 * 
	 * @param gamemode The chosen GameMode
	 */

	public void setGameMode(GameMode gamemode)
	{
		sendMessage("Gamemode", "Your gamemode has been changed.");
		player.setGameMode(gamemode);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Client other = (Client) obj;

		if (player == null)
		{
			if (other.player != null)
				return false;
		}

		else if (!player.equals(other.player))
			return false;

		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
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
