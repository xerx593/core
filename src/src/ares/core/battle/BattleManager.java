package src.ares.core.battle;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import src.ares.core.Main;
import src.ares.core.battle.ability.Ability;
import src.ares.core.battle.kit.Kit;
import src.ares.core.battle.kit.KitApollo;
import src.ares.core.battle.kit.KitAres;
import src.ares.core.battle.kit.KitHades;
import src.ares.core.battle.kit.KitHypnos;
import src.ares.core.battle.kit.KitPoseidon;
import src.ares.core.battle.kit.KitThor;
import src.ares.core.battle.kit.KitTyche;
import src.ares.core.battle.kit.KitZeus;
import src.ares.core.client.Client;
import src.ares.core.common.Module;
import src.ares.core.common.util.Chat;

public class BattleManager extends Module
{
	private static BattleManager instance = new BattleManager();

	// The sound played when the Kit is selected. //
	private static final Sound KIT_SELECT_SOUND = Sound.ORB_PICKUP;

	// The color of the Ability when showed on the chat. //
	private static final ChatColor ABILITY_COLOR = ChatColor.BLUE;

	public static BattleManager getInstance()
	{
		return instance;
	}

	// The Kit Array with the registered Kits. //
	private ArrayList<Kit> kitBag;

	// The list of Kit preferences from the players. //
	private HashMap<Player, Kit> kitPreferences = new HashMap<>();

	/**
	 * Creates and loads the list of the Kits.
	 */
	public void createKits()
	{
		kitBag = new ArrayList<>();

		kitBag.add(new KitZeus());
		kitBag.add(new KitPoseidon());
		kitBag.add(new KitThor());
		kitBag.add(new KitApollo());
		kitBag.add(new KitHypnos());
		kitBag.add(new KitTyche());
		kitBag.add(new KitHades());
		kitBag.add(new KitAres());

		for (Kit kit : kitBag)
		{
			Main.debug("Creating " + kit.getName() + ".");
			kit.registerEvents();
			kit.addItems();
		}
	}

	/**
	 * Gets the list of the registered Kits.
	 * @return ArrayList<Kit>
	 */
	public ArrayList<Kit> getKitBag()
	{
		return kitBag;
	}

	/**
	 * Gets a specific Kit by name from the registered Kits.
	 * 
	 * @param name The name of the Kit.
	 * @return Kit
	 */
	public Kit getKitByName(String name)
	{
		for (Kit kit : kitBag)
		{
			if (kit.getName().contains(name) || kit.getName().startsWith(name))
				return kit;
		}

		return null;
	}

	/**
	 * Gets the selected kit, if the player chose one.
	 * 
	 * @return Kit
	 */
	public Kit getKitPreference(Player player)
	{
		return kitPreferences.get(player);
	}

	/**
	 * Gets the list of the player Kit preferences.
	 * @return HashMap<Player, Kit>
	 */
	public HashMap<Player, Kit> getKitPreferences()
	{
		return kitPreferences;
	}

	/**
	 * Checks if the player has selected a kit.
	 * 
	 * @return Boolean
	 */
	public boolean hasSelectedKit(Player player)
	{
		if (kitPreferences.containsKey(player))
			return true;

		return false;
	}

	/**
	 * Sets the selected Kit for the player. Returns Boolean after checking if the target player owns the kit.
	 * 
	 * @param kit The kit to select.
	 * @param player The player to check.
	 * @param inform Display message
	 */
	public boolean selectKit(Kit kit, Player player, boolean inform)
	{
		Client client = new Client(player);

		if (client.getManager().isKitOwned(kit))
		{
			kitPreferences.put(player, kit);

			// Send a message only if inform is set to true.

			if (inform)
			{
				client.playSound(KIT_SELECT_SOUND, 1.0F, 1.0F);
				client.sendMessage("Kit", "You selected the " + Chat.kit(kit.getName() + " " + client.getManager().getKitLevel(kit)) + ".");

				client.sendRaw("");

				for (Ability ability : kit.getAbilityBag())
				{
					client.sendRaw(ABILITY_COLOR + "" + ChatColor.BOLD + ability.getName() + " " + client.getManager().getKitLevel(kit));
					client.sendRaw(" " + ChatColor.WHITE + ability.getTip(client));
				}

				client.sendRaw("");

				Main.debug(player.getName() + " selected " + kit.getName() + ".");
			}

			client.unload();

			return true;
		}
		else
		{
			return false;
		}
	}
}
