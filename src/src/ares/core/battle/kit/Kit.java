package src.ares.core.battle.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import src.ares.core.battle.BattleManager;
import src.ares.core.battle.ability.Ability;
import src.ares.core.battle.listener.BattleConst;
import src.ares.core.battle.upgrade.Upgrade;
import src.ares.core.battle.upgrade.UpgradeManager;
import src.ares.core.client.Client;
import src.ares.core.client.storage.ClientManager;
import src.ares.core.common.Module;
import src.ares.core.common.SoupAddon;
import src.ares.core.common.crafted.CraftedArmor;
import src.ares.core.common.crafted.CraftedItemStack;
import src.ares.core.world.CoreWorld;
import src.ares.core.world.WorldManager;
import src.ares.core.world.WorldType;

import com.google.common.collect.ObjectArrays;

/**
 * Represents a set of items with certain properties, features and abilities.
 * 
 * @see {@link Ability}
 */
public abstract class Kit extends Module
{
	private String name;
	private String[] description;
	private int cost;
	private ChatColor chatColor;
	private Color color;

	private ItemStack display;
	private CraftedArmor preDisplay;
	private List<ItemStack> itemStackBag = new ArrayList<>();
	private List<Ability> abilityBag = new ArrayList<>();

	/**
	 * Default Constructor
	 * 
	 * @param name The name of the kit.
	 * @param description The description of the kit.
	 * @param cost The purchase cost of the kit.
	 * @param display The display item of the kit.
	 */
	public Kit(String kitName, String[] kitDesc, int kitCost, ChatColor kitChatColor, Color kitColor)
	{
		super("Kit");

		name = kitName;
		description = kitDesc;
		cost = kitCost;
		chatColor = kitChatColor;
		color = kitColor;

		preDisplay = new CraftedArmor(Material.LEATHER_CHESTPLATE, color, chatColor + name, description);
		display = preDisplay.build();
	}

	/**
	 * Populates the item contents of the kit.
	 */
	public abstract void addItems();

	/**
	 * Secondary features to be applied on a player.
	 * 
	 * @param player The player to target.
	 */
	public abstract void addEffects(Player player);

	/**
	 * Returns the kit level of the player.
	 * 
	 * @param player The player to search.
	 * @return Integer
	 */
	protected int level(Player player)
	{
		Client client = new Client(player);
		return client.getManager().getKitLevel(this);
	}

	/**
	 * Adds an ability to the kit.
	 * 
	 * @param ability The ability to add.
	 */
	protected void addAbility(Ability ability)
	{
		if (!abilityBag.contains(ability))
			abilityBag.add(ability);
	}

	/**
	 * Adds an item to the kit.
	 * 
	 * @param itemStack The item to add.
	 */
	protected void addItemStack(ItemStack itemStack)
	{
		itemStackBag.add(itemStack);
	}

	/**
	 * (Shortcut) Adds a specific amount of soups to the kit.
	 * 
	 * @param amount The amount of soups.
	 */
	protected void addSoups(int amount)
	{
		if (amount <= 0)
		{
			addItemStack(SoupAddon.getInstance().getDisplay());
		}
		else
		{
			for (int i = 0; i < amount; i++)
			{
				addItemStack(SoupAddon.getInstance().getDisplay());
			}
		}
	}

	/**
	 * Equips the player with the kit contents and effects.
	 * 
	 * @param player The player to give that kit.
	 */
	public void equip(Player player)
	{
		Client client = new Client(player);
		client.playLocationSound(BattleConst.KIT_EQUIP_SOUND, 1.0F, 1.0F);

		addEffects(player);

		player.getInventory().setHelmet(new CraftedArmor(Material.LEATHER_HELMET, color, "Olympus Helmet").unbreakable(true).build());
		player.getInventory().setChestplate(new CraftedArmor(Material.LEATHER_CHESTPLATE, color, "Olympus Chestplate").unbreakable(true).build());
		player.getInventory().setLeggings(new CraftedArmor(Material.LEATHER_LEGGINGS, color, "Olympus Leggings").unbreakable(true).build());
		player.getInventory().setBoots(new CraftedArmor(Material.LEATHER_BOOTS, color, "Olympus Boots").unbreakable(true).build());

		fetchUpgrades(client);

		for (ItemStack itemStack : itemStackBag)
		{
			player.getInventory().addItem(itemStack);
			player.getInventory().setItem(8, new CraftedItemStack(Material.FEATHER, "Back to Hub").build());
		}

		player.updateInventory();
	}

