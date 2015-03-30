package src.ares.core.gadget;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import src.ares.core.Main;
import src.ares.core.client.Client;
import src.ares.core.client.Rank;
import src.ares.core.common.Module;
import src.ares.core.common.item.CraftedItemStack;
import src.ares.core.common.util.Chat;
import src.ares.core.currency.ICurrency;

/**
 * A general gadget form with player usable functionality.
 * 
 * @see {@link HandGadget} <br> {@link ParticleGadget}
 */
public abstract class Gadget extends Module
{
	private String name;
	private Material material;
	private byte data;
	private ICurrency currency;
	private Rank rank;
	private GadgetManager manager = GadgetManager.getInstance();

	/**
	 * Default Constructor
	 * 
	 * @param name The name of the gadget.
	 * @param material The material of the gadget.
	 * @param rank The minimum rank required to use the gadget.
	 * @param currency The cost for each use of the gadget.
	 */
	public Gadget(String name, Material material, Rank rank, ICurrency currency)
	{
		super("Gadget");

		this.name = name;
		this.material = material;
		this.rank = rank;
		this.currency = currency;

		registerEvents();
	}

	/**
	 * Called when the gadget is enabled from a player.
	 * 
	 * @param player The player using this gadget.
	 */
	public void onEnable(Player player)
	{};

	/**
	 * Called when the gadget is disabled from a player.
	 * 
	 * @param player The player disabled this gadget.
	 */
	public void onDisable(Player player)
	{};

	/**
	 * Charges the player with the currency cost provided.
	 * 
	 * @param player The player to target.
	 * @return Boolean
	 */
	public boolean charge(Player player)
	{
		Client client = new Client(player);
		return client.removeCurrency(currency, false);
	}

	/**
	 * Toggles the gadget as enabled for a specific player.
	 * 
	 * @param player The player to target.
	 */
	public void enable(Player player)
	{
		Client client = new Client(player);

		if (client.compareWith(rank) || client.compareSecondWith(rank))
		{
			if (manager.getToggledGadgets().containsKey(player))
			{
				if (manager.getToggledGadgets().get(player).equals(this))
				{
					manager.getToggledGadgets().get(player).disable(player);
					return;
				}

				manager.getToggledGadgets().get(player).disable(player);
			}

			onEnable(player);
			manager.getToggledGadgets().put(player, this);
			player.sendMessage(Chat.format(getModuleName(), "You have enabled " + Chat.gadget(name) + "."));

			Main.debug(player.getName() + " enabled " + name + ".");
		}
		else
		{
			client.sendMessage(getModuleName(), "Only players with " + Chat.rank(rank.getName() + "+") + " can use that gadget.");
		}

		client.unload();
	}

	/**
	 * Toggles the gadget as disabled for a specific player.
	 * 
	 * @param player The player to target.
	 */
	public void disable(Player player)
	{
		if (manager.getToggledGadgets().containsKey(player))
		{
			onDisable(player);
			String previous = manager.getToggledGadgets().get(player).getName();
			manager.getToggledGadgets().remove(player);
			player.sendMessage(Chat.format(getModuleName(), "You have disabled " + Chat.gadget(previous) + "."));
		}
	}

	public void setData(byte data)
	{
		this.data = data;
	}

	public String getName()
	{
		return name;
	}

	public Material getMaterial()
	{
		return material;
	}

	public ItemStack getDisplay()
	{
		return new CraftedItemStack(material, ChatColor.GREEN + name, new String[]
		{
		ChatColor.WHITE + "" + ChatColor.BOLD + currency.getFormatted(), ChatColor.GRAY + "" + ChatColor.BOLD + rank.getName() + "+ Rank"
		}).setData(data).pack();
	}

	public ICurrency getCost()
	{
		return currency;
	}

	public Rank getRank()
	{
		return rank;
	}
}
