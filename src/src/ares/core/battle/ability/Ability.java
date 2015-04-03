package src.ares.core.battle.ability;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import src.ares.core.battle.BattleManager;
import src.ares.core.battle.kit.Kit;
import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldType;

/**
 * Represents an ability form that is wrapped within a Kit.
 * 
 * @see {@link Kit}
 */
public abstract class Ability extends Module
{
	private String name;
	private Kit kit;
	private HashMap<Player, Boolean> enabled = new HashMap<>();

	/**
	 * Default Constructor.<br>
	 * Note that the ability is not stored by default in the kit provided.
	 * 
	 * @param kit The kit data needed for certain functionality.
	 * @param name The name of the ability.
	 * @param tip A short line of text describing the ability.
	 */
	public Ability(Kit storedKit, String kitName)
	{
		super("Ability");
		this.kit = storedKit;
		this.name = kitName;

		registerEvents();
	}

	public void useAbility(Player player)
	{};

	/**
	 * Returns the kit level of the player.
	 * 
	 * @param player The player to search.
	 * @return Integer
	 */
	protected int level(Player player)
	{
		Client client = new Client(player);
		return client.getManager().getKitLevel(kit);
	}

	/**
	 * Checks if the player has enabled the Ability.
	 * 
	 * @param player The player to target.
	 */
	protected void enableUse(Player player)
	{
		enabled.put(player, true);
		player.sendMessage(Chat.format(getModuleName(), "You enabled " + Chat.ability(name)) + ".");
		player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 0.3F, 1.4F);
	}

	/**
	 * Checks if the player has disabled the Ability.
	 * 
	 * @param player The player to target.
	 */
	protected void disableUse(Player player)
	{
		enabled.put(player, false);
		player.sendMessage(Chat.format(getModuleName(), "You disabled " + Chat.ability(name)) + ".");
		player.playSound(player.getLocation(), Sound.PISTON_RETRACT, 0.3F, 1.4F);
	}

	/**
	 * Checks if the player has enabled the ability use.
	 * 
	 * @param player The player to target.
	 */
	protected boolean hasToggledUse(Player player)
	{
		if (enabled.containsKey(player))
			return enabled.get(player);
		return false;
	}

	/**
	 * Checks if the player pressed the drop key.
	 * 
	 * @param event Event data needed for nessesary checks.
	 * @return Boolean
	 */
	protected boolean pressedEnableKey(PlayerDropItemEvent event)
	{
		if (!BattleManager.getInstance().getKitPreference(event.getPlayer()).equals(kit))
			return false;

		event.setCancelled(true);
		return true;
	}

	/**
	 * Displays a short line of text, signifying the use of the ability.
	 * 
	 * @param player The player to send that message.
	 */
	protected void showUse(Player player)
	{
		player.sendMessage(Chat.format(getModuleName(), "You used " + Chat.ability(name) + "."));
	}

	/**
	 * Determines if the ability should be toggled or not.
	 * <br>
	 * <br>
	 * <b>True:</b> The ability will be disabled.
	 * <br>
	 * <b>False:</b> The ability will be enabled.
	 * 
	 * @param player The player to target.
	 */
	protected void toggleUse(Player player)
	{
		if (!hasToggledUse(player))
			enableUse(player);
		else
			disableUse(player);
	}

	/**
	 * Checks if the player can use the ability.
	 * 
	 * @param player The player to check.
	 * @return Boolean
	 */
	protected boolean validate(Player player)
	{
		BattleManager manager = BattleManager.getInstance();

		if (!manager.hasSelectedKit(player))
			return false;
		if (manager.getKitPreference(player) == null)
			return false;
		if (!manager.getKitPreference(player).equals(kit))
			return false;

		CoreWorld world = WorldManager.getInstance().getWorld(player.getWorld().getName());

		if (world.getWorldType() != WorldType.PVP)
			return false;

		if (player.getGameMode().equals(GameMode.CREATIVE))
			return false;

		return true;
	}

	public String getTip(Client client)
	{
		return "Default";
	}

	public Kit getKit()
	{
		return kit;
	}

	public String getName()
	{
		return name;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();

		if (enabled.containsKey(player))
			enabled.remove(player);
	}
}