	/**
	 * Creates an item display with the client's data.
	 * 
	 * @param client The client to retrieve information.
	 * @return ItemStack
	 */
	public ItemStack getPersonalDisplay(Client client)
	{
		ClientManager manager = client.getManager();
		int level = manager.getKitLevel(this);

		if (level != 0)
			preDisplay.amount(level);

		if (manager.isKitOwned(this))
		{
			preDisplay.glow();

			preDisplay.lore(ObjectArrays.concat(description, new String[]
			{
			"", manager.getKitLevelFormat(this), ChatColor.GREEN + "" + ChatColor.BOLD + "Owned", "", ChatColor.WHITE + "Left-Click:" + ChatColor.GRAY + " to select that kit.", ChatColor.WHITE + "Right-Click:" + ChatColor.GRAY + " to upgrade that kit."
			}, String.class));

			return preDisplay.build();
		}
		else
		{
			preDisplay.lore(ObjectArrays.concat(description, new String[]
			{
			"", ChatColor.YELLOW + "" + ChatColor.BOLD + cost + " Gold", ChatColor.DARK_RED + "" + ChatColor.BOLD + "Not Owned", "", ChatColor.WHITE + "Left-Click:" + ChatColor.GRAY + " to select that kit.", ChatColor.WHITE + "Right-Click:" + ChatColor.GRAY + " to upgrade that kit."
			}, String.class));

			return preDisplay.build();
		}
	}

	/**
	 * Checks if the player is using that kit.
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
		if (!manager.getKitPreference(player).equals(this))
			return false;

		CoreWorld world = WorldManager.getInstance().getWorld(player.getWorld().getName());

		if (world.getWorldType() != WorldType.PVP)
			return false;

		if (player.getGameMode().equals(GameMode.CREATIVE))
			return false;

		return true;
	}

	/**
	 * Fetches all player upgrades to the kit.
	 * 
	 * @deprecated
	 * @param client The client to retrieve the upgrades from.
	 */
	private void fetchUpgrades(Client client)
	{
		Player player = client.getPlayer();

		for (Upgrade upgrade : UpgradeManager.getInstance().getUpgrades())
		{
			if (client.getManager().getUpgradeLevel(upgrade) > 0)
			{
				int level = client.getManager().getUpgradeLevel(upgrade);

				ItemStack[] armory = player.getInventory().getArmorContents();

				if (upgrade.getName().contains("Armor Protection"))
				{
					for (ItemStack armor : armory)
					{
						armor.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level);
					}
				}

				if (upgrade.getName().contains("Armor Fire Protection"))
				{
					for (ItemStack armor : armory)
					{
						armor.addEnchantment(Enchantment.PROTECTION_FIRE, level);
					}
				}

				if (upgrade.getName().contains("Armor Projectile Protection"))
				{
					for (ItemStack armor : armory)
					{
						armor.addEnchantment(Enchantment.PROTECTION_PROJECTILE, level);
					}
				}

				if (upgrade.getName().contains("Armor Thorns"))
				{
					for (ItemStack armor : armory)
					{
						armor.addEnchantment(Enchantment.THORNS, level);
					}
				}
			}
		}
	}

	public boolean isFree()
	{
		return cost == 0;
	}

	public List<Ability> getAbilityBag()
	{
		return abilityBag;
	}

	public ChatColor getChatColor()
	{
		return chatColor;
	}

	public Color getColor()
	{
		return color;
	}

	public int getCost()
	{
		return cost;
	}

	public String[] getDescription()
	{
		return description;
	}

	public ItemStack getDisplay()
	{
		return display;
	}

	public List<ItemStack> getItemStackBag()
	{
		return itemStackBag;
	}

	public String getName()
	{
		return name;
	}
}
