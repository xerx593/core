package src.ares.core.battle.killstreak;

import java.util.HashMap;

import org.bukkit.entity.Player;

import src.ares.core.client.Client;

/**
 * Represent a collection of killstreak scores, saved in temporary form.
 */
public class Killstreak
{
	private static Killstreak instance = new Killstreak();

	public static Killstreak getInstance()
	{
		return instance;
	}

	private HashMap<Player, Integer> killstreaks = new HashMap<>();

	/**
	 * Returns the current killstreak score for a player.
	 * 
	 * @param player The player to check.
	 * @return Integer
	 */
	public int getCurrent(Player player)
	{
		return killstreaks.get(player);
	}

	/**
	 * Increases the current killstreak by 1 and saves a new high score in player's data.
	 * 
	 * @param player The player to increase the killstreak.
	 * @return Integer
	 */
	public int increase(Player player)
	{
		Client client = new Client(player);

		if (!killstreaks.containsKey(player))
		{
			killstreaks.put(player, 0);
		}

		int current = killstreaks.get(player);
		killstreaks.put(player, ++current);
		current = killstreaks.get(player);

		if (current > client.getManager().getKillstreak())
		{
			client.getManager().setKillstreak(client.getManager().getKillstreak() + current);
		}

		return current;
	}

	/**
	 * Removes the player from the killstreak collection.
	 * The player will be added again once a new killstreak is achieved.
	 * 
	 * @see {@link Killstreak#increase(Player)}
	 * @param player The player to target.
	 */
	public void remove(Player player)
	{
		if (killstreaks.containsKey(player))
			killstreaks.remove(player);
	}

	/**
	 * Resets the current killstreak of a player to 0.
	 * 
	 * @param player The player to target.
	 */
	public void reset(Player player)
	{
		if (!killstreaks.containsKey(player))
			return;

		killstreaks.put(player, 0);
	}

	/**
	 * Returns all current killstreaks stored in the collection.
	 * 
	 * @return HashMap<Player, Integer>
	 */
	public HashMap<Player, Integer> getKillstreaks()
	{
		return killstreaks;
	}
}
